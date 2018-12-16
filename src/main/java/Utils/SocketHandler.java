package Utils;

import Model.PacketType;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

/**
 * Created by Enes Recep on 16.12.2018.
 */
public class SocketHandler {

    private PacketType packetType;
    private DatagramSocket socket;
    private PortHandler porthandler;
    private DatagramPacket recvPacket;


    //private byte[] buffer = new byte[256];
    //recvPacket = new DatagramPacket(buffer, buffer.length);

    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }

    public void openSocket(){
        try {
            socket = new DatagramSocket(porthandler.getPort());
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void openSocket(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public DatagramPacket[] getPackets(byte[] data, String addr, int port){
        return packetType.createPacket(data, addr,port);
    }

    public void sendPacket(DatagramPacket packet){
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DatagramPacket waitForPacket(int port) {

        try {
            socket = new DatagramSocket(port);
            socket.receive(recvPacket);
        } catch (SocketTimeoutException ste) {
            ste.getSuppressed();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Connection established");
        return recvPacket;
    }
}
