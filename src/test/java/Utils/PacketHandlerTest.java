package Utils;

import Model.PacketCreator;
import enums.BitTypeFlag;
import enums.MaxPacketSize;
import org.junit.Test;

import java.net.DatagramPacket;

import static org.junit.Assert.*;

public class PacketHandlerTest
{
  String binaryNumber = "0000011111001100";
  byte[] data = {'a', 'b', 'c', 'd', 'e',
          'a', 'b', 'c', 'd', 'e',
          'a', 'b', 'c', 'd', 'e',
          'a', 'b', 'c', 'd', 'e',
          'a', 'b', 'c', 'd', 'e',
          'a', 'b', 'c', 'd', 'e',
          'a', 'b', 'c', 'd', 'e'};

  String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam in bibendum lorem. Aenean vitae justo ipsum. Maecenas eu quam vitae purus pellentesque molestie nec in ligula. Nunc iaculis, mi in dictum malesuada, risus est lacinia dui, sed laoreet sem quam sed nulla. Sed ac metus tristique, ultricies dolor id, tincidunt diam. Cras sem justo, luctus eget fermentum eu, malesuada quis massa. Suspendisse cursus purus sit amet neque convallis, vel laoreet neque luctus. Aenean ac quam scelerisque, fringilla nisl sit amet, mattis enim. Maecenas at nisl non urna rutrum ultrices nec vulputate massa. Ut scelerisque porttitor libero eget lobortis. Curabitur dapibus metus nibh, a pretium lorem blandit quis. Fusce eget sapien posuere, viverra dui vel, blandit odio. Interdum et malesuada fames ac ante ipsum primis in faucibus.\n" +
          "\n" +
          "Vestibulum nec ante rhoncus, elementum justo a, ultricies tortor. Vestibulum mattis nibh id mi sollicitudin pellentesque. Sed euismod eleifend dui, ut auctor sem tincidunt vel. Etiam felis neque, imperdiet ut lectus sed, mollis suscipit enim. Proin vitae consequat orci. Mauris eleifend sapien eget ligula eleifend, vulputate volutpat nunc tristique. Nulla in porttitor ex. Mauris quam purus, iaculis ut mauris ut, sagittis suscipit ipsum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris sed lectus sagittis, pretium massa sit amet, iaculis metus. Phasellus mattis porta enim ut accumsan. Sed sit amet tortor et felis viverra accumsan vel ac ligula. Ut volutpat ex quis euismod pellentesque.\n" +
          "\n" +
          "Morbi vitae arcu non nunc aliquet molestie vel eu eros. Sed sodales consequat mi. Quisque sit amet ipsum tincidunt, euismod dolor sed, laoreet libero. Nunc fermentum nisi sit amet turpis interdum consequat. Pellentesque luctus justo erat, et feugiat est viverra at. Curabitur egestas orci vitae erat laoreet ultrices. Aliquam erat volutpat. Sed ultricies urna sed elit sollicitudin fringilla. In faucibus enim diam, nec posuere ligula feugiat id. Etiam malesuada eros odio, eget finibus elit feugiat sed. Nullam elementum auctor dignissim. Etiam ornare felis vel enim congue varius. Sed lobortis pellentesque consequat.\n" +
          "\n" +
          "Fusce sed risus a neque venenatis imperdiet. In imperdiet lacinia leo a volutpat. Duis pellentesque lacus lorem, nec blandit tellus scelerisque ac. Pellentesque ac odio est. Suspendisse eget ligula at nunc rutrum fringilla at eu mi. Cras vel imperdiet nisl. Pellentesque malesuada nisl nec arcu varius interdum. Mauris at accumsan felis. Morbi maximus id risus vitae maximus. Integer nec lacus nec nisl pretium mollis. Suspendisse bibendum laoreet rhoncus. Mauris metus mi, malesuada ac condimentum in, tempus in quam. Nam tincidunt feugiat ante ac congue. Quisque tortor mi, semper eu porta eu, eleifend vitae ligula.\n" +
          "\n" +
          "Aenean lobortis, quam tincidunt dignissim euismod, lorem mauris fermentum lorem, ut tempor nisl mi ut nisi. Pellentesque venenatis ligula eu sagittis hendrerit. Integer et diam tincidunt mauris aliquet viverra in eu sem. Etiam luctus lorem sed molestie porta. Aliquam dapibus sem id nisi varius, ut consequat lacus varius. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas vulputate est arcu, at convallis ante ultrices nec. Fusce et sollicitudin nisi, sit amet aliquet ante. Nullam non sollicitudin elit, quis tincidunt ligula. Aenean placerat, augue rhoncus aliquam iaculis, nisl justo accumsan libero, sed lobortis augue enim non urna. Nulla libero erat, pretium vel luctus a, gravida at urna. Nam quis urna et risus faucibus mollis id at neque. In ex augue, rhoncus vitae quam ac, mattis hendrerit risus.";

  @Test
  public void toBinaryTest()
  {
    PacketHandler packetHandler = new PacketHandler();

    assertEquals(binaryNumber, packetHandler.toBinary(1996, BitTypeFlag.TO_16_BIT));
  }

  @Test
  public void dividePacketTest()
  {
    PacketHandler packetHandler = new PacketHandler();

    assertEquals(18, packetHandler.dividePacket(data, MaxPacketSize.SIZE_2).size());
  }

  @Test
  public void createHandshakePacket()
  {
    PacketCreator packetCreator = new PacketCreator();
    DatagramPacket[] packets = packetCreator.createHandshakePackets(message, "192.168.1.1", 1995);

    for(int i = 0; i < 4; i++)
    {
      assertNotNull(packets[i]);
    }
  }

  @Test
  public void createHandshakeACKPacket()
  {
    PacketCreator packetCreator = new PacketCreator();
    DatagramPacket[] packets = packetCreator.createHandshakeACKPackets(message, "192.168.1.1", 1995);

    for(int i = 0; i < 4; i++)
    {
      assertNotNull(packets[i]);
    }
  }

  @Test
  public void createMessagePacket()
  {
    PacketCreator packetCreator = new PacketCreator();
    DatagramPacket[] packets = packetCreator.createMessagePackets(message, "192.168.1.1", 1995);

    for(int i = 0; i < 4; i++)
    {
      assertNotNull(packets[i]);
    }
  }

  @Test
  public void createMessageACKPacket()
  {
    PacketCreator packetCreator = new PacketCreator();
    DatagramPacket[] packets = packetCreator.createMessageACKPackets(message, "192.168.1.1", 1995);

    for(int i = 0; i < 4; i++)
    {
      assertNotNull(packets[i]);
    }
  }
}
