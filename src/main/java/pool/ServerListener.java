package pool;

import Model.Server;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 11.12.2018.
 */
public class ServerListener implements Runnable {

    Server server = new Server();
    ServerListenerPool pool;
    int port;
    DatagramPacket packet;
    boolean execution;
    boolean found;


    public void setExecution(boolean execution) {
        this.execution = execution;
    }

    public ServerListener(int port, ServerListenerPool pool){
        this.port = port;
        this.pool = pool;
    }

    public DatagramPacket returnPacket(){
        return this.packet;
    }

    @Override
    public void run() {
        DatagramPacket tempPacket = new DatagramPacket(null,1);
        do {

            tempPacket = server.waitForPacket(port);
            if(tempPacket.getData() != null){
                packet = tempPacket;
                pool.notifyFound(packet);
            }
        }while(packet == null  &&  execution );

    }

}
