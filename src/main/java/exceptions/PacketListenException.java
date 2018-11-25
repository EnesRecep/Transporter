package exceptions;

/**
 * It can be used for any packet listening exception
 *
 * @author 19XLR95
 * @version 1.0
 * @since 26.11.2018
 **/

public class PacketListenException extends Exception
{
  public PacketListenException()
  {
    super("Packet listen exception!");
  }

  public PacketListenException(String message)
  {
    super(message);
  }
}
