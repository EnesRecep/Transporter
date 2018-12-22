package communication;

import Utils.PortHandler;

import java.util.ArrayList;

/**
 * Created by Enes Recep on 15.12.2018.
 */
public class CommunicationPool {

    /*
    private ArrayList<Communication> communications;
    private static CommunicationPool pool;


    private CommunicationPool(){

    }
    public static CommunicationPool getInstance(){
        if(pool == null)
            pool = new CommunicationPool();

        return pool;

    }

    public void startCommunication(String addr){
        Communication communication = new Communication();
        communication.setOppositeAddr(addr);
        communication.setCommunicationStartedByUs(true);
        //Thread thread = new Thread(communication);

        //thread.start();
    }

    public void waitForCommunication(){
        PortHandler handler = new PortHandler();
        int[] myPorts = handler.getMyPorts();

        for(int i = 0 ; i < myPorts.length ; i++){
            HandshakeListener listener = new HandshakeListener();
            Thread thread = new Thread(listener);
            thread.start();
        }
    }

    public void addCommunication(Communication communication){
        communications.add(communication);
    }

    public void removeCommunication(Communication communication){
        communications.remove(communication);
    }

    */
}
