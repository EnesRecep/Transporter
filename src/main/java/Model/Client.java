package Model;

import java.io.IOException;
import java.net.*;

/**
 * Created by Enes Recep on 22.11.2018.
 */

public class Client {

    private DatagramSocket socket = null;
    private PacketType packetType;

    public Client() {

    }


    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }

    public void openSocket(){
        try {
            socket = new DatagramSocket(getPort());
            //TODO : Make the function name getPort()
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


    public void sendPacket(byte[] byteData, String addr, int port){
        try {
            DatagramPacket packet = packetType.createPacket(byteData, addr, port);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort(){

        //TODO : Omer fill this place
        return 0; //TODO : Delete this token
    }




}
