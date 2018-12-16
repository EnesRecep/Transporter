package pool;

import Utils.SocketHandler;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 11.12.2018.
 */
public class ServerListener implements Runnable {

    private SocketHandler socketHandler = new SocketHandler();
    private ServerListenerPool pool;
    private int port;
    private DatagramPacket packet;
    private boolean execution;
    private boolean found;


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
        DatagramPacket tempPacket;
        do {

            tempPacket = socketHandler.waitForPacket(port);
            if(tempPacket.getData() != null){
                packet = tempPacket;
                pool.notifyFound(packet);
            }
        }while(packet == null  &&  execution );

    }

}
