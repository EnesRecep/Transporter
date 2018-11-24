package Model;

import org.junit.Test;

import java.math.BigInteger;
import java.net.DatagramPacket;

import static org.junit.Assert.*;
/**
 * Created by Enes Recep on 23.11.2018.
 */
public class ServerTest {

    @Test
    public void openSocketControl(){

        Server server = new Server();
        server.openSocket(7777);
        assertNotNull(server.getSocket());
    }

    /**
     * Test 'getPacketTypeFlag' function ability
     **/
    @Test
    public void getPacketTypeTest()
    {
        Server server = new Server();
        String data = "00000000000100110000011000000101110111000000011111010000000010011100010000001011101110000000110110101100000011111010000011111111111111111111111111111111";
        byte[] byteData = new BigInteger(data, 2).toByteArray();
        DatagramPacket datagramPacket = new DatagramPacket(byteData, byteData.length);

        // 10 is in binary format and it represents communication message packet type (see RFC v2.0)
        assertEquals("10", server.getPacketTypeFlag(datagramPacket).toString());
    }

}
