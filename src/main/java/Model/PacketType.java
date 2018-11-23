package Model;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 22.11.2018.
 */


public interface PacketType {

    public DatagramPacket createPacket(Object data, String addr, int port);

}

class HandshakePacket implements PacketType{

    public DatagramPacket createPacket(Object data, String addr, int port) {
        return null;
    }
}

class ACKPacket implements PacketType{

    public DatagramPacket createPacket(Object data, String addr, int port) {

        return null;
    }
}

class MessagePacket implements PacketType{

    public DatagramPacket createPacket(Object data, String addr, int port) {
        return null;
    }
}






