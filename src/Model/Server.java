package Model;

import Utils.PortHandler;
import enums.PacketTypeFlag;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by Tunc on 27.10.2018.
 */
public class Server {

    private DatagramSocket socket;
    private int port;
    //private int milSecTimeout;
    private DatagramPacket recvPacket;
    private byte[] buffer = new byte[256];
    private PacketType packetType;
    private PortHandler portHandler;


    public Server(int port) {

        this.port = port;
        openSocket(port);
        recvPacket = new DatagramPacket(buffer, buffer.length);
        portHandler = new PortHandler();
    }


    public void openSocket(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public DatagramPacket waitForPacket() {

        port = portHandler.getSelectedPort();

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

    public void sendACKPacket(Object data, String addr, int port) {

        DatagramPacket packet = packetType.createPacket(data, addr, port);

        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Identifies and returns packet type
     *
     * @param packet a datagram packet that will be identified
     *
     * @return type of the packet (HAND_P, HAND_ACK_P, MESS_P, MESS_ACK_P)
     **/
    public PacketTypeFlag getPacketType(DatagramPacket packet)
    {
        String pflpFlags = String.format("%8s",
                Integer.toBinaryString(packet.getData()[2] & 0xFF)).replace(' ', '0');

        String packetType = pflpFlags.substring(pflpFlags.length() - 2);

        if(packetType.equals("00"))
            return PacketTypeFlag.HANDSHAKING;
        else if(packetType.equals("01"))
            return PacketTypeFlag.HANDSHAKING_ACK;
        else if(packetType.equals("10"))
            return PacketTypeFlag.MESSAGE;
        else if(packetType.equals("11"))
            return PacketTypeFlag.MESSAGE_ACK;
        else
            return PacketTypeFlag.UNKNOWN_PACKET;
    }

}
