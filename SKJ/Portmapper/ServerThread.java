import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;

    ServerThread(Socket socket) {
        super();
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("question received");

            int res = 0;
            String[] numbers = in.readLine().split(" ");
            try {
                for (String num : numbers) {
                    res += Integer.parseInt(num);
                }
            } catch (Exception e){
                out.println("ERROR! NOT NUMBER!");
                System.err.println("Not number");
                return;
            }
            System.out.println("result ready");
            out.println("Result: " + res);
        } catch (IOException e1) {
            // do nothing
        }

        try {
            socket.close();
        } catch (IOException e) {
            // do nothing
        }
    }
}