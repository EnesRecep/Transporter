package Model;

import org.junit.Test;

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

    @Test
    public void getPacketTypeTest()
    {
        Server server = new Server();
        String data = "The packet content!!!";
        DatagramPacket datagramPacket = new DatagramPacket(data.getBytes(), data.length());

        System.out.println(server.getPacketTypeFlag(datagramPacket).toString());
    }

}
