package exceptions;

/**
 * It can be used for any packet sending exception
 *
 * @author 19XLR95
 * @version 1.0
 * @since 26.11.2018
 **/

public class PacketSendException extends Exception
{
  public PacketSendException()
  {
    super("Packet send exception!");
  }

  public PacketSendException(String message)
  {
    super(message);
  }
}
