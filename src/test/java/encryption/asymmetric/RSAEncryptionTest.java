package encryption.asymmetric;

import org.junit.Test;

/**
 * The test class of RSAEncryption
 *
 * @author 19XLR95
 * @version 1.0
 * @since 23.11.2018
 **/
public class RSAEncryptionTest
{
  /**
   * Test whole class ability
   **/
  @Test
  public void rsaEncryptionTest()
  {
    RSAEncryption rsaEncryption = new RSAEncryption();

    PublicPrivateKeyPair publicPrivateKeyPair = rsaEncryption.generateNewKeyPair();

    System.out.println("public key: " + publicPrivateKeyPair.getPublicKey());
    System.out.println();

    System.out.println("private key: " + publicPrivateKeyPair.getPrivateKey());
    System.out.println();

    String text = "ömer aydın 19xlr95 seffah_xlr çorumlou bebe 19 qral bebişim";

    System.out.println(text);
    System.out.println();

    String encryptedAESKey = rsaEncryption.encryptWithPublicKey(publicPrivateKeyPair.getPublicKey(), text);
    System.out.println("encrypted aes key: " + encryptedAESKey);
    System.out.println();

    System.out.println("decrypted aes key: " + rsaEncryption.decryptWithPrivateKey(publicPrivateKeyPair.getPrivateKey(),
            encryptedAESKey));
  }
}
