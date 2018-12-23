package communication;

import Model.*;
import Utils.PacketHandler;
import Utils.PortHandler;
import Utils.SocketHandler;
import exceptions.PacketListenException;
import exceptions.PacketSendException;
import pool.ServerListenerPool;

import javax.sound.sampled.Port;
import java.net.DatagramPacket;
import java.net.SocketException;

/**
 * Created by Enes Recep on 8.12.2018.
 */
public class HandshakeCommunication implements Runnable {


    private boolean isSending;
    private boolean state;
    private PortHandler portHandler = new PortHandler();
    private PacketHandler packetHandler;
    private SocketHandler socketHandler = new SocketHandler();
    private PacketType packetType;
    private DatagramPacket handShakePacket;
    private Communication communication;

    private boolean execution;


    private int[] ackPortsSend;
    private int[] ackPortsListen;
    private int[] messagePortsListen;
    private int[] messagePortsSend;

    public int[] getAckPortsSend() {
        return ackPortsSend;
    }

    public void setAckPortsSend(int[] ackPortsSend) {
        this.ackPortsSend = ackPortsSend;
    }

    public int[] getAckPortsListen() {
        return ackPortsListen;
    }

    public void setAckPortsListen(int[] ackPortsListen) {
        this.ackPortsListen = ackPortsListen;
    }

    public int[] getMessagePortsListen() {
        return messagePortsListen;
    }

    public void setMessagePortsListen(int[] messagePortsListen) {
        this.messagePortsListen = messagePortsListen;
    }

    public int[] getMessagePortsSend() {
        return messagePortsSend;
    }

    public void setMessagePortsSend(int[] messagePortsSend) {
        this.messagePortsSend = messagePortsSend;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }



    public boolean isExecution() {
        return execution;
    }

    public void setExecution(boolean execution) {
        this.execution = execution;
    }

    public HandshakeCommunication() throws SocketException {
        packetHandler = new PacketHandler();
    }

    public HandshakeCommunication(Communication communication) throws SocketException {
        packetHandler = new PacketHandler();
        this.communication = communication;
    }
    public void sendHandshakeACK(DatagramPacket receivedHandshakePacket) {
        packetType = new ACKPacket();
        Packet packet = packetHandler.parsePacket(receivedHandshakePacket);
        for (int i = 0; i < Constants.MAX_TEST_TIME/2; i++) {
            ////////////////////////////////////////////
            //TEMP DELETE
            int  a = 5;
            ////////////////////////////////////////////
            DatagramPacket[] ackPackets = packetType.createPacket(a, receivedHandshakePacket.getAddress().toString(), packet.getAckPorts()[i % 3]);
            Packet packetACK = packetHandler.parsePacket(ackPackets[0]);
            communication.setMessagePortsListen(packetACK.getMessagePorts());
            socketHandler.sendPacket(ackPackets[0]);
        }


        //Call new Listen method
    }

    public Packet sendHandshake(String addr) throws PacketSendException, PacketListenException {

        Packet receivedPacket = null;
        if (!state)
            throw new PacketSendException("The communication is close!");

        for (int i = 0; i < Constants.MAX_TEST_TIME; i++) {

            socketHandler.openSocket();

            //Creating handshake packet and send to opposite user
            //TODO : instead of null send our public key
            packetType = new HandshakePacket();

            //////////////////////////////
            //TEMP DELETE

            String a = "handshake ackackack";
            ////////////////////////////////////////
            DatagramPacket[] sendingPackets = packetType.createPacket(a, addr, portHandler.createPortNumberFromDestinationHostname(addr)[i % 3]);
            socketHandler.sendPacket(sendingPackets[0]);

            Packet sendingPack = packetHandler.parsePacket(sendingPackets[0]);
            ackPortsListen = sendingPack.getAckPorts();
            System.out.println("ACK ports: "+ ackPortsListen[0]);
            messagePortsListen = sendingPack.getMessagePorts();
            communication.setMessagePortsListen(messagePortsListen);

            System.out.println("Waiting for ACK");
            Packet receivingPacket = communication.waitForMessage(ackPortsListen);
            if(receivingPacket == null)
                continue;

            System.out.println("Received ACK: " + receivedPacket.getSerializedData());

            messagePortsSend = receivedPacket.getMessagePorts();
            //CALL new method with messagePortSend
            break;

        }

        System.out.println("Returning : " + receivedPacket.toString());
        return receivedPacket;

    }

    public DatagramPacket waitHandshakePacket() throws PacketListenException {

        if (!state)
            throw new PacketListenException("The communication is close!");

        ServerListenerPool ackPool = new ServerListenerPool();
        DatagramPacket receivedhandshake = ackPool.threadPoolRunner(portHandler.getMyPorts(), Constants.MESSAGE_TIMEOUT);

        sendHandshakeACK(receivedhandshake);

        return receivedhandshake;

    }

    public void startHandshake(String addr) throws PacketSendException, PacketListenException {

        state = true;
        socketHandler.setPacketType(new HandshakePacket());
        sendHandshake(addr);
    }

    @Override
    public void run() {
        while(execution) {
            try {
                handShakePacket = waitHandshakePacket();
                if (handShakePacket != null)
                    sendHandshakeACK(handShakePacket);
            } catch (PacketListenException e) {
                e.printStackTrace();
            }
        }
    }

}
