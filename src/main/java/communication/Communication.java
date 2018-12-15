package communication;

import Utils.PortHandler;

import java.net.DatagramPacket;

/**
 * Created by Enes Recep on 15.12.2018.
 */
public class Communication implements Runnable{

    private String oppositeAddr;
    private DatagramPacket handshakePacket;
    private DatagramPacket handshakeACKpacket;
    private boolean isCommunicationStartedByUs;
    HandshakeCommunication handshakeCommunication = new HandshakeCommunication();
    PortHandler handler = new PortHandler();

    public void setHandshakePacket(DatagramPacket handshakePacket) {
        this.handshakePacket = handshakePacket;
    }

    public String getOppositeAddr() {
        return oppositeAddr;
    }


    public void setOppositeAddr(String oppositeAddr) {

        this.oppositeAddr = oppositeAddr;
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
            handshakeCommunication.sendHandshake(oppositeAddr);
        }else{
            handshakeCommunication.sendHandshakeACK(handshakePacket);
        }
    }
}
