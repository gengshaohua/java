package bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class BIOClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            OutputStream outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String message = scanner.nextLine();
                if ("exit".equals(message)) {
                    break;
                }
                outputStream.write(message.getBytes());
            }
            outputStream.close();;
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
