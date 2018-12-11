package pool;

import communication.Constants;

import java.net.DatagramPacket;
import java.util.ArrayList;

/**
 * Created by Enes Recep on 11.12.2018.
 */
public class ServerListenerPool {

    int[] ports;
    boolean found = false;
    DatagramPacket receivedPacket;
    ServerListenerPool s;
    ArrayList<ServerListener> listeners;
    int maxAttempt = Constants.ACK_TIMEOUT * 100; //Timeout in millis / 10 milliseconds

    public ServerListenerPool(){
        threadPoolRunner(ports);

    }

    public DatagramPacket threadPoolRunner(int[] ports){


        for(int i = 0 ; i < ports.length ; i++) {
            ServerListener listener = new ServerListener(ports[i],s);
            Thread thread = new Thread(listener);
            listeners.add(listener);
            thread.start();

        }

        while(true){
            if(found){
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
            s.execution = false;
        }
    }

}
