package pool;

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
    private boolean execution = true;
    private boolean found;


    public void setExecution(boolean execution) {
        this.execution = execution;
    }

    public ServerListener(int port, ServerListenerPool pool) throws SocketException {
        this.port = port;
        this.pool = pool;
    }

    public DatagramPacket returnPacket() {
        return this.packet;
    }

    @Override
    public void run() {
        DatagramPacket tempPacket;
        do {
            if (!execution)
                break;
            //System.out.println(port);
            tempPacket = socketHandler.waitForPacket(port);
            if (tempPacket.getData() != null) {
                packet = tempPacket;
                pool.notifyFound(packet);
            }

        } while (packet == null && execution);

    }

    public void closeSocket() {
        socketHandler.closeSocket();
    }


}
