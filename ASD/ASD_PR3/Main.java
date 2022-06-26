import java.util.*;

public class Main {

    private static char[][] CreateTab() {
        char[][] arr = new char[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int rand = (int)(Math.random()*2);
                arr[i][j] = (char)(rand+48);
            }
        }

        int Si = (int)(Math.random()*10);
        int Sj = (int)(Math.random()*10);
        int Fi = (int)(Math.random()*10);
        int Fj = (int)(Math.random()*10);

        arr[Si][Sj] = 'S';
        arr[Fi][Fj] = 'K';

        if(arr[Si][Sj] == 'K')
            arr = CreateTab();
        return arr;
    }

    private static void Show(char[][] field) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(field[i][j]+ "  ");
            }
            System.out.println();
        }
    }

    public static String FindWay(char[][] arr, int Si, int Sj){
        LinkedList<Node> graph = new LinkedList<>();
        Node Start = new Node(Si, Sj);
        if(Si != 0 && arr[Si-1][Sj] != '0'){
            Start.connected.add(new Node(Si-1, Sj));
        }
        if(Sj != 0 && arr[Si][Sj-1] != '0'){
            Start.connected.add(new Node(Si, Sj-1));
        }
        if(Si != 9 && arr[Si+1][Sj] != '0'){
            Start.connected.add(new Node(Si+1, Sj));
        }
        if(Sj != 9 && arr[Si][Sj+1] != '0'){
            Start.connected.add(new Node(Si, Sj+1));
        }
        graph.add(Start);
        int i = 0;
        while (i + 1 <= graph.size()) {
            Node node = graph.get(i);
            i++;
            if (arr[node.i][node.j] == 'K') return ShowWay(node);
            node.connected.forEach(child -> {
                if (child.i != 0 && arr[child.i - 1][child.j] != '0') {
                    child.connected.add(new Node(child.i - 1, child.j));
                }
                if (child.j != 0 && arr[child.i][child.j - 1] != '0') {
                    child.connected.add(new Node(child.i, child.j - 1));
                }
                if (child.i != 9 && arr[child.i + 1][child.j] != '0') {
                    child.connected.add(new Node(child.i + 1, child.j));
                }
                if (child.j != 9 && arr[child.i][child.j + 1] != '0') {
                    child.connected.add(new Node(child.i, child.j + 1));
                }
                child.parent = node;
                if (!graph.contains(child)) graph.add(child);
            });
        }
        return "The way doesn`t exist!";
    }

    private static String ShowWay(Node exit) {
        List<Node> nodes = new ArrayList<>();
        StringBuilder Result = new StringBuilder();
        Node tmp = exit;
        while (tmp != null){
            nodes.add(tmp);
            tmp = tmp.parent;
        }
        Collections.reverse(nodes);
        Result.append("Start ").append(nodes.get(0)).append(" Koniec ").append(nodes.get(nodes.size()-1)).append("\nThe way exist: ");
        nodes.forEach(Result::append);
        Result.deleteCharAt(Result.length()-1);
        return Result.toString();
    }

    public static void main(String[] args) {

        char[][] table = CreateTab();

        Show(table);

        int Si = 0;
        int Sj = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(table[i][j] == 'S'){
                    Si = i;
                    Sj = j;
                }
            }
        }

        System.out.println();

        System.out.println(FindWay(table, Si, Sj));
    }
}