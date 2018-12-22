package communication;

import Model.Packet;
import Model.PacketType;
import Utils.PacketHandler;
import Utils.PortHandler;
import Utils.SocketHandler;
import enums.PacketTypeFlag;
import exceptions.CommunicationDataException;
import exceptions.PacketListenException;
import exceptions.PacketSendException;
import pool.ServerListenerPool;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 15.12.2018.
 */
public class Communication {

    private String oppositeAddr;
    private DatagramPacket handshakePacket;
    private boolean isCommunicationStartedByUs;
    private HandshakeCommunication handshakeCommunication = new HandshakeCommunication();
    private MessageCommunication messageCommunication = new MessageCommunication();
    private PortHandler portHandler = new PortHandler();
    private PacketHandler packetHandler = new PacketHandler();
    private SocketHandler socketHandler = new SocketHandler();

    private int[] messagePortsListen;
    private int[] messagePortsSend;

    private int[] ackPortsListen;
    private int[] ackPortsSend;

    private boolean isHandshakeDone = false;

    private PacketType packetType;

    public int[] getMessagePortsListen() {
        return messagePortsListen;
    }

    public void setMessagePortsListen(int[] messagePortsListen) {
        this.messagePortsListen = messagePortsListen;
        handshakeCommunication.setMessagePortsListen(messagePortsListen);
        messageCommunication.setMessagePortsListen(messagePortsListen);
    }

    public int[] getMessagePortsSend() {
        return messagePortsSend;
    }

    public void setMessagePortsSend(int[] messagePortsSend) {
        this.messagePortsSend = messagePortsSend;
        handshakeCommunication.setMessagePortsSend(messagePortsSend);
        messageCommunication.setMessagePortsSend(messagePortsSend);

    }

    public int[] getAckPortsListen() {
        return ackPortsListen;
    }

    public void setAckPortsListen(int[] ackPortsListen) {
        this.ackPortsListen = ackPortsListen;
        handshakeCommunication.setAckPortsListen(ackPortsListen);
        messageCommunication.setAckPortsListen(ackPortsListen);
    }

    public int[] getAckPortsSend() {
        return ackPortsSend;
    }

    public void setAckPortsSend(int[] ackPortsSend) {
        this.ackPortsSend = ackPortsSend;
        handshakeCommunication.setAckPortsSend(ackPortsSend);
        messageCommunication.setAckPortsSend(ackPortsSend);
    }

    public void setHandshakePacket(DatagramPacket handshakePacket) {
        this.handshakePacket = handshakePacket;
    }

    public String getOppositeAddr() {
        return oppositeAddr;
    }


    public void setOppositeAddr(String oppositeAddr) {

        this.oppositeAddr = oppositeAddr;
    }

    public DatagramPacket getHandshakePacket() {
        return handshakePacket;
    }

    public boolean isCommunicationStartedByUs() {
        return isCommunicationStartedByUs;
    }

    public void setCommunicationStartedByUs(boolean communicationStartedByUs) {
        isCommunicationStartedByUs = communicationStartedByUs;
    }

    public void keepSendingACK(DatagramPacket receivedhandshake) {
        handshakeCommunication.sendHandshakeACK(receivedhandshake);

    }

    public Object communicationWait() {
        try {
            handshakeCommunication.waitHandshakePacket();

            Thread thread = new Thread(handshakeCommunication);
            thread.start();

        } catch (PacketListenException e) {
            e.printStackTrace();
        }
        return waitForMessage(messagePortsListen);
    }

    public Object communicationWait(String oppositeAddr) {
        try {
            Packet handShakeACKPacket = handshakeCommunication.sendHandshake(oppositeAddr);
            if (handShakeACKPacket.getPacketTypeFlag().equals(PacketTypeFlag.ACK_PACKET))
                isHandshakeDone = true;
        } catch (PacketListenException e) {
            e.printStackTrace();
        } catch (PacketSendException e) {
            e.printStackTrace();
        }
        return waitForMessage(messagePortsListen);
    }

    public void send(Object data, String oppositeAddr) {
        Packet handShakeACKPacket = null;

        try {

            handShakeACKPacket = handshakeCommunication.sendHandshake(oppositeAddr);
            if (handShakeACKPacket.getPacketTypeFlag().equals(PacketTypeFlag.ACK_PACKET))
                isHandshakeDone = true;

            messageCommunication.setMessagePortsListen(handShakeACKPacket.getMessagePorts());
            messageCommunication.startMessageCommunication(data, true);

        } catch (PacketSendException e) {
            e.printStackTrace();
        } catch (PacketListenException e) {
            e.printStackTrace();
        } catch (CommunicationDataException e) {
            e.printStackTrace();
        }

    }

    public Object waitForMessage(int[] ports) {
        DatagramPacket receivedMessage = null;

        ServerListenerPool messagePool = new ServerListenerPool();
        receivedMessage = messagePool.threadPoolRunner(ports, Constants.MESSAGE_TIMEOUT);
        if (receivedMessage != null) {
            handshakeCommunication.setExecution(false);
        }

        while (true) {
            Object object = packetHandler.addPacket(receivedMessage);
            if (object != null)
                return object;

        }
    }

    /*
    This method will be called when we or the other user wants to close the communication. Messages' flags will be checked before we send packet and when we receive packet.
    If a received message's flag is FINPacket after ACK is sent and FIN_TIMEOUT passes this emthod will be called.
    If we send a FINPacket after sending the packet this method will be called.
     */
    public void FINProcedure() {

        SocketHandler socketHandler = new SocketHandler();
        socketHandler.getSocket().close();

        CommunicationPool.getInstance().removeCommunication(this);

    }


}
