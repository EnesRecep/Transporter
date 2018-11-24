package RaspberryPi;

import org.pcap4j.core.*;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.*;

import java.io.EOFException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Sniffer {

    private String ip;
    private InetAddress addr;
    private PcapNetworkInterface nif;


    public Sniffer() {
    }

    /*
    According to given ip address this method listens all network traffic on the host and captures all packets
    with source address or destination address same with given ip address
     */
    public void sniff(int dstPort, int srcPort, String ip) {
        Packet packet;
        int snapLen = 65536;
        PromiscuousMode mode = PromiscuousMode.PROMISCUOUS;
        int timeout = 10;
        PcapHandle handle;

        this.ip = ip;
        try {
            addr = InetAddress.getByName(ip);
            nif = Pcaps.getDevByAddress(addr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                handle = nif.openLive(snapLen, mode, timeout);
                packet = handle.getNextPacketEx();

                IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
                if (ipV4Packet != null) {
                    String protocol = ipV4Packet.getHeader().getProtocol().name();
                    if (ipV4Packet.getHeader().getDstAddr().getHostAddress().equals(ip) || ipV4Packet.getHeader().getSrcAddr().getHostAddress().equals(ip)) {
                        if (protocol.equals("TCP")) {
                            TcpPacket tcpPacket = packet.get(TcpPacket.class);
                            System.out.println(tcpPacket.toString());
                        } else if (protocol.equals("UDP")) {
                            UdpPacket udpPacket = packet.get(UdpPacket.class);
                            System.out.println(udpPacket.toString());
                        }
                    }
                }
                handle.close();
            } catch (PcapNativeException e) {
                e.printStackTrace();
            } catch (EOFException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (NotOpenException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    First 20 byte of TCP packet is TCP header. After this 20 byte comes the data.
    This method extracts the data part from TCP packet
     */
    private byte[] getTCPData(TcpPacket tcpPacket) {
        return Arrays.copyOfRange(tcpPacket.getRawData(), 20, tcpPacket.getRawData().length);
    }
    /*
    First 8 byte of UDP packet is UDP header. After this 8 byte comes the data.
    This method extracts the data part from UDP packet
     */
    private byte[] getUDPData(UdpPacket udpPacket) {
        return Arrays.copyOfRange(udpPacket.getRawData(), 8, udpPacket.getRawData().length);
    }
}
