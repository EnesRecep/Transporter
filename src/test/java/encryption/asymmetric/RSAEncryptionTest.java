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
  private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuRND2h4DAtQfZ9adqr0Mkm3gaKXpl2h0\n" +
          "gVwtlEqhL8RPPdgZO85gVJLw6OMznuOEZEtqbGrSTKiQ5vAelig6K/rxvlPWdaQsdB5qv7wulvFg\n" +
          "A4ZY9+znAjoRIbalJQrvM053m/yKKS8VJMKcmkwlGc7RNMmiierAeWMQkhPy28XZ9A2t2PCCynGu\n" +
          "00AIyIU2al9heC6B/M9BGQvcJV+9Tpm0RXDD6s01l5o6M6auZUwMAeBQL0New1MsXw7Ycq6YikYR\n" +
          "rxdJHMmdV8e3t9r1oE1LioWTuUEXuAaPXX6/9L1jVdmX1aQmo039mw+aNAljVFBNWdEHQ1A36k5q\n" +
          "FJ7lFwIDAQAB";

  private String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC5E0PaHgMC1B9n1p2qvQySbeBo\n" +
          "pemXaHSBXC2USqEvxE892Bk7zmBUkvDo4zOe44RkS2psatJMqJDm8B6WKDor+vG+U9Z1pCx0Hmq/\n" +
          "vC6W8WADhlj37OcCOhEhtqUlCu8zTneb/IopLxUkwpyaTCUZztE0yaKJ6sB5YxCSE/Lbxdn0Da3Y\n" +
          "8ILKca7TQAjIhTZqX2F4LoH8z0EZC9wlX71OmbRFcMPqzTWXmjozpq5lTAwB4FAvQ17DUyxfDthy\n" +
          "rpiKRhGvF0kcyZ1Xx7e32vWgTUuKhZO5QRe4Bo9dfr/0vWNV2ZfVpCajTf2bD5o0CWNUUE1Z0QdD\n" +
          "UDfqTmoUnuUXAgMBAAECggEAHuwvHBSDoEdc7/aV5V8s6JJfOlzuSJP1U1/ZZbuKbva33rdqj+mN\n" +
          "gtQCWIcNqvCMwE0xfOfkN1a6zzszgRc7kPmexpQvcvx5SPWEFIEWr7Wouy4JU3NLrb2R+zYPJb9L\n" +
          "9CCaL3/kfbkWKUwy6jzqvgMmRVGjTLPicOWycRXQMBP38VOGrL9S+MLMTW+6XDCgMM3rGo7LHKnL\n" +
          "fxE/reAm1xJRaqG/bQ3kqfXKJ+NyH3gdp0QaDGehorN/GWPDr2ofyk77nL7k9QJ+gRwm5QWHpkwy\n" +
          "ChAqF/hOqJjlifApufjk3It4qXRqTEXi88y6vG/AS/j/Ix1qEKJDccweDpHWcQKBgQDfgSXZjlqa\n" +
          "CdbTlsZ3FtRvMfVhEbaJWvsgZtuGPT4i3aWFC0X+BVVuAfLaCDW3JAn3fDDBSHE9X0Ec2ZqBtH24\n" +
          "jYWX4Spss42EsUtEXZT4/h1ITlUquP2P6EYO2JDSf9NXoeErhijy72hoNqv1yNLPCbGSIjyI45Jh\n" +
          "SBPBD3hkiwKBgQDT+8dVGmzEyyF+mmk1ZU/LEV5caKstzwW79Yy2P6qKa+B86rKyeRlJzxvY3A42\n" +
          "c0iZnCKlSXDfdFDcfNSuktnIjStR1SkvjIzD4ymkX9PhtHk6T1ro1ZB6vrIY7kg5n08p6/qLtc8v\n" +
          "YoiZm5IzYKJuzEvln750adVnEVYcTyu3JQKBgQCyJ1CTRJxFcphRmjpiTMlSRQExuZvFisbnwfUz\n" +
          "4Hc59XDtRtooq/m3sUfxsV+XtttW8SAvwN26rMXLCEzXHJal1/Dn6mwVr12bqnKzcODBRFT1ALN0\n" +
          "VXaQEfZd+RQfJhLBJGDGf67+TgTsJMAXL1eR/kkTkcSs3LEeaASO/BTcFwKBgQC0zjl0UveW2pMV\n" +
          "Aurdii4GzCeptfTbO4puiL+d6quxKNXuddg49aUmGSptSzQsKSQxMMU0KXHGhdU6Wpak18g9kHr0\n" +
          "b1WTotOleKnZ84orJt2HaIvnr9BtgR0yunqL+19wKEAozceLaQWNzu1iOPgSfNn3Xsr5TBOit1y1\n" +
          "BKoKqQKBgBXTD8zDt2dL/yz87DPE8Jdn+Mz0vQu4STNlVC8YcWrwDDKFEFxivN87Fmzv83DTEQYM\n" +
          "nfXvrrPvWomwaUIOUQij18SJ/MiCMSmT4MYuN8PjJKspcJRnn+vvKY3sbJk4aLaEZ8RkexmjO05x\n" +
          "/xEM4Av3vIc6YHRwY3t4IN931dLu";

  private String encryptedText = "TnNqpMQnycxTCb2nqWagSUVpNeql83aQrYqvFU2qzk8ScZBg96syNPtw1rjdXoDQrrU2ic+/QDj4\n" +
          "yZxf5wA6FeGylArfyrc/dohMwH3wCCCbhuCV/V2Om1dMKtTfiwdA0bRO/dnqunU7YMprlQEL53J8\n" +
          "zWrSxcsHs9VxBWNUAtpvli7ueShw0XCIBZQfeqU9CG8BYUAmH7aXYpXkciQdA10jBP1noombEMrV\n" +
          "UqlgJ3VcygJmrEC/KkdZNEeTtziyicfSKCsNBsbzVZV/+o9Dr1jGkwhRzjDzxsmIGQ54DC99tTxz\n" +
          "8P35eLCB3Qq01I+bis5pH7NKs8KKOEBCyIEeUA==";

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

    // RSA algorithm encrypts a text with random pattern and the result is always different so the test always fails
    //assertEquals(encryptedText, rsaEncryption.encryptWithPublicKey(publicKey, decrptedText));

    assertEquals(decrptedText, rsaEncryption.decryptWithPrivateKey(privateKey, encryptedText));
  }
}
