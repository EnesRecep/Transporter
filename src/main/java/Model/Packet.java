package Model;

import enums.PacketTypeFlag;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 22.11.2018.
 *
 * Represents a packet
 * Includes packet parser
 * It implements the RFC v2.0
 */
public class Packet {

  private int order;
  private int partition;
  private int fin;
  private int last;
  private PacketTypeFlag packetTypeFlag;

  private int[] ackPorts;

  private int[] messagePorts;

  private Object data;

  public Packet()
  {
  }

  public Packet(int order, int partition, int fin, int last, PacketTypeFlag packetTypeFlag, int[] ackPorts, int[] messagePorts, Object data)
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

  public Object getData()
  {
    return data;
  }

  public void setData(Object data)
  {
    this.data = data;
  }

  public void parsePacket(DatagramPacket packet)
  {
    String packetData = new String(packet.getData());

    order = Integer.parseInt(packetData.substring(0, 17), 2);
    partition = Integer.parseInt(packetData.substring(17, 21), 2);
    fin = Integer.parseInt(packetData.substring(21, 22), 2);
    last = Integer.parseInt(packetData.substring(22, 23), 2);

    messagePorts[0] = Integer.parseInt(packetData.substring(73, 89), 2);
    messagePorts[1] = Integer.parseInt(packetData.substring(89, 105), 2);
    messagePorts[2] = Integer.parseInt(packetData.substring(105, 121), 2);

    data = packetData.substring(121);

    packetTypeFlag = PacketTypeFlag.toPacketTypeFlagEnum(packetData.substring(23, 24));

    if(packetTypeFlag == PacketTypeFlag.HANDSHAKING_ACK || packetTypeFlag == PacketTypeFlag.MESSAGE_ACK)
    {
      ackPorts[0] = -1;
      ackPorts[1] = -1;
      ackPorts[2] = -1;
    }
    else
    {
      ackPorts[0] = Integer.parseInt(packetData.substring(25, 41), 2);
      ackPorts[1] = Integer.parseInt(packetData.substring(41, 57), 2);
      ackPorts[2] = Integer.parseInt(packetData.substring(57, 73), 2);
    }
  }
}
