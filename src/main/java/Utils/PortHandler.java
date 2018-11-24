package Utils;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

/**
 * Created by Enes Recep on 22.11.2018.
 */
public class PortHandler {

    /*
    We want our port number between 65535(The maximum port number) and 1024(First 1024 ports are registered ports so we exclude that range).
    This function will generate a random number between this numbers.
    random.nextInt(param); generates a random number between 0 and "param"
    We send 65535 - 1024 = 64511 as param so we will have a random number between 0 and 64511
    When we add 1024 to this number we will have a number between 1024 and 65535
    */
    private int getRandomPortNumber(){
        Random random = new Random();
        int lowerBound = 1024;
        int higherBound = 65535;
        int randNum = random.nextInt(higherBound-lowerBound) + lowerBound;

        return randNum;
    }

    /**
     * Returns whether specified port is available or not
     *
     * @param portNumber the port number (1025 - 65535) that will be checked for availability
     *
     * @return true, if the port is available, otherwise false
     **/
    public boolean isPortAvailable(int portNumber){
        try
        {
            if(portNumber < 1025 || portNumber > 65535)
                throw new IllegalArgumentException("Invalid port number! The port number has to be a number that is between 1025 and 65535");

            DatagramSocket udpSocket = new DatagramSocket(portNumber);
            udpSocket.close();

            return true;
        }
        catch(SocketException ex)
        {
            return false;
        }
        catch(IllegalArgumentException ex)
        {
            return false;
        }
    }

    /**
     * Returns an available port
     * It will be executed until find an available port
     *
     * @return an available port number
     **/
    public int getPort()
    {
        int port = getRandomPortNumber();

        while(!isPortAvailable(port))
        {
            port = getRandomPortNumber();
        }

        return port;
    }

    public int getSelectedPort(){
        //TODO : find the last set packet to get port numbers

        return 0;
    }

    public int createPortNumberFromDestinationHostname(){

        return 0;
    }

}
