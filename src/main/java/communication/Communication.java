package communication;

import Model.PacketType;
import Utils.PacketHandler;
import Utils.PortHandler;
import Utils.SocketHandler;
import exceptions.PacketListenException;
import exceptions.PacketSendException;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 15.12.2018.
 */
public class Communication implements Runnable{

    private String oppositeAddr;
    private DatagramPacket handshakePacket;
    private boolean isCommunicationStartedByUs;
    HandshakeCommunication handshakeCommunication = new HandshakeCommunication();
    PortHandler handler = new PortHandler();
    private PacketType packetType;

    public void setHandshakePacket(DatagramPacket handshakePacket) {
        this.handshakePacket = handshakePacket;
    }

    public String getOppositeAddr() {
        return oppositeAddr;
    }


    public void setOppositeAddr(String oppositeAddr) {

        this.oppositeAddr = oppositeAddr;
    }

    public DatagramPacket getHandshakePacket() {
        return handshakePacket;
    }

    public boolean isCommunicationStartedByUs() {
        return isCommunicationStartedByUs;
    }

    public void setCommunicationStartedByUs(boolean communicationStartedByUs) {
        isCommunicationStartedByUs = communicationStartedByUs;
    }

    @Override
    public void run() {
        if(isCommunicationStartedByUs){
            try {
                handshakeCommunication.sendHandshake(oppositeAddr);
            } catch (PacketSendException e) {
                e.printStackTrace();
            } catch (PacketListenException e) {
                e.printStackTrace();
            }
        }else{
            handshakeCommunication.sendHandshakeACK(handshakePacket);
        }
    }

    /*
    This method will be called when we or the other user wants to close the communication. Messages' flags will be checked before we send packet and when we receive packet.
    If a received message's flag is FINPacket after ACK is sent and FIN_TIMEOUT passes this emthod will be called.
    If we send a FINPacket after sending the packet this method will be called.
     */
    public void FINProcedure(){

        SocketHandler socketHandler = new SocketHandler();
        socketHandler.getSocket().close();

        CommunicationPool.getInstance().removeCommunication(this);

    }

}
