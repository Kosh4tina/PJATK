package zad1;

public class ClientLog {
    String clientId;
    StringBuilder fullClientLog;

    public ClientLog(String clientId) {
        this.clientId = clientId;
        fullClientLog = new StringBuilder();
    }
}