package exceptions;

/**
 * It can be used for any communication closing exception
 *
 * @author 19XLR95
 * @version 1.0
 * @since 26.11.2018
 **/

public class CommunicationCloseException extends Exception
{
  public CommunicationCloseException()
  {
    super("Communication close exception!");
  }

  public CommunicationCloseException(String message)
  {
    super(message);
  }
}
