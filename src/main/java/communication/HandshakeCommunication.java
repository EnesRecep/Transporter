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

/**
 * Created by Enes Recep on 8.12.2018.
 */
public class HandshakeCommunication extends Communication implements Runnable {


    private boolean isSending;
    private boolean state;
    private PortHandler portHandler;
    private PacketHandler packetHandler;
    private SocketHandler socketHandler;
    private PacketType packetType;
    private DatagramPacket handShakePacket;

    private boolean execution;


    private int[] ackPortsSend;
    private int[] ackPortsListen;
    private int[] messagePortsListen;
    private int[] messagePortsSend;


    @Override
    public int[] getAckPortsSend() {
        return ackPortsSend;
    }

    @Override
    public void setAckPortsSend(int[] ackPortsSend) {
        this.ackPortsSend = ackPortsSend;
    }

    @Override
    public int[] getAckPortsListen() {
        return ackPortsListen;
    }

    @Override
    public void setAckPortsListen(int[] ackPortsListen) {
        this.ackPortsListen = ackPortsListen;
    }

    @Override
    public int[] getMessagePortsListen() {
        return messagePortsListen;
    }

    @Override
    public void setMessagePortsListen(int[] messagePortsListen) {
        this.messagePortsListen = messagePortsListen;
    }

    @Override
    public int[] getMessagePortsSend() {
        return messagePortsSend;
    }

    @Override
    public void setMessagePortsSend(int[] messagePortsSend) {
        this.messagePortsSend = messagePortsSend;
    }

    public boolean isExecution() {
        return execution;
    }

    public void setExecution(boolean execution) {
        this.execution = execution;
    }

    public HandshakeCommunication() {
        packetHandler = new PacketHandler();
    }

    public void sendHandshakeACK(DatagramPacket receivedHandshakePacket) {

        Packet packet = packetHandler.parsePacket(receivedHandshakePacket);
        for (int i = 0; i < Constants.MAX_TEST_TIME/2; i++) {
            DatagramPacket[] ackPackets = packetType.createPacket(null, receivedHandshakePacket.getAddress().toString(), packet.getAckPorts()[i % 3]);
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
            DatagramPacket[] sendingPackets = packetType.createPacket(null, addr, portHandler.createPortNumberFromDestinationHostname(addr)[i % 3]);
            socketHandler.sendPacket(sendingPackets[0]);

            Packet sendingPack = packetHandler.parsePacket(sendingPackets[0]);
            ackPortsListen = sendingPack.getAckPorts();
            messagePortsListen = sendingPack.getMessagePorts();

            DatagramPacket receivingPacket = (DatagramPacket) super.waitForMessage(ackPortsListen);
            if(receivingPacket == null)
                continue;


            receivedPacket = packetHandler.parsePacket(receivingPacket);

            messagePortsSend = receivedPacket.getMessagePorts();

            //CALL new method with messagePortSend
            break;

        }

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
