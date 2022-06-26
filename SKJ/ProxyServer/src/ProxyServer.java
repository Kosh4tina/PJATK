import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyServer {
    static int counter = 0; //Multithreaded counter

    private void listenSocket() {
        ServerSocket server = null;
        Socket client = null;
        try {
            server = new ServerSocket(1234);
        } catch (IOException e) {
            System.exit(-1);
        }
        System.out.println("Server listens on port: " + server.getLocalPort());

        while (true) {
            try {
                client = server.accept();
            } catch (IOException e) {
                System.out.println("Error occurred 'Accept failed'");
                System.exit(-1);
            }
            if (counter < 30) {
                try {
                    new Thread(new ProxyThread(client)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        ProxyServer server = new ProxyServer();
        server.listenSocket();
    }
}
