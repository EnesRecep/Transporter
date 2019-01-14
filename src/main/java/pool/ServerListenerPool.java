package pool;

import communication.Constants;
import enums.PacketTypeFlag;

import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by Enes Recep on 11.12.2018.
 */
public class ServerListenerPool {

    int[] ports;
    boolean found = false;
    DatagramPacket receivedPacket;
    ServerListenerPool s;
    ArrayList<ServerListener> listeners = new ArrayList<>();
    int maxAttempt = Constants.ACK_TIMEOUT * 100; //Timeout in millis / 10 milliseconds

    public ServerListenerPool(){
        //threadPoolRunner(ports);

    }


    public DatagramPacket threadPoolRunner(int[] ports, int timeOut){
        maxAttempt = timeOut * 100;

        if(ports == null){
            System.out.println("ports null");
        }
        for(int i = 0 ; i < ports.length ; i++) {
            ServerListener listener = null;
            try {
                listener = new ServerListener(ports[i],this);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            Thread thread = new Thread(listener);
            listeners.add(listener);
            thread.start();

        }

        while(true){
            if(found){
                terminate();
                return receivedPacket;
            }else{
                try {
                    Thread.sleep(10);
                    maxAttempt--;
                    if(maxAttempt == 0){
                        terminate();
                        return null;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void notifyFound(DatagramPacket packet){
        receivedPacket = packet;
        found = true;
        terminate();

    }

    public void terminate(){
        for(ServerListener s : listeners){
            //s.closeScoket();
            s.setExecution(false);
        }
    }


}
