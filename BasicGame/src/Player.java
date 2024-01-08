import java.util.ArrayList;

public class Player {
    int id;
    String name;
    ArrayList<God> cards = new ArrayList<>();
    int activeCard = 0;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
