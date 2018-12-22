package Model;

import Utils.PacketHandler;
import Utils.PortHandler;
import Utils.Serializer;
import enums.BitTypeFlag;
import enums.MaxPacketSize;
import enums.PacketTypeFlag;
import org.apache.commons.lang.ArrayUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

public class PacketCreator
{
  public DatagramPacket[] createPacket(Object data, String destAddress, int destPort, PacketTypeFlag packetType)
  {
    try
    {
      byte[] userDataBytes = Serializer.serialize(data);
      PacketHandler packetHandler = new PacketHandler();
      ArrayList<byte[]> dataChunks = packetHandler.dividePacket(userDataBytes, MaxPacketSize.SIZE_1024);
      DatagramPacket[] packets = new DatagramPacket[dataChunks.size()];
      Random random = new Random();
      int partition = random.nextInt(16);

      PortHandler portHandler = new PortHandler();

      for(int i = 0; i < dataChunks.size(); i++)
      {
        StringBuilder header = new StringBuilder();
        header.append(packetHandler.toBinary(i, BitTypeFlag.TO_16_BIT));
        header.append(packetHandler.toBinary(partition, BitTypeFlag.TO_4_BIT));

        if(packetType == PacketTypeFlag.FIN_PACKET)
          header.append(packetHandler.toBinary(1, BitTypeFlag.TO_1_BIT));
        else
          header.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

        if(i == (dataChunks.size() - 1))
          header.append(packetHandler.toBinary(1, BitTypeFlag.TO_1_BIT));
        else
          header.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

        header.append(packetType.toString());



        for(int j = (packetType == PacketTypeFlag.ACK_PACKET ? 1 : 2); j > 0 ; j--)
        {
            System.out.println("PORTs:");
          int port = portHandler.getPort();
            System.out.println(port);
          header.append(packetHandler.toBinary(port, BitTypeFlag.TO_16_BIT));
          port = portHandler.getPort();
            System.out.println(port);
          header.append(packetHandler.toBinary(port, BitTypeFlag.TO_16_BIT));
          port = portHandler.getPort();
            System.out.println(port);
          header.append(packetHandler.toBinary(port, BitTypeFlag.TO_16_BIT));
        }

        byte[] wholePacketContent = ArrayUtils.addAll(
                header.toString().getBytes(), dataChunks.get(i)
        );

        DatagramPacket packet = new DatagramPacket(wholePacketContent, wholePacketContent.length);
        packet.setAddress(InetAddress.getByName(destAddress));
        packet.setPort(destPort);

        packets[i] = packet;
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
