package pool;

import Utils.PortHandler;
import Utils.SocketHandler;

import java.net.DatagramPacket;
import java.net.SocketException;

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

    public ServerListener(int port, ServerListenerPool pool) throws SocketException {
        this.port = port;
        this.pool = pool;
    }

    public DatagramPacket returnPacket(){
        return this.packet;
    }

    public void closeScoket(){
        socketHandler.closeScoket();
    }

    @Override
    public void run() {
        DatagramPacket tempPacket;
        do {

            System.out.println("THREAD");
            System.out.println(port);
            if(!new PortHandler().isPortAvailable(port)){
                System.out.println("PORT NOT AVAILABLE");
            }
            tempPacket = socketHandler.waitForPacket(port);
            if(tempPacket.getData() != null){
                packet = tempPacket;
                pool.notifyFound(packet);
            }
        }while(packet == null  &&  execution );

    }

}
