import java.util.ArrayList;
import java.util.List;

public class Node {
    int i;
    int j;
    List<Node> connected;
    Node parent;

    public Node(int i, int j) {
        this.i = i;
        this.j = j;
        connected = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        Node o = (Node)obj;
        return i == o.i && j == o.j;
    }

    @Override
    public String toString() {
        return "(" + j + "," + i + "),";
    }
}
