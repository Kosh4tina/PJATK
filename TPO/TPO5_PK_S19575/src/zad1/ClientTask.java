/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ClientTask extends FutureTask<String> {

    private ClientTask(Callable<String> callable) {
        super(callable);
    }

    public static ClientTask create(Client client_node, List<String> requests, boolean showSendResult) {
        return new ClientTask(() -> {
            client_node.connect();
            client_node.send("login " + client_node.getClientId());
            requests.stream().map(client_node::send).filter(res -> showSendResult).forEach(System.out::println);
            return client_node.send("bye and log transfer");
        });
    }
}
