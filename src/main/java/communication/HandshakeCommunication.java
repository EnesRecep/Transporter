package communication;

import Model.*;
import Utils.PacketHandler;
import Utils.PortHandler;
import Utils.Serializer;
import Utils.SocketHandler;
import exceptions.PacketListenException;
import exceptions.PacketSendException;
import pool.ServerListenerPool;

import javax.sound.sampled.Port;
import java.io.IOException;
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

        System.out.println((new String(receivedHandshakePacket.getData())).substring(120));
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
            System.out.println("[Sending ACK to :]" + ackPackets[0].getAddress().getHostAddress());
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

            communication.setAckPortsListen(new Packet(sendingPackets[0]).getAckPorts());

            socketHandler.sendPacket(sendingPackets[0]);
            System.out.println("asdasdasdasdasdasdasd");
            System.out.println((new String(sendingPackets[0].getData())).substring(120));

            Packet sendingPack = packetHandler.parsePacket(sendingPackets[0]);
            ackPortsListen = sendingPack.getAckPorts();
            System.out.println("[ACK listen port : ]"+ ackPortsListen[0]);
            messagePortsListen = sendingPack.getMessagePorts();
            if(messagePortsListen == null){
                System.out.println("messageportslisten null");
            }
            communication.setMessagePortsListen(messagePortsListen);


            Packet receivingPacket = communication.waitForMessage(ackPortsListen);

            if(receivingPacket == null)
                continue;
            }

            receivedPacket = receivingPacket;

            System.out.println("Received Packet");
            System.out.println("Received ACK: " + receivingPacket.getSerializedData());

            messagePortsSend = receivingPacket.getMessagePorts();

            //CALL new method with messagePortSend
            break;

        }

        System.out.println("[Received ACK]" + receivedPacket.toString());
        return receivedPacket;

    }

    public DatagramPacket waitHandshakePacket() throws PacketListenException {

        if (!state)
            throw new PacketListenException("The communication is close!");

        ServerListenerPool ackPool = new ServerListenerPool();
        DatagramPacket receivedhandshake = ackPool.threadPoolRunner(portHandler.getMyPorts(), Constants.MESSAGE_TIMEOUT);

            if (receivedhandshake.getData() == null)
                System.out.println("Received Handshake is NULL");
            else {
                System.out.println("[Received Handshake]");
                Packet p = new Packet(receivedhandshake);
                //System.out.println("DATA:  " +  (String)Serializer.deserialize(p.getData()));
            }

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
