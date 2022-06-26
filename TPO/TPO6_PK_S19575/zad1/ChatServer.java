/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatServer {
    private final Thread t;
    private final StringBuilder log;
    private final InetSocketAddress address;
    private final Map<SocketChannel, String> clients_map;
    private final Lock reentrantLock = new ReentrantLock();
    private ServerSocketChannel channel;
    private Selector selector;

    public ChatServer(String host, int port) {
        clients_map = new HashMap<>();
        log = new StringBuilder();
        address = new InetSocketAddress(host, port);
        t = serverThread();
    }

    private Thread serverThread() {
        return new Thread(() -> {
            try {
                selector = Selector.open();
                channel = ServerSocketChannel.open();
                channel.bind(address);
                channel.configureBlocking(false);
                channel.register(selector, channel.validOps(), null);

                while (!t.isInterrupted()) {
                    selector.select();
                    if (t.isInterrupted()) break;

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (true) {
                        if (!iterator.hasNext()) break;
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            SocketChannel clientSocket = channel.accept();
                            clientSocket.configureBlocking(false);
                            clientSocket.register(selector, SelectionKey.OP_READ);
                        }
                        if (!key.isReadable()) {
                            continue;
                        } else {
                            SocketChannel clientSocket = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                            StringBuilder clientRequest = new StringBuilder();
                            int readBytes = 0;
                            do try {
                                reentrantLock.lock();
                                readBytes = clientSocket.read(buffer);
                                buffer.flip();
                                clientRequest.append(StandardCharsets.UTF_8.decode(buffer).toString());
                                buffer.clear();
                                readBytes = clientSocket.read(buffer);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            } finally {
                                reentrantLock.unlock();
                            } while (readBytes != 0);

                            String[] parts = clientRequest.toString().split("#");
                            for (String req : parts) {
                                String clientResponse = requestHandler(clientSocket, req).toString();
                                System.out.println(req);

                                Iterator<Map.Entry<SocketChannel, String>> iter = clients_map.entrySet().iterator();
                                while (iter.hasNext()) {
                                    Map.Entry<SocketChannel, String> entry = iter.next();
                                    ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(clientResponse);
                                    entry.getKey().write(byteBuffer);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private StringBuilder requestHandler(SocketChannel clientSocket, String str) throws IOException {
        StringBuilder response = new StringBuilder();

        if (!str.matches("log in .+")) {
            if (!str.matches("log out")) {
                log.append(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss.SSS"))).append(" ")
                        .append(clients_map.get(clientSocket)).append(": ").append(str).append("\n");

                response.append(clients_map.get(clientSocket)).append(": ").append(str).append("\n");
            } else {
                log.append(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss.SSS"))).append(" ")
                        .append(clients_map.get(clientSocket)).append(" logged out").append("\n");

                response.append(clients_map.get(clientSocket)).append(" logged out").append("\n");

                ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(response.toString());
                clientSocket.write(byteBuffer);

                clients_map.remove(clientSocket);
            }
        } else {
            clients_map.put(clientSocket, str.substring(7));

            log.append(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss.SSS"))).append(" ")
                    .append(str.substring(7)).append(" logged in").append("\n");

            response.append(str.substring(7)).append(" logged in").append("\n");
        }
        return response;
    }

    public void startServer() {
        t.start();
        System.out.println("Server started\n");
    }

    public void stopServer() {
        try {
            reentrantLock.lock();
            t.interrupt();
            selector.close();
            channel.close();
            System.out.println("Server stoped");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }

    String getServerLog() {
        return log.toString();
    }
}