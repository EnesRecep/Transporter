package Model;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import enums.BitTypeFlag;

/**
 * Created by Enes Recep on 22.11.2018.
 */


public interface PacketType {

    public DatagramPacket createPacket(Object data, String addr, int destPort, int order, int partition, int fin, int last);

}

class HandshakePacket implements PacketType{

    public DatagramPacket createPacket(Object data, String addr, int destPort, int order, int partition, int fin, int last) {
        
    	Packet p = new Packet();
    	DatagramPacket packet = null;
    	
    	try
    	{
    		if(data instanceof String)
        	{
        		StringBuilder userMessage = new StringBuilder();
        		
        		userMessage.append(p.toBinary(order, BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(partition, BitTypeFlag.TO_4_BIT));
        		userMessage.append(p.toBinary(fin, BitTypeFlag.TO_1_BIT));
        		userMessage.append(p.toBinary(last, BitTypeFlag.TO_1_BIT));
        		userMessage.append(p.toBinary(0, BitTypeFlag.TO_2_BIT));
        		
        		Server s = new Server();
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		
        		userMessage.append((String)data);
        		
        		packet = new DatagramPacket(userMessage.toString().getBytes(), userMessage.toString().length());
        		packet.setAddress(InetAddress.getByName(addr));
        		packet.setPort(destPort);
        	}
    	}
    	catch(UnknownHostException ex)
    	{
    		ex.printStackTrace();
    	}
    	
    	return packet;
    }
}

class HSACKPacket implements PacketType{

    public DatagramPacket createPacket(Object data, String addr, int destPort, int order, int partition, int fin, int last) {

    	Packet p = new Packet();
    	DatagramPacket packet = null;
    	
    	try
    	{
    		if(data instanceof String)
        	{
        		StringBuilder userMessage = new StringBuilder();
        		
        		userMessage.append(p.toBinary(order, BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(partition, BitTypeFlag.TO_4_BIT));
        		userMessage.append(p.toBinary(fin, BitTypeFlag.TO_1_BIT));
        		userMessage.append(p.toBinary(last, BitTypeFlag.TO_1_BIT));
        		userMessage.append(p.toBinary(1, BitTypeFlag.TO_2_BIT));
        		
        		Server s = new Server();
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		
        		userMessage.append((String)data);
        		
        		packet = new DatagramPacket(userMessage.toString().getBytes(), userMessage.toString().length());
        		packet.setAddress(InetAddress.getByName(addr));
        		packet.setPort(destPort);
        	}
    	}
    	catch(UnknownHostException ex)
    	{
    		ex.printStackTrace();
    	}
    	
    	return packet;
    }
}

class MessagePacket implements PacketType{

    public DatagramPacket createPacket(Object data, String addr, int destPort, int order, int partition, int fin, int last) {
        
    	Packet p = new Packet();
    	DatagramPacket packet = null;
    	
    	try
    	{
    		if(data instanceof String)
        	{
        		StringBuilder userMessage = new StringBuilder();
        		
        		userMessage.append(p.toBinary(order, BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(partition, BitTypeFlag.TO_4_BIT));
        		userMessage.append(p.toBinary(fin, BitTypeFlag.TO_1_BIT));
        		userMessage.append(p.toBinary(last, BitTypeFlag.TO_1_BIT));
        		userMessage.append(p.toBinary(2, BitTypeFlag.TO_2_BIT));
        		
        		Server s = new Server();
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		
        		userMessage.append((String)data);
        		
        		packet = new DatagramPacket(userMessage.toString().getBytes(), userMessage.toString().length());
        		packet.setAddress(InetAddress.getByName(addr));
        		packet.setPort(destPort);
        	}
    	}
    	catch(UnknownHostException ex)
    	{
    		ex.printStackTrace();
    	}
    	
    	return packet;
    	
    }
}

class MessageACKPacket implements PacketType{

    public DatagramPacket createPacket(Object data, String addr, int destPort, int order, int partition, int fin, int last) {
        
    	Packet p = new Packet();
    	DatagramPacket packet = null;
    	
    	try
    	{
    		if(data instanceof String)
        	{
        		StringBuilder userMessage = new StringBuilder();
        		
        		userMessage.append(p.toBinary(order, BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(partition, BitTypeFlag.TO_4_BIT));
        		userMessage.append(p.toBinary(fin, BitTypeFlag.TO_1_BIT));
        		userMessage.append(p.toBinary(last, BitTypeFlag.TO_1_BIT));
        		userMessage.append(p.toBinary(3, BitTypeFlag.TO_2_BIT));
        		
        		Server s = new Server();
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		userMessage.append(p.toBinary(s.getPort(), BitTypeFlag.TO_16_BIT));
        		
        		userMessage.append((String)data);
        		
        		packet = new DatagramPacket(userMessage.toString().getBytes(), userMessage.toString().length());
        		packet.setAddress(InetAddress.getByName(addr));
        		packet.setPort(destPort);
        	}
    	}
    	catch(UnknownHostException ex)
    	{
    		ex.printStackTrace();
    	}
    	
    	return packet;
    	
    }
}






