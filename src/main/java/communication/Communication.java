package communication;

import Model.Packet;
import Model.PacketType;
import Utils.PacketHandler;
import Utils.PortHandler;
import Utils.Serializer;
import Utils.SocketHandler;
import enums.PacketTypeFlag;
import exceptions.CommunicationDataException;
import exceptions.PacketListenException;
import exceptions.PacketSendException;
import pool.ServerListenerPool;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

/**
 * Created by Enes Recep on 15.12.2018.
 */
public class Communication {

    private String oppositeAddr;
    private DatagramPacket handshakePacket;
    private boolean isCommunicationStartedByUs;
    private HandshakeCommunication handshakeCommunication;
    private MessageCommunication messageCommunication;
    private PortHandler portHandler = new PortHandler();
    private PacketHandler packetHandler = new PacketHandler();
    private SocketHandler socketHandler = new SocketHandler();

    private int[] messagePortsListen;
    private int[] messagePortsSend;

    private int[] ackPortsListen;
    private int[] ackPortsSend;

    private boolean isHandshakeDone = false;

    private PacketType packetType;

    public Communication() throws SocketException {
        handshakeCommunication = new HandshakeCommunication(this);
        messageCommunication = new MessageCommunication(this);
    }

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

            handshakeCommunication.setState(true);
            handshakeCommunication.waitHandshakePacket();

            Thread thread = new Thread(handshakeCommunication);
            thread.start();

        } catch (PacketListenException e) {
            e.printStackTrace();
        }

        Packet packet = waitForMessage(messagePortsListen);
        //System.out.println("[INCOMING MESSAGE] " + packet.getSerializedData());


        return packet.getSerializedData();
    }

    public Object communicationWait(String oppositeAddr) {
        try {

            handshakeCommunication.setState(true);
            Packet handShakeACKPacket = handshakeCommunication.sendHandshake(oppositeAddr);
            if (handShakeACKPacket.getPacketTypeFlag().equals(PacketTypeFlag.ACK_PACKET))
                isHandshakeDone = true;
        } catch (PacketListenException e) {
            e.printStackTrace();
        } catch (PacketSendException e) {
            e.printStackTrace();
        }
        Packet packet = waitForMessage(messagePortsListen);
        return packet.getSerializedData();
    }

    public void send(Object data, String oppositeAddr) {

        messageCommunication.setAddress(oppositeAddr);
        Packet handShakeACKPacket = null;
        System.out.println("[Sending packet with data ]" + data);

        handshakeCommunication.setState(true);

        try {

            handShakeACKPacket = handshakeCommunication.sendHandshake(oppositeAddr);
            if (handShakeACKPacket.getPacketTypeFlag().equals(PacketTypeFlag.ACK_PACKET)){
                isHandshakeDone = true;
                //terminate threads, close sockets
            }


            System.out.println("Getting ACK");

            messageCommunication.setState(true);
            if(ackPortsListen == null){
                System.out.println("In comm messageportslisten null");
            }
            messageCommunication.setMessagePortsListen(messagePortsListen);
            messageCommunication.setMessagePortsSend(handShakeACKPacket.getMessagePorts());

            messageCommunication.startMessageCommunication(data, true);

        } catch (PacketSendException e) {
            e.printStackTrace();
        } catch (PacketListenException e) {
            e.printStackTrace();
        } catch (CommunicationDataException e) {
            e.printStackTrace();
        }
    }

    public Packet waitForMessage(int[] ports) {

        int i = 0;
        while(true) {
            System.out.println(i);
            System.out.println("[WAITING MESSAGE IN PORTS]" + ports[0] + " " + ports[1] + " " + ports[2]);
            DatagramPacket receivedMessage = null;
            ServerListenerPool messagePool = new ServerListenerPool();
            receivedMessage = messagePool.threadPoolRunner(ports, Constants.MESSAGE_TIMEOUT);

            if (receivedMessage == null) {
                System.out.println("[RECEIVED MESSAGE IS NULL]");
            }else{
                System.out.println("[RECEIVED MESSAGE IS NOT NULL]");
                //Packet receivedPacket = new Packet(receivedMessage);
                System.out.println("[INCOME DATA SZIE] " + receivedMessage.getData().length);
                messageCommunication.sendMessageACK(receivedMessage);
                ports = getMessagePortsListen();
                //setMessagePortsListen(ports);
                //Packet packet = packetHandler.addPacket(receivedMessage);
                System.out.println("[LAST BIT OF PACKET "+i+" ] = " + new Packet(receivedMessage).getLast());
                System.out.println("[ORDER BIT OF PACKET "+i+" ] = " + new Packet(receivedMessage).getOrder());


                Packet packet = addPacket(receivedMessage);

                if(packet != null){
                    System.out.println("[PACKET IS ] NOT NULL");
                    System.out.println("[WAIT FOR MESSAGE] " + packet.getSerializedData());
                    return packet;
                }
                System.out.println("[PACKET IS]  NULL " );
            }
            i++;
        }
    }

    public Packet addPacket(DatagramPacket datagramPacket){

        Packet packet = packetHandler.addPacket(datagramPacket);

        if(packet != null)
            return packet;
        return null;

    }

    /*
    This method will be called when we or the other user wants to close the communication. Messages' flags will be checked before we send packet and when we receive packet.
    If a received message's flag is FINPacket after ACK is sent and FIN_TIMEOUT passes this emthod will be called.
    If we send a FINPacket after sending the packet this method will be called.
     */
    public void FINProcedure() {

        SocketHandler socketHandler = null;
        try {
            socketHandler = new SocketHandler();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        socketHandler.getSocket().close();

        //CommunicationPool.getInstance().removeCommunication(this);

    }
}
