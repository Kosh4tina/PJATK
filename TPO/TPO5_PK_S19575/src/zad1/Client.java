/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    private final InetSocketAddress address;
    private SocketChannel socketChannel;
    private final String c_id;

    public Client(String host, int port, String id) {
        this.address = new InetSocketAddress(host, port);
        this.c_id = id;
    }

    public void connect() {
        try {
            socketChannel = SocketChannel.open(address);
            socketChannel.configureBlocking(false);
        } catch (IOException ignored) {}
    }

    public String send(String req) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            ByteBuffer wrap = ByteBuffer.wrap(req.getBytes());
            socketChannel.write(wrap);
            wrap.clear();
            int tmp = socketChannel.read(wrap);
            if (tmp == 0) {
                do {
                    Thread.sleep(10);
                    tmp = socketChannel.read(wrap);
                } while (tmp == 0);
            }
            while (true) {
                if (tmp == 0) break;
                wrap.flip();
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(wrap);
                stringBuilder.append(charBuffer);
                wrap.clear();
                tmp = socketChannel.read(wrap);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public String getClientId() {
        return c_id;
    }
}