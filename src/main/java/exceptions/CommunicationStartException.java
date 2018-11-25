package exceptions;

/**
 * It can be used for any communication stating exception
 *
 * @author 19XLR95
 * @version 1.0
 * @since 26.11.2018
 **/

public class CommunicationStartException extends Exception
{
  public CommunicationStartException()
  {
    super("Communication start exception!");
  }

  public CommunicationStartException(String message)
  {
    super(message);
  }
}
