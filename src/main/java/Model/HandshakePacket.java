package Model;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 11.12.2018.
 */
public class HandshakePacket implements PacketType {
    @Override
    public DatagramPacket createPacket(Object data, String addr, int port) {
        return null;
    }
}
