import Utils.PortHandler;
import communication.Communication;

import java.net.SocketException;

/**
 * Created by Enes Recep on 22.12.2018.
 */
public class Main {


    public static void main(String[] args) {

        PortHandler portHandler = new PortHandler();
        System.out.println(portHandler.getMyPorts()[0]);

        Communication communication = null;
        try {
            communication = new Communication();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String str = "We will rock you";

        communication.send(str,"192.168.0.55");



    }


}
