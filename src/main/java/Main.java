import Model.*;
import Utils.PacketHandler;
import Utils.PortHandler;
import Utils.Serializer;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import communication.Communication;

import enums.PacketTypeFlag;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.ArrayList;
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


//        Student student = new Student("ercan");
//        student.setName("((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp");
//        byte[] temp = null;
//        PacketHandler packetHandler = new PacketHandler();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        try {
//            temp = Serializer.serialize(student);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ArrayList<byte[]> divided = packetHandler.dividePacket(temp, MaxPacketSize.SIZE_1024);
//
//        for(byte[] arr:divided){
//            try {
//                baos.write(arr);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        byte[] merged = baos.toByteArray();
//
//        try {
//            Object obj = Serializer.deserialize(merged);
//            Student student1 = (Student) obj;
//            System.out.println(student1.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }


        PortHandler portHandler = new PortHandler();
        System.out.println("[My Handshake Listen Ports] "+portHandler.getMyPorts()[0]);


        Communication communication = null;
        try {
            communication = new Communication();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String str = "We will rock you";
        Student student = new Student("ercan");
        student.setName("((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp((ip.src==192.168.0.100 &&  ip.dst == 192.168.0.55) || (ip.src == 192.168.0.55 && ip.dst == 192.168.0.100)) && udp");
        System.out.println(student.getName().length());
        System.out.println(student);
        int a = 5;


        communication.send(student,"192.168.0.100");



        //communication.send(str,"192.168.0.101");
        Object o = communication.communicationWait();
        Packet p = (Packet) o;
        Student s = (Student) p.getSerializedData();
        System.out.println("[S]");
        System.out.println(s);

        //String e = (String) o;
        //System.out.println("Incame Data : " + e);
        //communication.send(a,"192.168.0.55");


/*

        String data = "data";
        PacketCreator creator = new PacketCreator();
        DatagramPacket[] packets = creator.createPacket("data","192.168.0.101",3131, PacketTypeFlag.HANDSHAKING_PACKET);
        Packet packet = new Packet(packets[0]);

        String newData = null;
        try {
            newData = (String) Serializer.deserialize(packet.getData());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(newData);
        */
//        System.out.println("----------------");
//
//        System.out.println("REAL");
//        try {
//            System.out.println(Serializer.serialize(data).length);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println();
//        Packet packet = new Packet(packets[0]);
//        System.out.println("PARSED");
//        System.out.println(packet.getData().length);
//
//        byte[] byteData = Arrays.copyOfRange(packets[0].getData(),120,packets[0].getData().length);
//        System.out.println("PARSED          REAL");
//        for(int i = 0;i < packet.getData().length;i++){
//            System.out.println(packet.getData()[i] + "          " + byteData[i]);
//        }

//        System.out.println("REAL");
//        for(int i = 0; i < byteData.length;i++){
//            System.out.println(byteData[i]);
//        }
//
//        System.out.println("PARSED");
//        for(int i = 0; i < packet.getData().length;i++){
//            System.out.println(packet.getData()[i]);
//        }
    }


}
