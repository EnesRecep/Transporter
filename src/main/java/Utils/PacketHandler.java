package Utils;

import Model.Packet;
import enums.BitTypeFlag;
import enums.MaxPacketSize;
import enums.PacketTypeFlag;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.*;

/**
 * Created by Tunc on 15.12.2018.
 */
public class PacketHandler {

    private HashMap<Integer,PriorityQueue<Packet>> partitionPriorityQueueHashMap = new HashMap<>();

    /*
       This method takes a priorityQueue as a parameter. In the priorityQueue there are packets compared according to their 'Order' number.
       Method iterates through the priorityQueue and gets all data(byte[]) and appends them.
     */
    public byte[] mergePacketData(PriorityQueue<Packet> priorityQueue){

        Packet packet = priorityQueue.peek();
        byte[] packetData = packet.getData();
        priorityQueue.remove(packet);

        while (priorityQueue.size() > 0){
            packet = priorityQueue.peek();
            byte[] temp = packetData;

            //POSSIBLE ERROR
            packetData = new byte[temp.length + packet.getData().length];
            System.arraycopy(temp,0,packetData,0,temp.length);
            System.arraycopy(packet.getData(),0,packetData,temp.length,packet.getData().length);
        }
        return packetData;
    }

    public void addPacket(DatagramPacket datagramPacket){

        Object object = dataExtraction(datagramPacket);

        if(object != null){
            notify(object);
        }
    }

    /*
    This method first checks the packet's type (if it is a single packet which is not divided or divided packet)
    If it is single, packet will be send to extractSinglePacketData and converted to Object type (if order == last)
    If it is a divided packet it's partition will be extracted. If there is not a priorityqueue for this partition a new entry
    will be created for the HashMap with partition being the key, and priorityqueue value. If there already is a priortiyqueue for this partition
    it will be used. Packet will be added to the priorityqueue. When a packet with LAST bit is set comes all packets' data will be extacted and appended.
    The entry will be deleted from the hashmap. Extracted data (byte[]) will be send to extractPacketData.
     */
    public Object dataExtraction(DatagramPacket datagramPacket){

        Packet packet = new Packet(datagramPacket);
        Object dataObject = null;

        if(packet.getOrder() == 0 && packet.getLast() == 1){
            dataObject = extractPacketData(packet);
        }else{
            int packetPartition = packet.getPartition();

            //Check if containsKey is redundent
            if(!partitionPriorityQueueHashMap.containsKey(packetPartition)){
                partitionPriorityQueueHashMap.put(packetPartition ,new PriorityQueue<Packet>());
            }

            PriorityQueue<Packet> priorityQueue = partitionPriorityQueueHashMap.get(packetPartition);
            priorityQueue.add(packet);

            if(packet.getLast() == 1){
                if(priorityQueue.size() - 1 == packet.getOrder()){
                    byte[] mergedPacketData = mergePacketData(priorityQueue);
                    partitionPriorityQueueHashMap.remove(packetPartition);

                    try {
                        dataObject = Serializer.deserialize(mergedPacketData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return dataObject;
    }

    public Object extractPacketData(Packet packet){
        Object dataObject = null;

        try {
            dataObject = Serializer.deserialize(packet.getData());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dataObject;
    }

    /**
     * Divides user data into specified chunk sizes
     *
     * @param data the user data that will be divided into chunks
     * @param size the max user data size in a packet
     *
     * @return user data chunks
     **/
    public ArrayList<byte[]> dividePacket(byte[] data, MaxPacketSize size)
    {
        ArrayList<byte[]> dataArray = new ArrayList<>();

        int totalChunks = data.length / size.getSize();

        for(int i = 0; i < totalChunks; i++)
        {
            byte[] dataChunk = new byte[size.getSize()];

            System.arraycopy(data, i * size.getSize(), dataChunk, 0, size.getSize());

            dataArray.add(dataChunk);
        }

        int remainData = data.length % size.getSize();

        if(remainData > 0)
        {
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
     *
     * @return converted number or flag
     **/
    public String toBinary(int i, BitTypeFlag f)
    {
        String binary = "";

        if(f == BitTypeFlag.TO_16_BIT)
        {
            binary = Integer.toBinaryString(i);

            if(binary.length() < 16)
            {
                int k = 16 - binary.length();

                for(int j = 0; j < k; j++)
                {
                    binary = "0" + binary;
                }
            }
        }
        else if(f == BitTypeFlag.TO_4_BIT)
        {
            binary = Integer.toBinaryString(i);

            if(binary.length() < 4)
            {
                int k = 4 - binary.length();

                for(int j = 0; j < k; j++)
                {
                    binary = "0" + binary;
                }
            }
        }
        else if(f == BitTypeFlag.TO_2_BIT)
        {
            binary = Integer.toBinaryString(i);

            if(binary.length() < 2)
            {
                int k = 2 - binary.length();

                for(int j = 0; j < k; j++)
                {
                    binary = "0" + binary;
                }
            }
        }
        else if(f == BitTypeFlag.TO_1_BIT)
        {
            binary = Integer.toBinaryString(i);
        }

        return binary;
    }

    public Packet parsePacket(DatagramPacket packet)
    {
        String packetContent = new String(packet.getData());
        Packet parsedPacket = new Packet();

        parsedPacket.setOrder(Integer.parseInt(packetContent.substring(0, 16), 2));
        parsedPacket.setPartition(Integer.parseInt(packetContent.substring(16, 20), 2));
        parsedPacket.setFin(Integer.parseInt(packetContent.substring(20, 21), 2));
        parsedPacket.setLast(Integer.parseInt(packetContent.substring(21, 22), 2));
        parsedPacket.setPacketTypeFlag(PacketTypeFlag.toPacketTypeFlagEnum(packetContent.substring(22, 24)));

        if(parsedPacket.getPacketTypeFlag() == PacketTypeFlag.HANDSHAKING_ACK || parsedPacket.getPacketTypeFlag() == PacketTypeFlag.MESSAGE_ACK)
        {
            int[] messagePorts = {
                    Integer.parseInt(packetContent.substring(24, 40), 2),
                    Integer.parseInt(packetContent.substring(40, 56), 2),
                    Integer.parseInt(packetContent.substring(56, 72), 2)
            };

            int[] ackPorts = {-1, -1, -1};

            parsedPacket.setMessagePorts(messagePorts);
            parsedPacket.setAckPorts(ackPorts);
            parsedPacket.setData(packetContent.substring(72).getBytes());
        }
        else
        {
            int[] ackPorts = {
                    Integer.parseInt(packetContent.substring(24, 40), 2),
                    Integer.parseInt(packetContent.substring(40, 56), 2),
                    Integer.parseInt(packetContent.substring(56, 72), 2)
            };

            int[] messagePorts = {
                    Integer.parseInt(packetContent.substring(72, 88), 2),
                    Integer.parseInt(packetContent.substring(88, 104), 2),
                    Integer.parseInt(packetContent.substring(104, 120), 2)
            };

            parsedPacket.setMessagePorts(messagePorts);
            parsedPacket.setAckPorts(ackPorts);
            parsedPacket.setData(packetContent.substring(120).getBytes());
        }

        return parsedPacket;
    }

}
