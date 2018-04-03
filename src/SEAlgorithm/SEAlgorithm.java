package SEAlgorithm;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by jessy on 2017/11/29.
 * According to JCE.jar
 * https://docs.oracle.com/javase/1.5.0/docs/guide/security/jce/JCERefGuide.html#HmacEx
 * https://www.owasp.org/index.php/Using_the_Java_Cryptographic_Extensions
 */
public class SEAlgorithm {

    /**
     * Pseudo-randomstream Encryption
     * Stream Cipher: AES-128
     * Operation Model: OFB
     * Padding Method: PKCS5Padding
     * @param msg
     * @param key secret seed used to generate the secret key to encrypt a message
     * @param IV initial vector
     * @return
     */
    public static String streamEnc(String msg, byte[] key, byte[] IV){
        if((msg == null) || (msg.equals("")))
            return null;
            SecretKey secretKey = ramEncStrGenerator(key);
            String cipher = new String();
            try {
                Cipher aesCipherForEncryption = Cipher.getInstance("AES/OFB/PKCS5Padding");

                aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey,
                        new IvParameterSpec(IV));

                byte[] byteDataToEncrypt = msg.getBytes();
                byte[] byteCipherText = aesCipherForEncryption
                        .doFinal(byteDataToEncrypt);

                cipher = new BASE64Encoder().encode(byteCipherText);

            } catch (NoSuchAlgorithmException e) {
                System.out.println(" No Such Algorithm exists ");
            } catch (NoSuchPaddingException e) {
                System.out.println(" No Such Padding exists ");
            } catch (InvalidKeyException invalidKey) {
                System.out.println(" Invalid Key " + invalidKey);
            }

            catch (BadPaddingException badPadding) {
                System.out.println(" Bad Padding " + badPadding);
            }

            catch (IllegalBlockSizeException illegalBlockSize) {
                System.out.println(" Illegal Block Size " + illegalBlockSize);
            }

            catch (InvalidAlgorithmParameterException invalidParam) {
                System.out.println(" Invalid Parameter " + invalidParam);
            }
            return cipher;
    }

    public static String streamDec(String cipher, byte[] key, byte[] IV) {

        if ((cipher == null) || (cipher.equals("")))
            return null;
        SecretKey secretKey = ramEncStrGenerator(key);
        String plainText = new String();
        Cipher aesCipherForDecryption = null;
        try {
            aesCipherForDecryption = Cipher.getInstance("AES/OFB/PKCS5Padding");
            aesCipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey,
                    new IvParameterSpec(IV));
            byte[] byteCipherText = new BASE64Decoder().decodeBuffer(cipher);
            byte[] byteDecryptedText = aesCipherForDecryption
                    .doFinal(byteCipherText);
            plainText = new String(byteDecryptedText);

        } catch (NoSuchAlgorithmException e) {
            System.out.println(" No Such Algorithm exists ");
        } catch (NoSuchPaddingException e) {
            System.out.println(" No Such Padding exists ");
        } catch (InvalidKeyException invalidKey) {
            System.out.println(" Invalid Key " + invalidKey);
        } catch (BadPaddingException badPadding) {
            System.out.println(" Bad Padding " + badPadding);
        } catch (IllegalBlockSizeException illegalBlockSize) {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
        } catch (InvalidAlgorithmParameterException invalidParam) {
            System.out.println(" Invalid Parameter " + invalidParam);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return plainText;
    }

    /*
    * IV is public
    * */
    public static byte[] generateIV(){

        final int AES_KEYLENGTH = 128;
        byte[] IV = new byte[AES_KEYLENGTH / 8];
        SecureRandom prng = new SecureRandom();
        prng.nextBytes(IV);
        return IV;
    }

    /**
     * Message Authentication Code Generator
     * @param cipher
     * @param key secret seed used to generate the secret key to generate the mac of a message
     * @return
     */
    public static String macGenerator(String cipher, byte[] key){
        SecretKey macKey = ramMacStrGenerator(key);
        String macresult = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(macKey);
            byte[] byteCipher = new BASE64Decoder().decodeBuffer(cipher);
            byte[] result = mac.doFinal(byteCipher);

            macresult = new BASE64Encoder().encode(result);

        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return macresult;
    }

    /**
     * Using a short secret seed to generate a long pseudo-random secret key to encrypt a message
     * @param secKey
     * @return
     */
    private static SecretKey ramEncStrGenerator(byte[] secKey){
        byte[] seed = secKey;
        SecretKey secretKey = null;
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom(seed);
            keyGen.init(128,secureRandom);
            secretKey = keyGen.generateKey();
        }catch (NoSuchAlgorithmException e){
            System.out.println(" No Such Algorithm exists ");
        }
        return secretKey;
    }

    /**
     * Using a short secret seed to generate a long pseudo-random secret key to generate a mac of a message
     * @param macKey
     * @return
     */
    private static SecretKey ramMacStrGenerator(byte[] macKey){
        byte[] seed = macKey;
        SecretKey secretKey = null;
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecureRandom secureRandom = new SecureRandom(seed);
            keyGen.init(256,secureRandom);
            secretKey = keyGen.generateKey();
        }catch (NoSuchAlgorithmException e){
            System.out.println(" No Such Algorithm exists ");
        }
        return secretKey;
    }


    public static void main(String args[]) throws Exception {
        String msg = "message";
        byte[] key = "1234".getBytes();

//        byte[] IV = {100, -85, -55, 85, -72, 17, 16, -70, 64, 101, -128, -25, 76, 74, -96, 67};
        byte[] IV = generateIV();
        String cipher = streamEnc(msg, key, IV);
        System.out.println("The cipher is " + cipher);
        String plaintext = streamDec(cipher, key, IV);
        System.out.println("The plaintext is " + plaintext);

        byte[] key2 = "5678".getBytes();
        String mac = macGenerator(cipher, key2);
        System.out.println("The mac is " + mac);
    }

}
