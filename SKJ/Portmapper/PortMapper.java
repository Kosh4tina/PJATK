import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PortMapper {
    static int PORT = 3333; //ip=172.23.65.119
    private void listenSocket() {
        ServerSocket server = null;
        Socket client = null;
        try {
            server = new ServerSocket(PORT);
        }
        catch (IOException e) {
            System.out.println("Could not listen");
            System.exit(-1);
        }

        while(true) {
            try {
                client = server.accept();
            }
            catch (IOException e) {
                System.out.println("Accept failed");
                System.exit(-1);
            }

            (new PortMapperThread(client)).start();
        }

    }

    public static void main(String[] args) {
        PortMapper server = new PortMapper();
        server.listenSocket();
    }
}