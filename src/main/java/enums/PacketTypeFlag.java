package enums;

/**
 * Represents communication packet types (see RFC v2.0)
 *
 * @author 19XLR95
 * @version 1.0
 * @since 23.11.2018
 **/
public enum PacketTypeFlag
{
  // Communication handshaking packet type
  HANDSHAKING_PACKET ("00"),

  // Communication handshaking ack packet type
  ACK_PACKET ("01"),

  // Communication message packet type
  MESSAGE_PACKET ("10"),

  // Communication message ack packet type
  FIN_PACKET ("11"),

  // Unknown communication packet type
  UNKNOWN_PACKET ("Unknown packet type!");

  private String packetTypeFlag;

  PacketTypeFlag(String packetTypeFlag)
  {
    this.packetTypeFlag = packetTypeFlag;
  }

  @Override
  public String toString()
  {
    return this.packetTypeFlag;
  }

  /**
   * Convert a string to appropriate enumeration value
   *
   * @param packetTypeFlag the string that will be converted to enumeration value
   *
   * @return appropriate enumeration value
   **/
  public static PacketTypeFlag toPacketTypeFlagEnum(String packetTypeFlag)
  {
    if(packetTypeFlag.equals("00"))
      return HANDSHAKING_PACKET;
    else if(packetTypeFlag.equals("01"))
      return ACK_PACKET;
    else if(packetTypeFlag.equals("10"))
      return MESSAGE_PACKET;
    else if(packetTypeFlag.equals("11"))
      return FIN_PACKET;
    else
      return UNKNOWN_PACKET;
  }
}
