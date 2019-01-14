package Utils;

import Model.Packet;
import Model.UserData;
import enums.BitTypeFlag;
import enums.MaxPacketSize;
import enums.PacketTypeFlag;
import org.apache.commons.lang.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.util.*;

/**
 * Created by Tunc on 15.12.2018.
 */
public class PacketHandler {

    private HashMap<Integer, PriorityQueue<Packet>> partitionPriorityQueueHashMap = new HashMap<>();

    /*
       This method takes a priorityQueue as a parameter. In the priorityQueue there are packets compared according to their 'Order' number.
       Method iterates through the priorityQueue and gets all data(byte[]) and appends them.
     */
    public byte[] mergePacketData(PriorityQueue<Packet> priorityQueue) {



        byte[] both = priorityQueue.peek().getData();
//        System.out.println("--------------------------------------");
//        for(int i = 0 ; i < both.length; i++){
//            System.out.println(both[i]);
//        }
        priorityQueue.remove(priorityQueue.peek());
        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (priorityQueue.size() > 0){

//            System.out.println("-------------------------------------");
//            for(int i = 0 ; i < priorityQueue.peek().getData().length ; i++){
//                System.out.println(priorityQueue.peek().getData()[i]);
//            }
//            System.out.println("-------------------------------------");
            System.out.println("[P QUEUE SIZE  ]" + priorityQueue.size());
            both = ArrayUtils.addAll(both, priorityQueue.peek().getData());
            priorityQueue.remove(priorityQueue.peek());
        }

        return both;

    }

    public Packet addPacket(DatagramPacket datagramPacket) {

        return dataExtraction(datagramPacket);

    }

    /*
    This method first checks the packet's type (if it is a single packet which is not divided or divided packet)
    If it is single, packet will be send to extractSinglePacketData and converted to Object type (if order == last)
    If it is a divided packet it's partition will be extracted. If there is not a priorityqueue for this partition a new entry
    will be created for the HashMap with partition being the key, and priorityqueue value. If there already is a priortiyqueue for this partition
    it will be used. Packet will be added to the priorityqueue. When a packet with LAST bit is set comes all packets' data will be extacted and appended.
    The entry will be deleted from the hashmap. Extracted data (byte[]) will be send to extractPacketData.
     */
    public Packet dataExtraction(DatagramPacket datagramPacket) {

        Packet packet = new Packet(datagramPacket);

        if (packet.getPacketTypeFlag().equals(PacketTypeFlag.ACK_PACKET)) {

            packet.setToSerializeData(Arrays.copyOfRange(datagramPacket.getData(), 72, datagramPacket.getData().length));
            //System.out.println(packet.getToSerializeData().toString());
        } else {
            packet.setToSerializeData(Arrays.copyOfRange(datagramPacket.getData(), 120, datagramPacket.getData().length));
        }
        Object dataObject = null;

        if (packet.getOrder() == 0 && packet.getLast() == 1) {

            dataObject = extractPacketData(packet);
            packet.setSerializedData(dataObject);
        } else {
            int packetPartition = packet.getPartition();

            //Check if containsKey is redundent
            if (!partitionPriorityQueueHashMap.containsKey(packetPartition)) {
                partitionPriorityQueueHashMap.put(packetPartition, new PriorityQueue<Packet>());
            }

            PriorityQueue<Packet> priorityQueue = partitionPriorityQueueHashMap.get(packetPartition);

            priorityQueue.add(packet);

            //partitionPriorityQueueHashMap.remove(packetPartition);
            partitionPriorityQueueHashMap.put(packetPartition, priorityQueue);
            System.out.println("[DATA EXTRACTION][QUEUE ADD] " + priorityQueue.size());
            if (packet.getLast() == 1) {
                if (priorityQueue.size() - 1 == packet.getOrder()) {
                    byte[] mergedPacketData = mergePacketData(priorityQueue);
                    System.out.println("[AFTER MERGE DATA]");
                    partitionPriorityQueueHashMap.remove(packetPartition);

                    try {
                        dataObject = Serializer.deserialize(mergedPacketData);
                        packet.setSerializedData(dataObject);
                        //System.out.println("[SERIALIZED DATA OF PACKET ]" + packet.getSerializedData().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return null;
            }
        }
        System.out.println("[DATA EXTRACTION] " + packet.getSerializedData());


        return packet;
    }

    public Object extractPacketData(Packet packet) {
        Object dataObject = null;


        try {
            dataObject = Serializer.deserialize(packet.getToSerializeData());
            packet.setSerializedData(dataObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return packet;
    }

    /**
     * Divides user data into specified chunk sizes
     *
     * @param data the user data that will be divided into chunks
     * @param size the max user data size in a packet
     * @return user data chunks
     **/
    public ArrayList<byte[]> dividePacket(byte[] data, MaxPacketSize size) {
        ArrayList<byte[]> dataArray = new ArrayList<>();

        int totalChunks = data.length / size.getSize();

        for (int i = 0; i < totalChunks; i++) {
            byte[] dataChunk = new byte[size.getSize()];

            System.arraycopy(data, i * size.getSize(), dataChunk, 0, size.getSize());

            dataArray.add(dataChunk);
        }

        int remainData = data.length % size.getSize();

        if (remainData > 0) {
            byte[] remainBytes = new byte[remainData];

            System.arraycopy(data, totalChunks * size.getSize(), remainBytes, 0, remainData);

            dataArray.add(remainBytes);
        }

        return dataArray;
    }

    /**
     * Converts numbers or flags to bit type
     *
     * @param i the number or flag that will be converted
     * @param f the bit length (to 1, 2, 4, 16 bits)
     * @return converted number or flag
     **/
    public String toBinary(int i, BitTypeFlag f) {
        String binary = "";

        if (f == BitTypeFlag.TO_16_BIT) {
            binary = Integer.toBinaryString(i);

            if (binary.length() < 16) {
                int k = 16 - binary.length();

                for (int j = 0; j < k; j++) {
                    binary = "0" + binary;
                }
            }
        } else if (f == BitTypeFlag.TO_4_BIT) {
            binary = Integer.toBinaryString(i);

            if (binary.length() < 4) {
                int k = 4 - binary.length();

                for (int j = 0; j < k; j++) {
                    binary = "0" + binary;
                }
            }
        } else if (f == BitTypeFlag.TO_2_BIT) {
            binary = Integer.toBinaryString(i);

            if (binary.length() < 2) {
                int k = 2 - binary.length();

                for (int j = 0; j < k; j++) {
                    binary = "0" + binary;
                }
            }
        } else if (f == BitTypeFlag.TO_1_BIT) {
            binary = Integer.toBinaryString(i);
        }

        return binary;
    }


    public Packet parsePacket(DatagramPacket packet) {
        String packetContent = null;
        try {
            packetContent = new String(packet.getData(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //String packetContent = packet.getData().toString();


        Packet parsedPacket = new Packet();

        parsedPacket.setOrder(Integer.parseInt(packetContent.substring(0, 16), 2));
        parsedPacket.setPartition(Integer.parseInt(packetContent.substring(16, 20), 2));
        parsedPacket.setFin(Integer.parseInt(packetContent.substring(20, 21), 2));
        parsedPacket.setLast(Integer.parseInt(packetContent.substring(21, 22), 2));
        parsedPacket.setPacketTypeFlag(PacketTypeFlag.toPacketTypeFlagEnum(packetContent.substring(22, 24)));


        if (parsedPacket.getPacketTypeFlag() == PacketTypeFlag.ACK_PACKET) {
            int[] messagePorts = {
                    Integer.parseInt(packetContent.substring(24, 40), 2),
                    Integer.parseInt(packetContent.substring(40, 56), 2),
                    Integer.parseInt(packetContent.substring(56, 72), 2)
            };

            int[] ackPorts = {-1, -1, -1};

            parsedPacket.setMessagePorts(messagePorts);
            parsedPacket.setAckPorts(ackPorts);

            parsedPacket.setData(Arrays.copyOfRange(packet.getData(), 72, packet.getData().length));
            //parsedPacket.setData(packetContent.substring(72).getBytes());
        } else {
            int[] ackPorts = {
                    Integer.parseInt(packetContent.substring(24, 40), 2),
                    Integer.parseInt(packetContent.substring(40, 56), 2),
                    Integer.parseInt(packetContent.substring(56, 72), 2)
            };

            //System.out.println("Parser ACK Ports"+ackPorts[0]);
            int[] messagePorts = {
                    Integer.parseInt(packetContent.substring(72, 88), 2),
                    Integer.parseInt(packetContent.substring(88, 104), 2),
                    Integer.parseInt(packetContent.substring(104, 120), 2)
            };

            parsedPacket.setMessagePorts(messagePorts);
            parsedPacket.setAckPorts(ackPorts);

            System.out.println("packet content:" + packetContent.substring(120).length());

            //parsedPacket.setData(packetContent.substring(120).getBytes());
            parsedPacket.setData(Arrays.copyOfRange(packet.getData(),120,packet.getData().length));
            System.out.println("In packet handler:" + packetContent.substring(120).getBytes().length);

        }


        return parsedPacket;
    }

    /**
     * Identifies and returns communication packet type
     *
     * @param packet a datagram packet that will be identified
     * @return the packet type (00, 01, 10, 11 - see RFC v2.0)
     **/
    public PacketTypeFlag getPacketTypeFlag(DatagramPacket packet) {
        return new PacketHandler().parsePacket(packet).getPacketTypeFlag();
    }

}
