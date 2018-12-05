package Utils;

import enums.BitTypeFlag;
import enums.MaxPacketSize;

import java.util.ArrayList;

/**
 * Operations that are related with packets (see RFC v2.0)
 *
 * @author 19XLR95
 * @author Clasmad
 * @version 1.0
 * @since 05.12.2018
 **/
public class PacketHandler
{
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
}
