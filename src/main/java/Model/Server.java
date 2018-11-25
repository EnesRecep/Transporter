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

    public Server(){

    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public DatagramPacket getRecvPacket() {
        return recvPacket;
    }

    public void setRecvPacket(DatagramPacket recvPacket) {
        this.recvPacket = recvPacket;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }

    public void openSocket(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket()
    {
        if(socket != null)
            socket.close();
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

    public DatagramPacket sendACKPacket(Object data, String addr, int port) {

        DatagramPacket packet = packetType.createPacket(data, addr, port);

        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return packet;
    }

    /**
     * Identifies and returns communication packet type
     *
     * @param packet a datagram packet that will be identified
     *
     * @return the packet type (00, 01, 10, 11 - see RFC v2.0)
     **/
    public PacketTypeFlag getPacketTypeFlag(DatagramPacket packet)
    {
        String pflpFlags = String.format("%8s",
                Integer.toBinaryString(packet.getData()[1] & 0xFF)).replace(' ', '0');

        String packetFlag = pflpFlags.substring(pflpFlags.length() - 2);

        if(packetFlag.equals("00"))
            return PacketTypeFlag.HANDSHAKING_PACKET;
        else if(packetFlag.equals("01"))
            return PacketTypeFlag.HANDSHAKING_ACK;
        else if(packetFlag.equals("10"))
            return PacketTypeFlag.MESSAGE_PACKET;
        else if(packetFlag.equals("11"))
            return PacketTypeFlag.MESSAGE_ACK;
        else
            return PacketTypeFlag.UNKNOWN_PACKET;
    }

}
