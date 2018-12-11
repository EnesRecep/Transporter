package Model;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 22.11.2018.
 */


public interface PacketType {

    public DatagramPacket createPacket(Object data, String addr, int port);

}





