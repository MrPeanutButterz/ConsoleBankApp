package nl.Bank.Database;

import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import java.util.Arrays;
import java.util.Base64;

public class Crypto {
    private static SecretKeySpec secretKeySpec;
    private static final String ALGORITHM = "AES";

    public void getSecreteKey(String myKey) {
        MessageDigest msgDigest;
        try {
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            msgDigest = MessageDigest.getInstance("SHA-1");
            key = msgDigest.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKeySpec = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public String encryptString(String strToEncrypt, String secret) {
        try {
            getSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: ");
        }
        return null;
    }
    public String decryptString(String strToDecrypt, String secret) {
        try {
            getSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: It seems like someone has tempered with the database...");
            System.out.println("Account is blocked deu to suspicious activities, please contact your bank");
            System.exit(0);
        }
        return null;
    }
}
