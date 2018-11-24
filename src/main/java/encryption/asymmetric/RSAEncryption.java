package encryption.asymmetric;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA encryption operations:
 * 1. Generate new public - private key pair
 * 2. Encrypt a secret key with public key
 * 3. Decrypt an encrypted secret key with private key
 *
 * @author 19XLR95
 * @version 1.1
 * @since 07.11.2018
 **/
public class RSAEncryption
{
  /**
   * Generate new public - private key pair
   *
   * @return new public - private key pair that is stored in an object
   **/
  public PublicPrivateKeyPair generateNewKeyPair()
  {
    try
    {
      KeyPairGenerator newKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
      newKeyPairGenerator.initialize(2048, new SecureRandom());

      KeyPair newKeyPair = newKeyPairGenerator.generateKeyPair();

      return new PublicPrivateKeyPair(Base64.encode(newKeyPair.getPublic().getEncoded()),
              Base64.encode(newKeyPair.getPrivate().getEncoded()));
    }
    catch(NoSuchAlgorithmException ex)
    {
      ex.printStackTrace();
    }

    return null;
  }

  /**
   * Encrypt a text with public key
   *
   * @param publicKey the public key that will be used for text encryption
   * @param textToEncrypt a text that will be encrypted
   *
   * @return encrypted text
   **/
  public String encryptWithPublicKey(String publicKey, String textToEncrypt)
  {
    try
    {
      RSAPublicKey pubKey = buildPublicKey(publicKey);

      Cipher theCipher = Cipher.getInstance("RSA");
      theCipher.init(Cipher.ENCRYPT_MODE, pubKey);

      byte[] encryptedText = theCipher.doFinal(textToEncrypt.getBytes());

      return Base64.encode(encryptedText);
    }
    catch(NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    catch(NoSuchPaddingException e)
    {
      e.printStackTrace();
    }
    catch(InvalidKeyException e)
    {
      e.printStackTrace();
    }catch(BadPaddingException e)
    {
      e.printStackTrace();
    }
    catch(IllegalBlockSizeException e)
    {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Decrypt an encrypted text with private key
   *
   * @param privateKey the private key that will be used for text decryption
   * @param encryptedTextToDecrypt an encrypted text that will be decrypted
   *
   * @return decrypted text
   **/
  public String decryptWithPrivateKey(String privateKey, String encryptedTextToDecrypt)
  {
    try
    {
      RSAPrivateKey priKey = buildPrivateKey(privateKey);

      Cipher theCipher = Cipher.getInstance("RSA");
      theCipher.init(Cipher.DECRYPT_MODE, priKey);

      byte[] encryptedText = Base64.decode(encryptedTextToDecrypt);

      return new String(theCipher.doFinal(encryptedText));
    }
    catch(NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    catch(NoSuchPaddingException e)
    {
      e.printStackTrace();
    }
    catch(InvalidKeyException e)
    {
      e.printStackTrace();
    }
    catch(BadPaddingException e)
    {
      e.printStackTrace();
    }
    catch(IllegalBlockSizeException e)
    {
      e.printStackTrace();
    }
    catch(Base64DecodingException e)
    {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Builds existing public key that is in string format
   *
   * @param publicKey existing public key that is in string format
   *
   * @return public key object
   **/
  public RSAPublicKey buildPublicKey(String publicKey)
  {
    try
    {
      byte[] publicKeyBytes = Base64.decode(publicKey);
      X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
      KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");

      return (RSAPublicKey)publicKeyFactory.generatePublic(publicKeySpec);
    }
    catch(Base64DecodingException ex)
    {
      ex.printStackTrace();
    }
    catch(NoSuchAlgorithmException ex)
    {
      ex.printStackTrace();
    }
    catch(InvalidKeySpecException ex)
    {
      ex.printStackTrace();
    }

    return null;
  }

  /**
   * Builds existing private key that is in string format
   *
   * @param privateKey existing private key that is in string format
   *
   * @return private key object
   **/
  public RSAPrivateKey buildPrivateKey(String privateKey)
  {
    try
    {
      byte[] privateKeyBytes = Base64.decode(privateKey);
      PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
      KeyFactory privateKeyFactory = KeyFactory.getInstance("RSA");

      return (RSAPrivateKey)privateKeyFactory.generatePrivate(privateKeySpec);
    }
    catch(Base64DecodingException ex)
    {
      ex.printStackTrace();
    }
    catch(NoSuchAlgorithmException ex)
    {
      ex.printStackTrace();
    }
    catch(InvalidKeySpecException ex)
    {
      ex.printStackTrace();
    }

    return null;
  }
}
