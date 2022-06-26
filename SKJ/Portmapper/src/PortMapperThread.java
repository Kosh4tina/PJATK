import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class PortMapperThread extends Thread {
    private Socket client;
    private static HashMap<String, String> list  = new HashMap<>();
    PortMapperThread(Socket client) {
        super();
        this.client = client;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            String line;
            StringBuilder res;
            while((line = in.readLine()) != null && !line.isEmpty()) {
                String[] strings = line.split(" ");
                try {
                    switch (strings[0]) {
                        case "REGISTER":
                            list.put(strings[1], strings[2] + ":" + strings[3]);
                            res = new StringBuilder("Service " + strings[1] + " has been successfully registered on " +
                                    strings[2] + ":" + strings[3]);
                            break;
                        case "GET":
                            res = new StringBuilder(list.get(strings[1]));
                            break;
                        case "LIST":
                            res = new StringBuilder(list.toString());
                            break;
                        case "CALL": {
                            res = call(strings);
                            break;
                        }
                        default:
                            res = new StringBuilder("ERROR0");
                            break;
                    }
                } catch (Exception e) {
                    res = new StringBuilder("Smth went wrong");
                }

                out.println(res.toString());
            }
        } catch (IOException e1) {
            // do nothing
        }

        try {
            client.close();
        } catch (IOException e) {
            // do nothing
        }
    }

    private StringBuilder call(String[] strings){
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        try {
            socket = new Socket(list.get(strings[1]).split(":")[0],
                    Integer.parseInt(list.get(strings[1]).split(":")[1]));

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            return new StringBuilder("ERROR1");
        }
        StringBuilder question = new StringBuilder();
        for(int i = 2; i < strings.length; i++){
            question.append(strings[i]).append(i+1<strings.length?" ":"");
        }
        out.println(question.toString());
        System.out.println("question send");

        String tmpRes;
        StringBuilder res = new StringBuilder();
        try {
            while ((tmpRes = in.readLine()) != null) {
                res.append(tmpRes);
            }
        } catch (IOException e) {
            return new StringBuilder("ERROR2");
        }

        try {
            socket.close();
        } catch (IOException e) {
            return new StringBuilder("ERROR3");
        }
        System.out.println("result send");
        return res;
    }
}