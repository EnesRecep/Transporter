package Utils;

import org.junit.Test;

import static org.junit.Assert.*;

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

    int port = 1000;

    // The port number should be between 1025 and 65535 (see RFC v2.0)
    assertFalse(portHandler.isPortAvailable(port));

    port = portHandler.getPort();

    // This does not make sense. XD Because the 'getPort' method always returns an available port via 'isPortAvailable' method.
    assertTrue(portHandler.isPortAvailable(port));
  }
}
