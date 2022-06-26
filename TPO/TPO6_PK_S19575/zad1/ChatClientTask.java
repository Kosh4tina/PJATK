/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ChatClientTask extends FutureTask<ChatClient> {
    public ChatClientTask(Callable<ChatClient> callable) {
        super(callable);
    }
    public static ChatClientTask create(ChatClient client, List<String> strings, int time_to_wait) {
        return new ChatClientTask(() -> {
            try {
                client.login();
                Thread.sleep(time_to_wait);
                int i = 0;
                while (i < strings.size()) {
                    String msg = strings.get(i);
                    client.send(msg);
                    Thread.sleep(time_to_wait);
                    i++;
                }
                client.logout();
                Thread.sleep(time_to_wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return client;
        });
    }

    public ChatClient getClient() {
        try {
            return this.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
