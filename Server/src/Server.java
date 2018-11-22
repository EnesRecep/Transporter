import java.io.IOException;
import java.net.*;
import java.util.Random;

/**
 * Created by Tunc on 27.10.2018.
 */
public class Server{

    private DatagramSocket socket;
    private int port = 9500;
    private int milSecTimeout = 60;
    private DatagramPacket recvPacket;
    private byte[] buffer = new byte[256];
    private String recvData = "";

    public Server() throws IOException {
        socket = new DatagramSocket(port);
        System.out.println("Server");
        recvPacket = new DatagramPacket(buffer,buffer.length);
        byte[] data = recvPacket.getData();
    }

    public DatagramPacket conn(){

        try {
            socket.setSoTimeout(milSecTimeout);
            socket.receive(recvPacket);
        } catch (SocketTimeoutException ste){
            ste.getSuppressed();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] data = recvPacket.getData();
        recvData = new String(data);

        while(true){

            port = getNewPort();
            System.out.println(port);

            try {
                socket = new DatagramSocket(port);
                socket.setSoTimeout(milSecTimeout);
                socket.receive(recvPacket);
                break;

            }catch (SocketTimeoutException ste) {
                ste.getSuppressed();
            } catch (IOException e) {
                e.printStackTrace();
            }

            socket.close();
        }

        System.out.println("Connection established");
        System.out.println("PORT " + port);
        return recvPacket;

    }

    public void ackRespond(){
        String ack = "ack";
        byte[] ackBytes = ack.getBytes();

        DatagramPacket responsePacket = new DatagramPacket(ackBytes,ackBytes.length,recvPacket.getAddress(),7777);  //Port 7777 will be received from the received packet later
        try {
            socket.send(responsePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNewPort(){
        //This method will be used to get new port but for now it will just increment the port
        return port + 1;
    }

    /*
    We want our port number between 65535(The maximum port number) and 1024(First 1024 ports are registered ports so we exclude that range).
    This function will generate a random number between this numbers.
    random.nextInt(param); generates a random number between 0 and "param"
    We send 65535 - 1024 = 64511 as param so we will have a random number between 0 and 64511
    When we add 1024 to this number we will have a number between 1024 and 65535
     */
    private int randomPort(){
        Random random = new Random();
        int lowerBound = 1024;
        int higherBound = 65535;
        int randNum = random.nextInt(higherBound-lowerBound) + lowerBound;

        return randNum;
    }
}
