import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.Random;

public class ServerThread implements Runnable{
    private DatagramSocket serverSocket;
    private boolean running;
    private byte[] buf = new byte[256];

    public ServerThread(int port){
        try {
            serverSocket = new DatagramSocket(port);
        }catch (IOException e){
            System.err.println("Cannot create ServerSocket on this port");
            System.exit(-1);
        }
    }

    public void run(){
        try {
            running = true;

            while(running){
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                serverSocket.receive(packet);
                InetAddress clientAddr = packet.getAddress();

                int clientPort = packet.getPort();
                String clientData = clientAddr.toString() + ":" + clientPort;
                String received = new String(packet.getData(), 0, packet.getLength());
                log("Message " + received);
                log("Client data " + clientData);

                if(received.compareTo(clientData) ==0){
                    log("Access granted");
                    Random random = new Random();
                    Integer randomPort= random.nextInt(65535);

                    if(randomPort<=1024){
                        randomPort = randomPort+1024;
                    }

                    DatagramSocket socket = new DatagramSocket(randomPort);
                    byte[] portData = randomPort.toString().getBytes();

                    DatagramPacket msgBack = new DatagramPacket(portData, portData.length, clientAddr, clientPort);
                    socket.send(msgBack);
                    String fname = "Text.txt";

                    File file = new File("src//Text.txt");
                    byte[] fileName = fname.getBytes();

                    DatagramPacket fileNam = new DatagramPacket(fileName, fileName.length, clientAddr, clientPort);
                    socket.send(fileNam);

                    byte[] fileArr = Files.readAllBytes(file.toPath());
                    DatagramPacket filePack = new DatagramPacket(fileArr, fileArr.length, clientAddr, clientPort);
                    socket.send(filePack);
                }else{
                    log("Not correspondent data");
                }
            }
        }catch (IOException e){
            System.err.println("Can't listen on this socket");
            System.exit(-1);
        }
    }
    public void log(String msg){
        System.out.println(msg);
    }
}
