package communication;

import Model.ACKPacket;
import Model.MessagePacket;
import Model.Packet;
import Model.PacketType;
import Utils.PacketHandler;
import Utils.Serializer;
import Utils.SocketHandler;
import enums.PacketTypeFlag;
import exceptions.*;
import pool.ServerListenerPool;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Represents message communication that starts after handshaking
 * For more information see method descriptions, comments and RFC v2.0
 *
 * @author 19XLR95
 * @version 2.0
 * @since 26.11.2018
 **/

public class MessageCommunication {
    // The message ports that is used for next packet
    private int[] messagePortsListen;
    private int[] messagePortsSend;

    // The ack ports that is used for related message packet
    private int[] ackPortsListen;
    private int[] ackPortsSend;

    // The data that is used to save sent user message
    private Object outgoingData;

    private PacketType packetType;

    // The data that is used to save sent user message
    private Object incomigData;

    // The state indicates whether communication open and start or not.
    private boolean state;

    // The destination ip address
    private String address;

    // The packet parser that is used to parse packet and get packet properties (fin, last, ports, etc.)
    private Packet packet;

    // The packet parser
    private PacketHandler packetHandler;

    //SocketHandler
    private SocketHandler socketHandler;

    /*
     * The switch that is used to change communication operation (send message or listen message)
     * True represents send message
     * False represents listen message
     */
    private boolean sendOrListen;

    // The maximum number of trying for waiting ACK
    private final int MAX_TRY = 6;

    private Communication communication;

    public MessageCommunication() {
    }

    public MessageCommunication(Communication communication) {
        this.communication = communication;
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

    public int[] getAckPortsListen() {
        return ackPortsListen;
    }

    public void setAckPortsListen(int[] ackPortsListen) {
        this.ackPortsListen = ackPortsListen;
    }

    public int[] getAckPortsSend() {
        return ackPortsSend;
    }

    public void setAckPortsSend(int[] ackPortsSend) {
        this.ackPortsSend = ackPortsSend;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void startMessageCommunication(Object outgoingData, boolean isCommunicationStartedByUs) throws CommunicationDataException, PacketSendException {

        if (outgoingData == null)
            throw new CommunicationDataException("The communication data is null!");

        if (!state)
            throw new PacketSendException("The communication is close!");

        try {
            socketHandler = new SocketHandler();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        packetType = new MessagePacket();
        System.out.println("Address: " + address);
        DatagramPacket[] packets = packetType.createPacket(outgoingData, address, 5555);

        for (int i = 0; i < packets.length; i++) {
            for (int j = 0; j < Constants.MAX_TEST_TIME; j++) {

                communication.setAckPortsListen(new Packet(packets[i]).getAckPorts());
                packets[i].setPort(messagePortsSend[j % 3]);

                System.out.println("PACKET MESSAGE PORTS SEND:");
                System.out.println(messagePortsSend[j % 3]);
                socketHandler.sendPacket(packets[i]);


                ServerListenerPool ackPool = new ServerListenerPool();

                ackPortsListen = communication.getAckPortsListen();

                if(ackPortsListen == null){
                    System.out.println("message communcaton ackportslisten null");
                }
                DatagramPacket receivedACK = ackPool.threadPoolRunner(ackPortsListen, Constants.MESSAGE_TIMEOUT);

                if (receivedACK == null) {
                    continue;
                }
                System.out.println("ACK RECEIVED");
                try {
                    System.out.println(Serializer.deserialize(new Packet(receivedACK).getData()).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //System.out.println(new Packet(receivedACK).get);
                //if (  ) {

                packetHandler = new PacketHandler();
                messagePortsSend = packetHandler.parsePacket(receivedACK).getMessagePorts();
                if (packetHandler.getPacketTypeFlag(packets[i]).equals(PacketTypeFlag.FIN_PACKET)) {
                     try {
                         new Communication().FINProcedure();
                     } catch (SocketException e) {
                         e.printStackTrace();
                     }

                }
                break;
                //}
            }
        }


    }

    public void sendMessageACK(DatagramPacket receivedHandshakePacket) {
        try {
            SocketHandler s = new SocketHandler();
            packetHandler = new PacketHandler();

            packetType = new ACKPacket();
            Packet packet = packetHandler.parsePacket(receivedHandshakePacket);
            for (int i = 0; i < Constants.MAX_TEST_TIME / 2; i++) {
                ////////////////////////////////////////////
                //TEMP DELETE
                System.out.println("[ACK PACKET for MESSAGE is being sent to ] " + packet.getAckPorts()[i % 3]);
                String a = "This is a message ACK";
                ////////////////////////////////////////////
                DatagramPacket[] ackPackets = packetType.createPacket(a, receivedHandshakePacket.getAddress().toString(), packet.getAckPorts()[i % 3]);
                Packet packetACK = packetHandler.parsePacket(ackPackets[0]);
                communication.setMessagePortsListen(packetACK.getMessagePorts());
                //System.out.println("Sending ACK to :" + ackPackets[0].getAddress().getHostAddress());
                s.sendPacket(ackPackets[0]);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }


        //Call new Listen method
    }

    /*
    public void sendAndListenACK() throws CommunicationDataException, PacketSendException {
        if (outgoingData == null)
            throw new CommunicationDataException("The communication data is null!");

        if (!state)
            throw new PacketSendException("The communication is close!");

        for (int i = 0; i < MAX_TRY; i++) {
            try {
                socketHandler.openSocket();


                DatagramPacket outgoingMessagePacket = packetType.createPacket(outgoingData, address, messagePortsSend[i % 3]);
                socketHandler.sendPacket(outgoingMessagePacket);

                packet = packetHandler.parsePacket(outgoingMessagePacket);

                ackPortsListen = packet.getAckPorts();
                messagePortsListen = packet.getMessagePorts();

                server.openSocket(ackPortsListen[i % 3]);
                DatagramPacket incomingACKPacket = server.waitForPacket();

                packet = packetHandler.parsePacket(incomingACKPacket);

                messagePortsSend = packet.getMessagePorts();

                sendOrListen = false;

                if (packet.getFin() == 1) {
                    System.out.println("Communication was closed on request!");

                    try {
                        closeCommunication();
                    } catch (CommunicationCloseException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (SocketTimeoutException ex) {
                if (i != 5)
                    System.out.println("ACK was not received! Try for " + (i + 1) + ". time.");
                else {
                    System.out.println("ACK was not received for " + (i + 1) + ". time.");
                    System.out.println("Communication failed!");
                    System.out.println("Communication is closing!");

                    try {
                        closeCommunication();
                    } catch (CommunicationCloseException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        }
    }



    public void listenAndSendACK() throws PacketListenException {
        if (!state)
            throw new PacketListenException("The communication is close!");

        int fin = 0;
        int last = 0;

        int i = 0;
        while (last == 0) {
            try {
                server.openSocket(messagePortsListen[i % 3]);
                DatagramPacket incomingMessagePacket = server.waitForPacket();

                i++;

                if (incomingMessagePacket == null)
                    continue;

                packet = packetHandler.parsePacket(incomingMessagePacket);

                incomigData += packet.getData().toString();
                fin = packet.getFin();
                last = packet.getLast();

                ackPortsSend = packet.getAckPorts();
                messagePortsSend = packet.getMessagePorts();

                server.sendACKPacket(outgoingData, address, ackPortsSend[0]);
                server.sendACKPacket(outgoingData, address, ackPortsSend[1]);
                DatagramPacket outgoingACKPacket = server.sendACKPacket(outgoingData, address, ackPortsSend[2]);

                packet = packetHandler.parsePacket(outgoingACKPacket);

                messagePortsListen = packet.getMessagePorts();
            } catch (SocketTimeoutException ex) {
                System.out.println("Waiting for message packet...");
            }
        }

        sendOrListen = true;

        try {
            if (fin == 1) {
                System.out.println("Communication was closed on request!");

                closeCommunication();
            }
        } catch (CommunicationCloseException ex) {
            ex.printStackTrace();
        }
    }


    public void startCommunication() throws CommunicationStartException, CommunicationDataException,
            PacketSendException, PacketListenException {
        if (state)
            throw new CommunicationStartException("The communication has been already started!");

        state = true;

        if (server == null)
            server = new Server();

        if (client == null)
            client = new Client();

        while (state) {
            if (sendOrListen)
                sendAndListenACK();
            else
                listenAndSendACK();
        }
    }


    public void closeCommunication() throws CommunicationCloseException {
        if (!state)
            throw new CommunicationCloseException("The communication is already close!");

        state = false;

        if (server != null)
            server.closeSocket();

        if (client != null)
            client.closeSocket();
    }
    */
}
