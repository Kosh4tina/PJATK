/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad4;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public void listenSocket() {
    ServerSocket server = null;
    Socket client = null;
    try {
      server = new ServerSocket(0);
    }
    catch (IOException e) {
      System.out.println("Could`t listen");
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
    Main server = new Main();
    server.listenSocket();
  }
}
