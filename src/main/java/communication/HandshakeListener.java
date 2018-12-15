package communication;

import Model.Server;
import Utils.PortHandler;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 15.12.2018.
 */
public class HandshakeListener implements Runnable {

    Server server = new Server();
    DatagramPacket packet;
    PortHandler handler = new PortHandler();
    int port;
    CommunicationPool pool;

    public HandshakeListener(){
        pool = CommunicationPool.getInstance();
    }
    @Override
    public void run() {
        DatagramPacket tempPacket;
        do {
            tempPacket = server.waitForPacket(port);
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
}
