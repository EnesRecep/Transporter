import Model.*;
import Utils.PacketHandler;
import Utils.PortHandler;
import Utils.Serializer;
import communication.Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Created by Enes Recep on 22.12.2018.
 */
public class Main {


    public static void main(String[] args) {

        PacketType packet = new ACKPacket();
        String rr = "handshakehandshake";
        DatagramPacket p[] = packet.createPacket(rr,"192.168.0.55",5555);

        Packet pp = new Packet(p[0]);


        System.out.println(p[0].getLength());
        try {
            Object e = Serializer.deserialize(Arrays.copyOfRange(p[0].getData(),72, 158));
            System.out.println(e.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }


/*
        PortHandler portHandler = new PortHandler();
        System.out.println(portHandler.getMyPorts()[0]);

        Communication communication = null;
        try {
            communication = new Communication();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String str = "We will rock you";
        int a = 5;

        Object o = communication.communicationWait();
        System.out.println(o.toString());
        //communication.send(a,"192.168.0.55");


*/
    }


}
