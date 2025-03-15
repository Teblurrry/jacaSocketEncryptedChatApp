import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

//logics handling about the encryption and decryption of texts
//the class that both server.java && client.java call for encrypting and decrypting the text
public class EncryptionUtils {
    private static final String SECRET_KEY = "0123456789abcdef"; //16-chae key
    private static SecretKey secretKey;

    //AES secretKey is generated once when the class is loaded
    static {
        try {
            //create the secretKey from the static key
            secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //encrypts the text
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    //decrypts the text
    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
}
