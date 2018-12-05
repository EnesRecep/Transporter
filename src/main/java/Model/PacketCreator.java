package Model;

import Utils.PacketHandler;
import Utils.Serializer;
import enums.BitTypeFlag;
import enums.MaxPacketSize;
import enums.PacketTypeFlag;
import org.apache.commons.lang.ArrayUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

public class PacketCreator
{
  public DatagramPacket[] createHandshakePackets(Object data, String addr, int port)
  {
    try
    {
      byte[] allData = Serializer.serialize(data);
      PacketHandler packetHandler = new PacketHandler();
      ArrayList<byte[]> dataChunks = packetHandler.dividePacket(allData, MaxPacketSize.SIZE_1024);
      DatagramPacket[] packets = new DatagramPacket[dataChunks.size()];

      for(int i = 0; i < dataChunks.size(); i++)
      {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(packetHandler.toBinary(i, BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(dataChunks.size(), BitTypeFlag.TO_4_BIT));
        userMessage.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

        if(i == (dataChunks.size() - 1))
          userMessage.append(packetHandler.toBinary(1, BitTypeFlag.TO_1_BIT));
        else
          userMessage.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

        userMessage.append(PacketTypeFlag.HANDSHAKING_PACKET.toString());

        Server s = new Server();

        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));

        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));

        byte[] packetContent = ArrayUtils.addAll(
                userMessage.toString().getBytes(), dataChunks.get(i)
        );

        DatagramPacket aPacket = new DatagramPacket(packetContent, packetContent.length);
        aPacket.setAddress(InetAddress.getByName(addr));
        aPacket.setPort(port);

        packets[i] = aPacket;
      }

      return packets;
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }

    return null;
  }

  public DatagramPacket[] createHandshakeACKPackets(Object data, String addr, int port)
  {
    try
    {
      byte[] allData = Serializer.serialize(data);
      PacketHandler packetHandler = new PacketHandler();
      ArrayList<byte[]> dataChunks = packetHandler.dividePacket(allData, MaxPacketSize.SIZE_1024);
      DatagramPacket[] packets = new DatagramPacket[dataChunks.size()];

      for(int i = 0; i < dataChunks.size(); i++)
      {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(packetHandler.toBinary(i, BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(dataChunks.size(), BitTypeFlag.TO_4_BIT));
        userMessage.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

        if(i == (dataChunks.size() - 1))
          userMessage.append(packetHandler.toBinary(1, BitTypeFlag.TO_1_BIT));
        else
          userMessage.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

        userMessage.append(PacketTypeFlag.HANDSHAKING_ACK.toString());

        Server s = new Server();

        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));

        byte[] packetContent = ArrayUtils.addAll(
                userMessage.toString().getBytes(), dataChunks.get(i)
        );

        DatagramPacket aPacket = new DatagramPacket(packetContent, packetContent.length);
        aPacket.setAddress(InetAddress.getByName(addr));
        aPacket.setPort(port);

        packets[i] = aPacket;
      }

      return packets;
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }

    return null;
  }

  public DatagramPacket[] createMessagePackets(Object data, String addr, int port)
  {
    try
    {
      byte[] allData = Serializer.serialize(data);
      PacketHandler packetHandler = new PacketHandler();
      ArrayList<byte[]> dataChunks = packetHandler.dividePacket(allData, MaxPacketSize.SIZE_1024);
      DatagramPacket[] packets = new DatagramPacket[dataChunks.size()];

      for(int i = 0; i < dataChunks.size(); i++)
      {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(packetHandler.toBinary(i, BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(dataChunks.size(), BitTypeFlag.TO_4_BIT));
        userMessage.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

        if(i == (dataChunks.size() - 1))
          userMessage.append(packetHandler.toBinary(1, BitTypeFlag.TO_1_BIT));
        else
          userMessage.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

        userMessage.append(PacketTypeFlag.MESSAGE_PACKET.toString());

        Server s = new Server();

        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));

        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));

        byte[] packetContent = ArrayUtils.addAll(
                userMessage.toString().getBytes(), dataChunks.get(i)
        );

        DatagramPacket aPacket = new DatagramPacket(packetContent, packetContent.length);
        aPacket.setAddress(InetAddress.getByName(addr));
        aPacket.setPort(port);

        packets[i] = aPacket;
      }

      return packets;
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }

    return null;
  }

  public DatagramPacket[] createMessageACKPackets(Object data, String addr, int port)
  {
    try
    {
      byte[] allData = Serializer.serialize(data);
      PacketHandler packetHandler = new PacketHandler();
      ArrayList<byte[]> dataChunks = packetHandler.dividePacket(allData, MaxPacketSize.SIZE_1024);
      DatagramPacket[] packets = new DatagramPacket[dataChunks.size()];

      for(int i = 0; i < dataChunks.size(); i++)
      {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(packetHandler.toBinary(i, BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(dataChunks.size(), BitTypeFlag.TO_4_BIT));
        userMessage.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

        if(i == (dataChunks.size() - 1))
          userMessage.append(packetHandler.toBinary(1, BitTypeFlag.TO_1_BIT));
        else
          userMessage.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

        userMessage.append(PacketTypeFlag.MESSAGE_ACK.toString());

        Server s = new Server();

        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        userMessage.append(packetHandler.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));

        byte[] packetContent = ArrayUtils.addAll(
                userMessage.toString().getBytes(), dataChunks.get(i)
        );

        DatagramPacket aPacket = new DatagramPacket(packetContent, packetContent.length);
        aPacket.setAddress(InetAddress.getByName(addr));
        aPacket.setPort(port);

        packets[i] = aPacket;
      }

      return packets;
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }

    return null;
  }
}
