import java.io.*;
import java.net.*;

public class Client {
    private static DatagramSocket socket;
    private static InetAddress addr;
    private static byte[] buf;
    public static void main(String[] args){

        if(args.length != 2){
            System.err.println("Waiting for: [IP][PORT]");
            System.exit(-1);
        }else{
            try {
                socket = new DatagramSocket();
                addr = InetAddress.getByName(args[0]);
                String msg = addr.toString() + ":" + socket.getLocalPort();
                buf = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, addr,Integer.parseInt(args[1]));
                socket.send(packet);
                log("Packet have sent");

                byte[] port = new byte[1024];
                DatagramPacket dp = new DatagramPacket(port, port.length);
                socket.setSoTimeout(5000);

                try {
                    socket.receive(dp);
                    String portReceived = new String(dp.getData(), 0, dp.getLength());
                    log(portReceived);

                    byte[] fileName = new byte[1024];
                    byte[] fileBytes = new byte[1024];

                    DatagramPacket fileNamePack = new DatagramPacket(fileName, fileName.length);
                    socket.receive(fileNamePack);
                    String fname = "New" + new String(fileNamePack.getData(), 0, fileNamePack.getLength());
                    log(fname);

                    DatagramPacket fileBytesPack = new DatagramPacket(fileBytes, fileBytes.length);
                    socket.receive(fileBytesPack);
                    File file = new File(fname);
                    OutputStream os = new FileOutputStream(file);
                    os.write(fileBytes);
                    log("Bytes written");
                    os.close();
                    socket.close();

                }catch (SocketTimeoutException e){
                    System.err.println("TIMEOUT");
                    System.exit(-1);
                }
            }catch (IOException e){
                System.err.println("Cannot create client on this IP and PORT");
                System.exit(-1);
            }
        }
    }

    public static void log(String msg){
        System.out.println(msg);
    }
}
