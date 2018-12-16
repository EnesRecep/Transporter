package enums;

/**
 * Represents max packet size in byte (see RFC v2.0)
 *
 * @author 19XLR95
 * @author Clasmad
 * @version 1.0
 * @since 05.12.2018
 **/
public enum MaxPacketSize
{
  SIZE_2 (2),
  SIZE_4 (4),
  SIZE_8 (8),
  SIZE_16 (16),
  SIZE_32 (32),
  SIZE_64 (64),
  SIZE_128 (128),
  SIZE_256 (256),
  SIZE_512 (512),
  SIZE_1024 (1024);

  private int size;

  MaxPacketSize(int size)
  {
    this.size = size;
  }

  public int getSize()
  {
    return size;
  }
}
