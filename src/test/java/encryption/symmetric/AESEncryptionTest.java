package encryption.symmetric;

import org.junit.Test;

import static org.junit.Assert.*;

public class AESEncryptionTest
{
  private String key = "kpZjtiDKa9cMvFWu3PGZQA==";
  private String text = "önder muhteşem biridir!!!";
  private String encryptedText = "zV4LRYmouMeav3boKNxDk2FRqdkdfZCzqXtGSADCtjE=";

  @Test
  public void generateSecretKeyTest()
  {
    AESEncryption aesEncryption = new AESEncryption();

    assertNotNull(aesEncryption.generateNewSecretKey());
  }

  @Test
  public void buildKeyTest()
  {
    AESEncryption aesEncryption = new AESEncryption();

    assertNotNull(aesEncryption.buildSecretKey(key));
  }

  @Test
  public void encryptTest()
  {
    AESEncryption aesEncryption = new AESEncryption();

    assertEquals(encryptedText, aesEncryption.encrypt(text, key));
  }

  @Test
  public void decryptTest()
  {
    AESEncryption aesEncryption = new AESEncryption();

    assertEquals(text, aesEncryption.decrypt(encryptedText, key));
  }
}
