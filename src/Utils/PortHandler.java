package Utils;

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

    public int getPortNumber(){
        //TODO : Omer fill this
        return 0;
    }

    public int getSelectedPort(){
        //TODO : find the last set packet to get port numbers

        return 0;
    }

    public int createPortNumberFromDestinationHostname(){

        return 0;
    }

}
