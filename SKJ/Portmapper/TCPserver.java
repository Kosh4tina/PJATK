import java.io.*;
import java.net.*;


public class TCPserver {
    public void listenSocket() {
        ServerSocket server = null;
        Socket client = null;
        try {
            server = new ServerSocket(0);
        }
        catch (IOException e) {
            System.out.println("Could not listen");
            System.exit(-1);
        }
        System.out.println("Server listens on port: " + server.getLocalPort());

        while(true) {
            try {
                client = server.accept();
            }
            catch (IOException e) {
                System.out.println("Accept failed");
                System.exit(-1);
            }

            (new ServerThread(client)).start();
        }

    }

    public static void main(String[] args) {
        TCPserver server = new TCPserver();
        server.listenSocket();
    }
}