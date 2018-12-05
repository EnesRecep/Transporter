package encryption.symmetric;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.InvalidKeyException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
/* Operations:
 * 	Generate a secretKey (Symmetric Key)
 * 	Encrypt the input string(plainText) with this secretKey
 * 	Decrypt the encrypted plainText with this secretKey
 * 	Provider is SunJCE for AES algorithm
 */
public class AESEncryption
{
  private Cipher cipher; // "AES" must be used for an instance.


  /**
   *  Uses KeyGenerator and an algorithm to generate the secretKey (keygGenerator.generateKey())
   *
   * @return keyGenerator.generateKey();
   */
  public SecretKey generateNewSecretKey()
  {
    try
    {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128);

      return keyGenerator.generateKey();
    }
    catch(NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }

    return null;
  }
  /** Instantiate Cipher with ENCRYPT_MODE, this uses the secretKey to encrypt the bytes.
   @param plainText the input of the user to be encrypted
   @param secretKey is the key to encrypt the plainText
   @return encrytedText
   **/
  public String encrypt(String plainText, String secretKey)
  {
    try
    {
      SecretKey theKey = buildSecretKey(secretKey);

      byte[] plainTextByte = plainText.getBytes();

      cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, theKey);

      byte[] encryptedByte = cipher.doFinal(plainTextByte);

      return Base64.encode(encryptedByte);
    }
    catch(InvalidKeyException e)
    {
      e.printStackTrace();
    }
    catch(IllegalBlockSizeException e)
    {
      e.printStackTrace();
    }
    catch(BadPaddingException e)
    {
      e.printStackTrace();
    }catch(NoSuchPaddingException e)
    {
      e.printStackTrace();
    }catch(NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }

    return null;
  }
  /**Instantiate Cipher with DECRYPT_MODE, this uses the same secretKey to decrypt the bytes.
   *
   * @param encryptedText that is encrytped with secretKey
   * @param secretKey is the key to decrypt the encrytedText
   * @return decryptedText
   */
  public String decrypt(String encryptedText, String secretKey)
  {
    try
    {
      SecretKey theKey = buildSecretKey(secretKey);

      byte[] encryptedTextByte = Base64.decode(encryptedText);

      cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, theKey);

      byte[] decryptedByte = cipher.doFinal(encryptedTextByte);

      return new String(decryptedByte);
    }
    catch(InvalidKeyException e)
    {
      e.printStackTrace();
    }
    catch(IllegalBlockSizeException e)
    {
      e.printStackTrace();
    }
    catch(BadPaddingException e)
    {
      e.printStackTrace();
    }
    catch(Base64DecodingException ex)
    {
      ex.printStackTrace();
    }catch(NoSuchPaddingException e)
    {
      e.printStackTrace();
    }catch(NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }

    return null;
  }

  public SecretKey buildSecretKey(String key)
  {
    try
    {
      byte[] keyBytes = Base64.decode(key);
      return new SecretKeySpec(keyBytes, "AES");
    }
    catch(Base64DecodingException ex)
    {
      ex.printStackTrace();
    }

    return null;
  }
}
