package Model;

import Utils.PacketHandler;
import enums.PacketTypeFlag;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 22.11.2018.
 */
public class Packet implements Comparable<Packet>{

    private int order;
    private int partition;
    private int fin;
    private int last;
    private PacketTypeFlag packetTypeFlag;
    private int[] ackPorts;
    private int[] messagePorts;
    private byte[] data; //was Object changed to byte[]
    public Packet()
    {
    }

    public Packet(DatagramPacket datagramPacket){
//        parsePacket(datagramPacket);
        Packet tempPacket = new PacketHandler().parsePacket(datagramPacket);
        this.order = tempPacket.order;
        this.partition = tempPacket.partition;
        this.fin = tempPacket.fin;
        this.last = tempPacket.last;
        this.packetTypeFlag = tempPacket.packetTypeFlag;
        this.ackPorts = tempPacket.ackPorts;
        this.messagePorts = tempPacket.messagePorts;
        this.data = tempPacket.data;
    }
    public Packet(int order, int partition, int fin, int last, PacketTypeFlag packetTypeFlag, int[] ackPorts, int[] messagePorts, byte[] data)
    {
        this.order = order;
        this.partition = partition;
        this.fin = fin;
        this.last = last;
        this.packetTypeFlag = packetTypeFlag;
        this.ackPorts = ackPorts;
        this.messagePorts = messagePorts;
        this.data = data;
    }
    public int getOrder()
    {
        return order;
    }
    public void setOrder(int order)
    {
        this.order = order;
    }
    public int getPartition()
    {
        return partition;
    }
    public void setPartition(int partition)
    {
        this.partition = partition;
    }
    public int getFin()
    {
        return fin;
    }
    public void setFin(int fin)
    {
        this.fin = fin;
    }
    public int getLast()
    {
        return last;
    }
    public void setLast(int last)
    {
        this.last = last;
    }
    public PacketTypeFlag getPacketTypeFlag()
    {
        return packetTypeFlag;
    }
    public void setPacketTypeFlag(PacketTypeFlag packetTypeFlag)
    {
        this.packetTypeFlag = packetTypeFlag;
    }
    public int[] getAckPorts()
    {
        return ackPorts;
    }
    public void setAckPorts(int[] ackPorts)
    {
        this.ackPorts = ackPorts;
    }
    public int[] getMessagePorts()
    {
        return messagePorts;
    }
    public void setMessagePorts(int[] messagePorts)
    {
        this.messagePorts = messagePorts;
    }
    public byte[] getData()
    {
        return data;
    }
    public void setData(byte[] data)
    {
        this.data = data;
    }
//    public void parsePacket(DatagramPacket packet)
//    {
//
//        String packetData = new String(packet.getData());
//        order = Integer.parseInt(packetData.substring(0, 17), 2);
//        partition = Integer.parseInt(packetData.substring(17, 21), 2);
//        fin = Integer.parseInt(packetData.substring(21, 22), 2);
//        last = Integer.parseInt(packetData.substring(22, 23), 2);
//        messagePorts[0] = Integer.parseInt(packetData.substring(73, 89), 2);
//        messagePorts[1] = Integer.parseInt(packetData.substring(89, 105), 2);
//        messagePorts[2] = Integer.parseInt(packetData.substring(105, 121), 2);
//        data = packetData.substring(121).getBytes();
//        packetTypeFlag = PacketTypeFlag.toPacketTypeFlagEnum(packetData.substring(23, 24));
//        if(packetTypeFlag == PacketTypeFlag.ACK_PACKET)
//        {
//            ackPorts[0] = -1;
//            ackPorts[1] = -1;
//            ackPorts[2] = -1;
//        }
//        else
//        {
//            ackPorts[0] = Integer.parseInt(packetData.substring(25, 41), 2);
//            ackPorts[1] = Integer.parseInt(packetData.substring(41, 57), 2);
//            ackPorts[2] = Integer.parseInt(packetData.substring(57, 73), 2);
//        }
//    }


    /*
    This method is used to compare packets according to their orders
    if this packet's order is greater returns 1
    if less returns -1
     */
    @Override
    public int compareTo(Packet packet) {
        if(this.getOrder() > packet.getOrder()){
            return 1;
        }else if(this.getOrder() < packet.getOrder()){
            return -1;
        }

        return 0;
    }
}
