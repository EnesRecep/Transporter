package exceptions;

/**
 * It can be used for any communication data exception
 *
 * @author 19XLR95
 * @version 1.0
 * @since 26.11.2018
 **/

public class CommunicationDataException extends Exception
{
  public CommunicationDataException()
  {
    super("Communication data exception!");
  }

  public CommunicationDataException(String message)
  {
    super(message);
  }
}
