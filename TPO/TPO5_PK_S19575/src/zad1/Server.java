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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server {
    private Thread t;
    private final StringBuilder builder;
    private final InetSocketAddress address;
    private final Map<SocketChannel, ClientLog> socketChannelClientLogMap;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public Server(String host, int port) {
        address = new InetSocketAddress(host, port);
        socketChannelClientLogMap = new HashMap<>();
        builder = new StringBuilder();
        createThread();
    }

    private void createThread() {
        t = new Thread(() -> {
            try {
                selector = Selector.open();

                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(address);
                serverSocketChannel.configureBlocking(false);

                serverSocketChannel.register(selector, serverSocketChannel.validOps(), null);

                while (true) {
                    if (!t.isInterrupted()) {
                        selector.select();

                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                        while (true) {
                            if (iterator.hasNext()) {
                                SelectionKey key = iterator.next();
                                iterator.remove();

                                if (key.isAcceptable()) {
                                    SocketChannel clientSocket = serverSocketChannel.accept();
                                    clientSocket.configureBlocking(false);
                                    clientSocket.register(selector, SelectionKey.OP_READ);
                                }

                                if (key.isReadable()) {
                                    SocketChannel clientSocket = (SocketChannel) key.channel();
                                    ByteBuffer buffer = ByteBuffer.allocate(256);
                                    clientSocket.read(buffer);

                                    String clientRequest = new String(buffer.array()).trim();

                                    String clientResponse = requestHandler(clientSocket, clientRequest).toString();

                                    CharBuffer charBuffer = CharBuffer.wrap(clientResponse);
                                    ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
                                    clientSocket.write(byteBuffer);
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }
            } catch (Exception ignored) {}
        });
    }

    private StringBuilder requestHandler(SocketChannel clientSocket, String str) {
        StringBuilder response = new StringBuilder();

        if (!str.matches("login .+")) {
            if (!str.matches(".*\\d{4}-\\d{2}-\\d{2}.*")) {
                if (!str.contains("bye")) {
                    return response;
                }
                socketChannelClientLogMap.get(clientSocket).fullClientLog.append("logged out\n").append("=== ")
                        .append(socketChannelClientLogMap.get(clientSocket).clientId).append(" log end ===\n");
                builder.append(socketChannelClientLogMap.get(clientSocket).clientId).append(" logged out at ").append(LocalTime.now())
                        .append("\n");
                if (!str.equals("bye and log transfer")) {
                    response.append("logged out");
                } else {
                    response.append(socketChannelClientLogMap.get(clientSocket).fullClientLog);
                }
                socketChannelClientLogMap.remove(clientSocket);
            } else {
                String[] parts = str.split(" ");
                socketChannelClientLogMap.get(clientSocket).fullClientLog.append("Request: ").append(str).append("\n").append("Result:\n")
                        .append(Time.passed(parts[0], parts[1])).append("\n");
                builder.append(socketChannelClientLogMap.get(clientSocket).clientId).append(" request at ").append(LocalTime.now())
                        .append(": \"").append(str).append("\"").append("\n");
                response.append(Time.passed(parts[0], parts[1]));
            }
        } else {
            socketChannelClientLogMap.put(clientSocket, new ClientLog(str.substring(6)));
            socketChannelClientLogMap.get(clientSocket).fullClientLog.append("=== ").append(socketChannelClientLogMap.get(clientSocket).clientId)
                    .append(" log start ===\n").append("logged in\n");
            builder.append(socketChannelClientLogMap.get(clientSocket).clientId).append(" logged in at ").append(LocalTime.now())
                    .append("\n");
            response.append("logged in");
        }
        return response;
    }

    public void startServer() {
        t.start();
    }

    public void stopServer() {
        try {
            t.interrupt();
            Thread.sleep(300);
            serverSocketChannel.close();
            selector.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    String getServerLog() {
        return builder.toString();
    }
}