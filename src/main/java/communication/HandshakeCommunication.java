package communication;

import Model.*;
import Utils.PacketHandler;
import Utils.PortHandler;
import exceptions.PacketListenException;
import exceptions.PacketSendException;
import pool.ServerListenerPool;

import javax.sound.sampled.Port;
import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 8.12.2018.
 */
public class HandshakeCommunication {

    private boolean isSending;
    private Client client;
    private Server server;
    private boolean state;
    private PortHandler portHandler;
    private PacketHandler packetHandler;
    private PacketHandler packetParser;
    private int[] ackPortsListen;
    private int[] messagePortsListen;
    private int[] messagePortsSend;

    public HandshakeCommunication(){
        packetHandler = new PacketHandler();
    }

    public void sendHandshakeACK(DatagramPacket receivedHandshakePacket){

//        packetParser.parsePacket(receivedHandshakePacket);

        Packet packet = packetHandler.parsePacket(receivedHandshakePacket);
        for(int i = 0 ; i < Constants.MAX_TEST_TIME ; i++){
            client.sendPacket(null, receivedHandshakePacket.getAddress().toString(), packet.getAckPorts()[i%3]);
        }

        //Call Omer's Listen method
    }

    public void sendHandshake(String addr)throws PacketSendException, PacketListenException{

        if(!state)
            throw new PacketSendException("The communication is close!");

        if(isSending){
            for(int i = 0 ; i < Constants.MAX_TEST_TIME ; i++){

                client.openSocket();

                DatagramPacket sendingPacket = client.sendPacket(null, addr, portHandler.createPortNumberFromDestinationHostname(addr)[i%3]);

//                packetParser.parsePacket(sendingPacket);

                Packet sendingPack = packetHandler.parsePacket(sendingPacket);
                ackPortsListen = sendingPack.getAckPorts();
                messagePortsListen = sendingPack.getMessagePorts();

                DatagramPacket receivingPacket = waitHandshakePacket(ackPortsListen);
                if(receivingPacket == null)
                    continue;

                Packet receivedPacket = packetParser.parsePacket(receivingPacket);

                messagePortsSend = receivedPacket.getMessagePorts();

                //CALL OMER's method with messagePortSend
                break;

            }
        }
    }

    public DatagramPacket waitHandshakePacket(int[] ackPorts) throws PacketListenException{

        if(!state)
            throw new PacketListenException("The communication is close!");

        ServerListenerPool ackPool = new ServerListenerPool();
        DatagramPacket receivedACK = ackPool.threadPoolRunner(ackPorts);

        return receivedACK;

    }

    public void  startHandshake(String addr)throws PacketSendException, PacketListenException{


        state = true;

        if(client == null)
            client = new Client();

        client.setPacketType(new HandshakePacket());
        sendHandshake(addr);
    }
}
