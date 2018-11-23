package encryption.asymmetric;

/**
 * Represents public - private key pair
 *
 * @author 19XLR95
 * @version 1.0
 * @since 07.11.2018
 **/
public class PublicPrivateKeyPair
{
  private String publicKey;
  private String privateKey;

  public PublicPrivateKeyPair()
  {
  }

  public PublicPrivateKeyPair(String publicKey, String privateKey)
  {
    this.publicKey = publicKey;
    this.privateKey = privateKey;
  }

  public String getPublicKey()
  {
    return publicKey;
  }

  public void setPublicKey(String publicKey)
  {
    this.publicKey = publicKey;
  }

  public String getPrivateKey()
  {
    return privateKey;
  }

  public void setPrivateKey(String privateKey)
  {
    this.privateKey = privateKey;
  }
}
