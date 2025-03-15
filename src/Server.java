import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(12345)) { //create a server socket listening on port
            System.out.println("Server is listening...");
            Socket socket = serverSocket.accept(); //client connection accept
            System.out.println("Client connected");

            //input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String encryptedMessage;
            while ((encryptedMessage = in.readLine()) != null) {
                try {
                    //log the encrypted text received
                    System.out.println("Encrypted Text Received: " + encryptedMessage);
                    //decrypt incoming text
                    String message = EncryptionUtils.decrypt(encryptedMessage);
                    System.out.println("Client: " + message);

                    //encrypt and send response
                    String response  = "Server: " + message.toUpperCase();
                    out.println(EncryptionUtils.encrypt(response)); //echo text back in uppercase
                    //log the encrypted text sent
                    System.out.println("Encrypted Response Sent: " + encryptedMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
