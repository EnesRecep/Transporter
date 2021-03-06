package Model;

import Utils.PacketHandler;
import Utils.PortHandler;
import Utils.Serializer;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import enums.BitTypeFlag;
import enums.MaxPacketSize;
import enums.PacketTypeFlag;
import org.apache.commons.lang.ArrayUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;


public class PacketCreator {
    public DatagramPacket[] createPacket(Object data, String destAddress, int destPort, PacketTypeFlag packetType) {
        //UserData userData = new UserData(data);
        //data = userData;
        try {
            System.out.println();
            byte[] userDataBytes = Serializer.serialize2(data);

            for (int i = 0; i < userDataBytes.length; i++) {
                System.out.println(userDataBytes[i]);
            }

            System.out.println("In CreatePacket");
            System.out.println(userDataBytes.length);
            System.out.println(new String(userDataBytes));
            PacketHandler packetHandler = new PacketHandler();
            ArrayList<byte[]> dataChunks = packetHandler.dividePacket(userDataBytes, MaxPacketSize.SIZE_1024);
            DatagramPacket[] packets = new DatagramPacket[dataChunks.size()];
            Random random = new Random();
            int partition = random.nextInt(16);

            PortHandler portHandler = new PortHandler();

            for (int i = 0; i < dataChunks.size(); i++) {

                System.out.println("CHUNK " + i);
                for(int j = 0; j < dataChunks.get(i).length;j++){
                    System.out.println(dataChunks.get(i)[j]);
                }
                StringBuilder header = new StringBuilder();
                header.append(packetHandler.toBinary(i, BitTypeFlag.TO_16_BIT));
                header.append(packetHandler.toBinary(partition, BitTypeFlag.TO_4_BIT));

                if (packetType == PacketTypeFlag.FIN_PACKET)
                    header.append(packetHandler.toBinary(1, BitTypeFlag.TO_1_BIT));
                else
                    header.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

                if (i == (dataChunks.size() - 1))
                    header.append(packetHandler.toBinary(1, BitTypeFlag.TO_1_BIT));
                else
                    header.append(packetHandler.toBinary(0, BitTypeFlag.TO_1_BIT));

                header.append(packetType.toString());


                for (int j = (packetType == PacketTypeFlag.ACK_PACKET ? 1 : 2); j > 0; j--) {
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

                System.out.println((new String(dataChunks.get(0))));
                System.out.println(dataChunks.get(0).length);
                System.out.println("All packet content");

//        for(int k = 0; k < wholePacketContent.length;k++){
//          System.out.print();
//        }

                DatagramPacket packet = new DatagramPacket(wholePacketContent, wholePacketContent.length);
                destAddress = destAddress.replace("/", "");
                packet.setAddress(InetAddress.getByName(destAddress));
                packet.setPort(destPort);

                packets[i] = packet;


//        Packet packet1 = new Packet(packet);
//        System.out.println(packet1.getData().length);
//        System.out.println(new String(packet1.getData()));
            }

            System.out.println("Packet number = " + packets.length);

            System.out.println("PACKET SIZES");
            for(int i = 0; i < packets.length;i++){
                System.out.println(packets[i].getData().length);
            }
            return packets;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;

    }
}
