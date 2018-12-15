package Utils;

import Model.Packet;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.DatagramPacket;

/**
 * Created by Tunc on 15.12.2018.
 */
public class PacketHandlerTest {

    @Test
    public void extractPacketDataCorrectObject(){
        String data = "Test Data";
        byte[] byteData = data.getBytes();

        DatagramPacket datagramPacket = new DatagramPacket(byteData,byteData.length);
        Packet packet = new Packet(datagramPacket);
        Object object = null,extractedObject = null;

        try {
            object = Serializer.deserialize(packet.getData());
            PacketHandler packetHandler = new PacketHandler();
            extractedObject = packetHandler.extractPacketData(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals((String)object, (String)extractedObject);
    }
}
