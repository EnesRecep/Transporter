package Model;

import enums.BitTypeFlag;

/**
 * Created by Enes Recep on 22.11.2018.
 */
public class Packet {
	
	public String toBinary(int i, BitTypeFlag f)
	{
		String binary = "";
		
		if(f == BitTypeFlag.TO_16_BIT)
		{
			binary = Integer.toBinaryString(i);
			
			if(binary.length() < 16)
			{
				int k = 16 - binary.length();
				
				for(int j = 0; j < k; j++)
				{
					binary = "0" + binary;
				}
			}
		}
		else if(f == BitTypeFlag.TO_4_BIT)
		{
			binary = Integer.toBinaryString(i);
			
			if(binary.length() < 4)
			{
				int k = 4 - binary.length();
				
				for(int j = 0; j < k; j++)
				{
					binary = "0" + binary;
				}
			}
		}
		else if(f == BitTypeFlag.TO_2_BIT)
		{
			binary = Integer.toBinaryString(i);
			
			if(binary.length() < 2)
			{
				int k = 2 - binary.length();
				
				for(int j = 0; j < k; j++)
				{
					binary = "0" + binary;
				}
			}
		}
		else if(f == BitTypeFlag.TO_1_BIT)
		{
			binary = Integer.toBinaryString(i);
		}
		
		return binary;
	}

}
