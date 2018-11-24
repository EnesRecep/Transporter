package encryption.asymmetric;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The test class of RSAEncryption
 *
 * @author 19XLR95
 * @version 1.0
 * @since 23.11.2018
 **/
public class RSAEncryptionTest
{
  private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvKcfX1tCISL69jaEvMAd" +
          "96wRMs+sSF6EKKrV4WXaMkxLS4szQJ9df4Y7hab7ZlZ3iUE8XYjaP6hrgXxAShrG" +
          "7fa/O76T8B67Bl9t6SG1ismn3Sxv4itfrrz3Q6KL3kaC8Aj6D4ufs9wCcO8ya7ya" +
          "O5D/Aq9F21PiEykqKaRx9ve1hiGv4ip9sx2ZtxMqVtGbNhCisvhTN95dy3bg7lGZ" +
          "p7sB+JusBF2Bzey+Nqqk5l4KOP/yFKWBNMXx+Ni89DsYARD4lUp1bcCsAK5z3UqW" +
          "l/vcmQkOf6O/CMkXOw2CW2i5ZJEPdoM4hscrdUdNn1R9E/LmeN7ypYzr+ubP9Mi+" +
          "xwIDAQAB";

  private String privateKey = "MIIEpAIBAAKCAQEAvKcfX1tCISL69jaEvMAd96wRMs+sSF6EKKrV4WXaMkxLS4sz" +
          "QJ9df4Y7hab7ZlZ3iUE8XYjaP6hrgXxAShrG7fa/O76T8B67Bl9t6SG1ismn3Sxv" +
          "4itfrrz3Q6KL3kaC8Aj6D4ufs9wCcO8ya7yaO5D/Aq9F21PiEykqKaRx9ve1hiGv" +
          "4ip9sx2ZtxMqVtGbNhCisvhTN95dy3bg7lGZp7sB+JusBF2Bzey+Nqqk5l4KOP/y" +
          "FKWBNMXx+Ni89DsYARD4lUp1bcCsAK5z3UqWl/vcmQkOf6O/CMkXOw2CW2i5ZJEP" +
          "doM4hscrdUdNn1R9E/LmeN7ypYzr+ubP9Mi+xwIDAQABAoIBAAaN8+wXOpv2ICwP" +
          "xtrb/e6N/ub6Ci1Vg1f6X4X+SO94cSFFy9kR8Giu3ECPhaqPlIWjS5qgV5zgmCOe" +
          "kolAlurLqOdFftRX7P2SsTnpXupciUWI9rYv9ZoXnKuv6RYm8wOt/CowGrNgEIGl" +
          "/eV6Xw10wzdvqYiuSGuMAZgCy6bd8x3hQ1aROH2U8XHbX5zzvuM5bQbjmmSylRYw" +
          "bC6w8+DheDM6cyfmcX+7D7HR3mjs73Og1QfI4vnJvSyGpOgvwdSu7L6XAcWb5GXO" +
          "VQiE/Ck2TSn0FoomMtRSOrmdyPpitguzi9hwk7aCCNE+WEisxosh6sF3wnyr8nGM" +
          "iZr8FlkCgYEA3uLMKHN5fQd28SIu25kGALQ6/OfKk9LU320imLHdNKcMuYk0s/eh" +
          "LfOMY7u0ZwEBrx14R0LXiSuIKamk4MNf1KsMWsq8JRzOKlrO5ATsptxdC73X3Al6" +
          "Vj2crJi/1zT+1C8/GVqljywOZCQdQWYSbLdKGppxB4VyPXRrWtj6eR0CgYEA2K5P" +
          "NWDpjDd2pgSqlC+IY6U23sJza/BWwaBhSDkt1nY99SjWHh+rMYrwU6Kywj9glfTv" +
          "XWY4Pap+/s63k+sTebOy1Ski51nd7mZ+cQ5xbCtSjAVv4HlznVoQTd6exnN2U5Eb" +
          "mMtukJxLzHUfiFe4M6zE6oTECztDXjIM7DlotjMCgYEA1iVEEG16QWWFowK70KOC" +
          "v/RqZIlz+nlrq8tYnSrvJOKHeE/agjse8l54+W3kU78E1jJTWOxg19sqdRUYZBdz" +
          "IIrDUQxOnr3BW7Jp62zLzzoUb+6T0kaM8wU5Xh9Y2rWBTW6jioGsZhXnMdH9BdPD" +
          "Sg8EtPDEPny5mVAcwWmNpgUCgYBChlnJQKTTqN+jnwEkF+fe+AV9pOO8+wAX2H6l" +
          "pvB8zLa/Azeh7gUoY8jfVK9fVgf4YkfvPAf+Xs84ugzAvEaxcctxCH0v3HZ30D33" +
          "xgBqvm5MkQrFN2SYpeMllTjYIvDsPTvk0Gh0u5fGARuaR5xFOUEH45UxAZPwYpt3" +
          "FzUf4wKBgQCkJ25iSklFnFoMW8fNq+Q90wfoOIzx9+ew7JD4eHsus+kYJFTjCV3T" +
          "MadVN8/yjZLgDV749TORp0zjA1yNDMqhNx8K9uGRlkGxJhs4+cAV+rzFC9yC/NX0" +
          "/q+jDY5fjkNRu6gE8PLY2tZtBuVsbjppScupfjo6xzBhjqr4Ft1TiA==";

  private String encryptedText = "L/8KdHoH5d3XRqFlV6uSoClmpfvo03lXXZotpmU+LRRaR9tc6QP" +
          "GAAJumbVC1jKMheyAgyLm4MhMmSpGm+mZ9rHEt/8C2c926dypOyNT/vE+stwQ+y9OXtTrONk8K0s" +
          "r+k6Fmoeo9vwuakLfGOEDZoHDcwoa/VR6rtWdLqbKV98HAtswwg8bCwiFBs1Sqf9kaSl7+nkxFeG+" +
          "DXNOqb2ry0QAu/4EDUqbpT66se5NBq3dCAH1H+I9zjlQqT+XG/iAPgRT/38VUVflgfLVDp+xhj/fl7f" +
          "vwVtMdlqyLdl6tA24MbRAqUvP4i78B+ii0AIHgdstK+ItXdI2X8l3Q0HMvw==";

  private String decrptedText = "ömer aydın 19xlr95 seffah_xlr çorumlou bebe 19 qral bebişim";

  /**
   * Test 'generateNewKeyPair' function ability
   **/
  @Test
  public void generateNewKeyPairTest()
  {
    RSAEncryption rsaEncryption = new RSAEncryption();

    assertNotNull(rsaEncryption.generateNewKeyPair());
  }

  /**
   * Test key building functions abilities
   **/
  @Test
  public void buildKeysTest()
  {
    RSAEncryption rsaEncryption = new RSAEncryption();

    assertNotNull(rsaEncryption.buildPublicKey(publicKey));

    assertNotNull(rsaEncryption.buildPrivateKey(privateKey));
  }

  @Test
  public void encryptionTest()
  {
    RSAEncryption rsaEncryption = new RSAEncryption();

    assertEquals(encryptedText, rsaEncryption.encryptWithPublicKey(publicKey, decrptedText));

    assertEquals(decrptedText, rsaEncryption.decryptWithPrivateKey(privateKey, encryptedText));
  }
}
