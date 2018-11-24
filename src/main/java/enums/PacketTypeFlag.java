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
  HANDSHAKING_ACK ("01"),

  // Communication message packet type
  MESSAGE_PACKET ("10"),

  // Communication message ack packet type
  MESSAGE_ACK ("11"),

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
  }}
