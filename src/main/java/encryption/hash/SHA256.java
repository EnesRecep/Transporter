package encryption.hash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tunc on 16.12.2018.
 */
public class SHA256 {

    public SHA256(){

    }

    public String computeHash(byte[] data){

        MessageDigest md = null;
        String hashText = null;

        try {
            md = MessageDigest.getInstance("SHA256");
            byte[] messageDigest = md.digest(data);
            BigInteger bigInteger = new BigInteger(1,messageDigest);
            hashText = bigInteger.toString();

            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashText;
    }
}
