/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatClient {
    private final InetSocketAddress address;
    private final StringBuilder client_message;
    private final String ID;
    private SocketChannel socketChannel;
    private final Thread gate = new Thread(this::run);
    private final Lock reentrantLock = new ReentrantLock();

    public ChatClient(String host, int port, String ID) {
        this.ID = ID;
        this.address = new InetSocketAddress(host, port);
        client_message = new StringBuilder(ID + " start!\n");
    }

    public void login() {
        try {
            socketChannel = SocketChannel.open(address);
            socketChannel.configureBlocking(false);
            while (true) {
                if (socketChannel.finishConnect()) break;
            }
            send("log in " + ID);
            gate.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String req) {
        try {
            Thread.sleep(50);
            socketChannel.write(StandardCharsets.UTF_8.encode(req + "#"));
            Thread.sleep(50);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getChatView() {
        return client_message.toString();
    }

    private void run() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        int b_r = 0;
        while (!gate.isInterrupted()) {
            do try {
                reentrantLock.lock();
                b_r = socketChannel.read(buffer);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                reentrantLock.unlock();
            } while (!gate.isInterrupted() && 0 == b_r);
            buffer.flip();
            String response = StandardCharsets.UTF_8.decode(buffer).toString();
            client_message.append(response);
            buffer.clear();
        }
    }

    public void logout() {
        send("log out" + "#");
        try {
            reentrantLock.lock();
            gate.interrupt();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

}