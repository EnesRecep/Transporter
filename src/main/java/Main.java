import Model.*;
import Utils.PacketHandler;
import Utils.PortHandler;
import Utils.Serializer;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import communication.Communication;
import enums.PacketTypeFlag;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Created by Enes Recep on 22.12.2018.
 */
public class Main {


    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        String a = "000000000000000010000010000100111010000010101010110101000110110000101110101001100111101000100101001001001011010010110110.. .sr .Student....E_... .L .namet .Ljava/lang/String;xpt..((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp";
//        byte[] str = a.getBytes();
//
//        Object oo = Serializer.deserialize(str);
//        System.out.println(oo);

/*

        Student s = new Student("ercan");
        byte[] strrArray = Serializer.serialize(s);

        System.out.println(new String(strrArray));

        Object ob = Serializer.deserialize(strrArray);

        Student er = (Student) ob ;
        System.out.println(er);
        System.out.println( String(strrArray));


        PacketCreator p = new PacketCreator();
        String str = "This is a string";
        DatagramPacket[] packets = p.createPacket(str, "192.168.0.1", 5555, PacketTypeFlag.HANDSHAKING_PACKET);

        PacketHandler h = new PacketHandler();
        Packet pp = h.addPacket(packets[0]);
        System.out.println(new String(pp.getData()));

        Packet packet = new Packet(packets[0]);


        byte[] srtArray = Serializer.serialize(str);

        System.out.println("STR  " + srtArray.length);
        System.out.println("PACKCET  " + packet.getData().length);
        for(int i = 0 ; i < packet.getData().length ; i++){
            System.out.print(packet.getData()[i]);
            System.out.println(" - " + srtArray[i]);
        }



        //System.out.println((String) packet.getSerializedData());
*/
/*
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
*/




        PortHandler portHandler = new PortHandler();
        System.out.println("[My Handshake Listen Ports] "+portHandler.getMyPorts()[0]);

        Communication communication = null;
        try {
            communication = new Communication();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String str = "We will rock you";
        int a = 5;



        //communication.send(str,"192.168.0.101");
        Object o = communication.communicationWait();
        Packet p = (Packet) o;
        Student s = (Student) p.getSerializedData();
        System.out.println("[S]");
        System.out.println(s);

        //String e = (String) o;
        //System.out.println("Incame Data : " + e);
        //communication.send(a,"192.168.0.55");



    }


}
