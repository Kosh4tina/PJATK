import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ProxyThread implements Runnable {
    private Socket client;
    private BufferedReader br;

    ProxyThread(Socket client) throws IOException {
        super();
        this.client = client;
        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void run(){
        ProxyServer.counter++;
        try {
            Socket destination;
            boolean https = false;
            String urlAddress="";
            StringBuilder req=new StringBuilder();
            StringBuilder tmp=new StringBuilder();
            int port=0;
            tmp.append(br.readLine());
            String[] tab;

            while (!tmp.toString().isEmpty()) {
                if(tmp.toString().startsWith("CONNECT")){
                    https=true;
                }
                else if (tmp.toString().startsWith("Host:") ){
                    tab=tmp.toString().split(":");
                    urlAddress=tab[1].trim();
                    port=tab.length==3?Integer.decode(tab[2].trim()):80;
                }
                else if(tmp.toString().startsWith("Proxy-Connection:")){
                    tmp.replace(0,6,"");
                }
                req.append(tmp.toString()).append("\n");
                tmp.setLength(0);
                tmp.append(br.readLine());
            }
            req.append("\n");
            System.out.println(tmp.toString());
            if(!https) {
                destination = new Socket(urlAddress, port);
                destination.getOutputStream().write(req.toString().getBytes());
                destination.getOutputStream().flush();
                transfer(destination,client);
                client.getOutputStream().flush();
                client.close();
                destination.close();
            }else{
                destination = new Socket(urlAddress, port);
                System.out.println(client + ": " + destination);
                client.getOutputStream().write("HTTP/1.0 200 Connection established\r\n Proxy-Agent: ProxyServer/1.0\r\n\r\n".getBytes());
                client.getOutputStream().flush();
                destination.setSoTimeout(3000);
                new Thread(()->{
                    try {
                        transfer(client, destination);

                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }).start();
                transfer(destination, client);
                client.close();
                destination.close();
            }
            br.close();
        } catch (SocketTimeoutException e) {
            try{
                client.getOutputStream().write("HTTP/1.0 Error occurred'Timeout'\nUser-Agent: ProxyServer/1.0\n\r\n".getBytes());
                client.getOutputStream().flush();

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        catch (IOException ignored) {}
        ProxyServer.counter--;
    }

    private void transfer(Socket destination, Socket client) throws IOException {
        byte[] buffer = new byte[4096];
        int read;
        do {
            read = destination.getInputStream().read(buffer);
            if (read > 0) {
                client.getOutputStream().write(buffer, 0, read);
                if (destination.getInputStream().available() < 1) {
                    client.getOutputStream().flush();
                }
            }
        } while (read >= 0);
    }
}
