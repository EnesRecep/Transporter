package Model;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Enes Recep on 23.11.2018.
 */
public class ServerTest {

    @Test
    public void openSocketControl(){

        Server server = new Server();
        server.openSocket(7777);
        assertNotNull(server.getSocket());
    }

}
