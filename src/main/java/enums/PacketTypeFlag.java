package enums;

/**
 * Represents types of packets
 *
 * @author 19XLR95
 * @version 1.0
 * @since 23.11.2018
 **/

public enum PacketTypeFlag
{
  // Handshaking packet type
  HANDSHAKING ("00"),

  // ACK of handshaking packet type
  HANDSHAKING_ACK ("01"),

  // Communication message packet type
  MESSAGE ("10"),

  // ACK of communication message packet type
  MESSAGE_ACK ("11"),

  // Unknown packet type
  UNKNOWN_PACKET ("Unknown packet type!");

  private String packetType;

  PacketTypeFlag(String packetType)
  {
    this.packetType = packetType;
  }

  @Override
  public String toString()
  {
    return this.packetType;
  }
}
