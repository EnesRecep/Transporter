package Utils;

import org.junit.Test;

/**
 * The test class of PortHandler
 *
 * @author 19XLR95
 * @version 1.0
 * @since 23.11.2018
 **/
public class PortHandlerTest
{
  /**
   * Test 'isPortAvailable' function ability
   **/
  @Test
  public void isPortAvailableTest()
  {
    PortHandler portHandler = new PortHandler();

    int port = 1995;

    if(portHandler.isPortAvailable(port))
      System.out.println(port + " is available!");
    else
      System.out.println(port + " is not available!");
  }
}
