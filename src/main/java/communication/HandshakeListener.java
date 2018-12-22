package communication;

import Utils.PortHandler;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 15.12.2018.
 */
public class HandshakeListener  {

    /*

    DatagramPacket packet;
    PortHandler handler = new PortHandler();
    int port;
    CommunicationPool pool;
    Communication communication = new Communication();
    HandshakeCommunication handshakeCommunication = new HandshakeCommunication();
    MessageCommunication messageCommunication = new MessageCommunication();


    //When handshake arrives, start a new communication
    public HandshakeListener(){
        pool = CommunicationPool.getInstance();
    }
    @Override
    public void run() {
        DatagramPacket tempPacket;
        do {
            tempPacket = communication.waitForPacket(port);
            if(tempPacket.getData() != null){
                packet = tempPacket;
                Communication communication = new Communication();
                communication.setCommunicationStartedByUs(false);
                communication.setHandshakePacket(packet);
                pool.addCommunication(communication);
                Thread thread = new Thread(communication);
                thread.start();
            }
        }while(packet == null );
    }

    */
}
