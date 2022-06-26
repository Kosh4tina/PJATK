import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class TCPclient {
    public static void main(String[] args) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String address =
                //"localhost"
                //"172.21.217.1"
                "172.23.65.119"
                ;
        int port = 3333;
        try {
            socket = new Socket(address, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (UnknownHostException e) {
            System.out.println("Unknown host");
            System.exit(-1);
        }
        catch  (IOException e) {
            System.out.println("No I/O");
            System.exit(-1);
        }

        try {
            out.println("REGISTER ADDER 172.23.65.119 54584");
            //out.println("LIST");
            //out.println("GET Konstantsin");
            //out.println("CALL Konstantsin 1 2 3 4");
            //out.println("CALL Konstantsin 4 5 bla -6");
            //out.println("CALL Konstantsin 4 5");
            //out.println("CALL Konstantsin 4 5 100 -6");
            System.out.println(in.readLine());
        }
        catch (IOException e) {
            System.out.println("Error during communication");
            System.exit(-1);
        }

        try {
            socket.close();
        }
        catch (IOException e) {
            System.out.println("Cannot close the socket");
            System.exit(-1);
        }

    }
}