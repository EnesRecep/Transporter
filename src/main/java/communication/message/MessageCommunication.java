package communication.message;

import Model.Client;
import Model.Packet;
import Model.Server;
import Utils.Serializer;
import exceptions.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;

/**
 * Represents message communication that starts after handshaking
 * For more information see method descriptions, comments and RFC v2.0
 *
 * @author 19XLR95
 * @version 1.0
 * @since 26.11.2018
 **/

public class MessageCommunication
{
  // The message ports that is used for next packet
  private int[] messagePortsListen;
  private int[] messagePortsSend;

  // The ack ports that is used for related message packet
  private int[] ackPortsListen;
  private int[] ackPortsSend;

  // The data that is used to save sent and received user message
  private Object data;

  // The server that is used to listen message and send related ack
  private Server server;

  // The client that is used to send user message
  private Client client;

  // The state indicates whether communication open and start or not.
  private boolean state;

  // The destination ip address
  private String address;

  // The packet parser that is used to parse packet and get packet properties (fin, last, ports, etc.)
  private Packet packetParser;

  // The switch that is used to change communication operation (send message or listen message)
  private char sendOrListen;

  /**
   * After handshaking first message sending ports have to be specified
   *
   * @param messagePortsSend first message sending ports
   * @param address destination ip address
   **/
  public MessageCommunication(int[] messagePortsSend, String address)
  {
    this.messagePortsSend = messagePortsSend;
    this.address = address;
    state = false;
    data = null;
    packetParser = new Packet();
    sendOrListen = 'S';
  }

  /**
   * After handshaking first message sending ports have to be specified
   *
   * @param messagePortsSend the first message sending ports
   * @param address the destination ip address
   * @param server the server that will be used in communication
   **/
  public MessageCommunication(int[] messagePortsSend, String address, Server server)
  {
    this.messagePortsSend = messagePortsSend;
    this.address = address;
    this.server = server;
    state = false;
    data = null;
    packetParser = new Packet();
    sendOrListen = 'S';
  }

  /**
   * After handshaking first message sending ports have to be specified
   *
   * @param messagePortsSend the first message sending ports
   * @param address the destination ip address
   * @param client the client that will be used in communication
   **/
  public MessageCommunication(int[] messagePortsSend, String address, Client client)
  {
    this.messagePortsSend = messagePortsSend;
    this.address = address;
    this.client = client;
    state = false;
    data = null;
    packetParser = new Packet();
    sendOrListen = 'S';
  }

  public char getSendOrListen()
  {
    return sendOrListen;
  }

  public void setSendOrListen(char sendOrListen)
  {
    this.sendOrListen = sendOrListen;
  }

  public Packet getPacketParser()
  {
    return packetParser;
  }

  public void setPacketParser(Packet packetParser)
  {
    this.packetParser = packetParser;
  }

  public int[] getMessagePortsListen()
  {
    return messagePortsListen;
  }

  public void setMessagePortsListen(int[] messagePortsListen)
  {
    this.messagePortsListen = messagePortsListen;
  }

  public int[] getMessagePortsSend()
  {
    return messagePortsSend;
  }

  public void setMessagePortsSend(int[] messagePortsSend)
  {
    this.messagePortsSend = messagePortsSend;
  }

  public int[] getAckPortsListen()
  {
    return ackPortsListen;
  }

  public void setAckPortsListen(int[] ackPortsListen)
  {
    this.ackPortsListen = ackPortsListen;
  }

  public int[] getAckPortsSend()
  {
    return ackPortsSend;
  }

  public void setAckPortsSend(int[] ackPortsSend)
  {
    this.ackPortsSend = ackPortsSend;
  }

  public Object getData()
  {
    return data;
  }

  public void setData(Object data)
  {
    this.data = data;
  }

  public Server getServer()
  {
    return server;
  }

  public void setServer(Server server)
  {
    this.server = server;
  }

  public Client getClient()
  {
    return client;
  }

  public void setClient(Client client)
  {
    this.client = client;
  }

  public void setCommunicationStartState(boolean state)
  {
    this.state = state;
  }

  public boolean getCommunicationStartState()
  {
    return state;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  /**
   * Creates and sends a communication message packet
   * After sending packet, it listens the packet's ack
   * It implements the RFC v2.0
   **/
  public void sendAndListenACK() throws CommunicationDataException, PacketSendException
  {
    if(data == null)
      throw new CommunicationDataException("The communication data is null!");

    if(!state)
      throw new PacketSendException("The communication is close!");

    for(int i = 0; i < 6; i++)
    {
      try
      {
        client.openSocket();

        DatagramPacket p1 = client.sendPacket(Serializer.serialize(data), address, messagePortsSend[i % 3]);

        packetParser.parsePacket(p1);

        ackPortsListen = packetParser.getAckPorts();
        messagePortsListen = packetParser.getMessagePorts();

        server.openSocket(ackPortsListen[i % 3]);
        DatagramPacket p2 = server.waitForPacket();

        packetParser.parsePacket(p2);

        messagePortsSend = packetParser.getMessagePorts();

        sendOrListen = 'L';

        if(packetParser.getFin() == 1)
        {
          System.out.println("Communication was closed on request!");

          try
          {
            closeCommunication();
          }
          catch(CommunicationCloseException ex)
          {
            ex.printStackTrace();
          }
        }
      }
      catch(SocketTimeoutException ex)
      {
        if(i != 5)
          System.out.println("ACK was not received! Try for " + (i + 1) + ". time.");
        else
        {
          System.out.println("ACK was not received for " + (i + 1) + ". time.");
          System.out.println("Communication failed!");
          System.out.println("Communication is closing!");

          try
          {
            closeCommunication();
          }
          catch(CommunicationCloseException exc)
          {
            exc.printStackTrace();
          }
        }
      }
      catch(IOException ex)
      {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Listens incoming communication message packets
   * After listening the packet, it sends the packet's ack
   * In implements the RFC v2.0
   **/
  public void listenAndSendACK() throws PacketListenException
  {
    if(!state)
      throw new PacketListenException("The communication is close!");

    int fin = 0;
    int last = 0;

    data = null;

    int i = 0;
    while(last == 0)
    {
      server.openSocket(messagePortsListen[i % 3]);
      DatagramPacket p1 = server.waitForPacket();

      i++;

      if(p1 == null)
        continue;

      packetParser.parsePacket(p1);

      data += packetParser.getData().toString();
      fin = packetParser.getFin();
      last = packetParser.getLast();

      ackPortsSend = packetParser.getAckPorts();
      messagePortsSend = packetParser.getMessagePorts();

      server.sendACKPacket(data, address, ackPortsSend[0]);
      server.sendACKPacket(data, address, ackPortsSend[1]);
      DatagramPacket p2 = server.sendACKPacket(data, address, ackPortsSend[2]);

      packetParser.parsePacket(p2);

      messagePortsListen = packetParser.getMessagePorts();
    }

    sendOrListen = 'S';

    try
    {
      if(fin == 1)
      {
        System.out.println("Communication was closed on request!");

        closeCommunication();
      }
    }
    catch(CommunicationCloseException ex)
    {
      ex.printStackTrace();
    }
  }

  /**
   * Starts the communication
   * It switches between sending and listening operations until the communication end
   **/
  public void startCommunication() throws CommunicationStartException, CommunicationDataException,
          PacketSendException, PacketListenException
  {
    if(state)
      throw new CommunicationStartException("The communication has been already started!");

    state = true;

    if(server == null)
      server = new Server();

    if(client == null)
      client = new Client();

    while(state)
    {
      if(sendOrListen == 'S')
        sendAndListenACK();
      else
        listenAndSendACK();
    }
  }

  /**
   * If any user wants to finish the communication via sending packet that contains 'FIN' bit
   * OR
   * If any user's ack is not received for 6 times (see RFC v2.0)
   * It closes the communication
   **/
  public void closeCommunication() throws CommunicationCloseException
  {
    if(!state)
      throw new CommunicationCloseException("The communication is already close!");

    state = false;

    if(server != null)
      server.closeSocket();

    if(client != null)
      client.closeSocket();
  }
}
