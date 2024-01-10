import java.util.ArrayList;

public class Player {
    int id;
    String name;
    ArrayList<God> gods = new ArrayList<>();

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
