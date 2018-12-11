package communication;

import Model.*;
import Utils.PortHandler;
import pool.ServerListenerPool;
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
    private Packet packetParser;
    private int[] ackPortsListen;
    private int[] messagePortsListen;
    private int[] messagePortsSend;

    public HandshakeCommunication(){
        
    }

    public void sendHandshake(String addr){

        if(!state)
            throw new PacketSendException("The communication is close!");

        if(isSending){
            for(int i = 0 ; i < Constants.MAX_TEST_TIME ; i++){

                client.openSocket();

                DatagramPacket sendingPacket = client.sendPacket(null, addr, portHandler.calculatePorts());

                packetParser.parsePacket(sendingPacket);

                ackPortsListen = packetParser.getAckPorts();
                messagePortsListen = packetParser.getMessagePorts();

                DatagramPacket receivingPacket = waitHandshakePacket(ackPortsListen);
                if(receivingPacket == null)
                    continue;

                packetParser.parsePacket(receivingPacket);

                messagePortsSend = packetParser.getMessagePorts();

                //CALL OMER's method with messagePortSend
                break;

            }
        }
    }

    public DatagramPacket waitHandshakePacket(int[] ackPorts){

        if(!state)
            throw new PacketListenException("The communication is close!");

        ServerListenerPool ackPool = new ServerListenerPool();
        DatagramPacket receivedACK = ackPool.threadPoolRunner(ackPorts);

        return receivedACK;

    }

    public void  startHandshake(String addr){


        state = true;

        if(server == null)
            server = new Server();

        if(client == null)
            client = new Client();

        client.setPacketType(new HandshakePacket());
        sendHandshake(addr);
    }
}
