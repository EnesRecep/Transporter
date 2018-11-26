package RaspberryPi;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Tunc on 26.11.2018.
 */
public class IPListener {
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter out;
    //private BufferedReader in;
    private int port = 30666;

    public IPListener(){
        try {
            serverSocket = new ServerSocket(port);
            //socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        while (true){
            try {
                socket = serverSocket.accept();
                if(socket.isConnected()){
                    System.out.println("Connection established");
                }
                out = new PrintWriter(socket.getOutputStream(), true);
                DataInputStream dis=new DataInputStream(socket.getInputStream());
                String ip = (String)dis.readUTF();
                //if(ip != null){
                System.out.println(ip);
                //}
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}