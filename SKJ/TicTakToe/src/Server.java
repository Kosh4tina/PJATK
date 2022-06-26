import java.net.ServerSocket;
public class Server {

    public static void main(String[] args) throws Exception {
        try (ServerSocket listener = new ServerSocket(8902)) {
            System.out.println("Tic Tac Toe Server is Running");
            while (true) {
                Game game = new Game();
                Game.Player playerX = game.new Player(listener.accept(), 'X');
                Game.Player playerO = game.new Player(listener.accept(), 'O');
                playerX.setOpponent(playerO);
                playerO.setOpponent(playerX);
                game.currentPlayer = playerX;
                playerX.start();
                playerO.start();
            }
        }
    }
}

