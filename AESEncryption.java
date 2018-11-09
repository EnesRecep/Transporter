package onder_encrypt;


import java.security.InvalidKeyException;
import java.util.Base64;
import javax.crypto.*;
import java.security.NoSuchAlgorithmException;
/* Operations:
 * 	Generate a secretKey (Symmetric Key)
 * 	Encrypt the input string(plainText) with this secretKey
 * 	Decrypt the encrypted plainText with this secretKey 
 * 	Provider is SunJCE for AES algorithm
 */
public class AESEncryption
{
  static Cipher cipher; // "AES" must be used for an instance.
  
  
/**
 *  Uses KeyGenerator and an algorithm to generate the secretKey (keygGenerator.generateKey())
 * @param keySize keySize must be entered. Ideal is 128 bits.
 * @param keyType the type of algorithm. AES will be used.
 * @return keyGenerator.generateKey();
 */
  public static SecretKey generateNewSecretKey(int keySize, String keyType)
  {
    try
    {
      KeyGenerator keyGenerator = KeyGenerator.getInstance(keyType);
      keyGenerator.init(keySize);
      
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
  public static String encrypt(String plainText, SecretKey secretKey)
  {
    try
    {
      byte[] plainTextByte = plainText.getBytes();

      cipher.init(Cipher.ENCRYPT_MODE, secretKey);

      byte[] encryptedByte = cipher.doFinal(plainTextByte);

      Base64.Encoder encoder = Base64.getEncoder();

      return encoder.encodeToString(encryptedByte);
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

    return null;
  }
/**Instantiate Cipher with DECRYPT_MODE, this uses the same secretKey to decrypt the bytes.
 * 
 * @param encryptedText that is encrytped with secretKey 
 * @param secretKey is the key to decrypt the encrytedText
 * @return decryptedText
 */
  public static String decrypt(String encryptedText, SecretKey secretKey)
  {
    try
    {
      Base64.Decoder decoder = Base64.getDecoder();

      byte[] encryptedTextByte = decoder.decode(encryptedText);

      cipher.init(Cipher.DECRYPT_MODE, secretKey);

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

    return null;
  }
}
