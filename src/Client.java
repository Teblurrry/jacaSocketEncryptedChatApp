import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; //localhost
        int port = 12345; //server's port

        try (Socket socket = new Socket(serverAddress, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to the server!");
            String message;
            while ((message = console.readLine()) != null) {
                try {
                    //encrypts and send text
                    String encryptedMessage = EncryptionUtils.encrypt(message);
                    out.println(encryptedMessage); //send to server

                    //log the encrypted text sent
                    System.out.println("Encrypted Text Sent: " + encryptedMessage);

                    //receive and decrypt response
                    String encryptedResponse = in.readLine();
                    System.out.println("Encrypted Response Received: " + encryptedResponse);

                    String response = EncryptionUtils.decrypt(encryptedResponse);
                    System.out.println("Decrypted Response" + response); //receive response
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
