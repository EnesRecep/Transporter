package Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

/**
 * Created by Tunc on 26.11.2018.
 */
public class IPNotifier {

    private String raspiAddr = "192.168.0.180";
    private DatagramSocket socket;
    private String ip;


    public IPNotifier(){
        try {
            socket = new DatagramSocket();
            ip = getIP();
            System.out.println("Current ip:" + ip);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void waitForIPChange() throws UnknownHostException {

        System.out.println("Waiting for ip change");

        while (true){
            String newIP = getIP();
            if(!ip.equals(newIP)){
                System.out.println("New IP !!");
                System.out.println(newIP);
                notifyRaspbberyPi(newIP);
            }
        }
    }
    public String getIP() throws UnknownHostException {
        String newIP;
        try {
            socket.connect(InetAddress.getByName("8.8.8.8"),new PortHandler().getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        newIP = socket.getLocalAddress().getHostAddress();
        //System.out.println(newIP);

        return newIP;
    }

    /**
     This method sends a TCP packet to raspberry pi with the new ip address of this host
     **/
    public void notifyRaspbberyPi(String newIP){
        PrintWriter pw;
        Socket socket;
        System.out.println("Notifying raspberry pi");
        try {
            socket = new Socket(raspiAddr,30666);
            pw = new PrintWriter(socket.getOutputStream());
            DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(newIP);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ip = newIP;
    }
}
