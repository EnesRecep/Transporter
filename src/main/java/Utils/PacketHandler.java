package Utils;

import Model.Packet;

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

}
