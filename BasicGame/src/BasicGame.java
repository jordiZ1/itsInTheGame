import nl.saxion.app.SaxionApp;

import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.util.ArrayList;

public class BasicGame implements GameLoop {

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1000, 1000, 40);
    }

    ArrayList<String> players = new ArrayList<>();
    int turn;

    @Override
    public void init() {
        turn = 1;
        players.add("Player 1");
        players.add("Player 2");
    }

    @Override
    public void loop() {
        SaxionApp.clear();
        String currentPlayer = getCurrentPlayer();

        SaxionApp.drawText("Turn: " + turn, 80, 80, 20);
        SaxionApp.drawText(currentPlayer, 100, 100, 20);

        SaxionApp.sleep(2);

        turn++;
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {

    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }

    private String getCurrentPlayer() {
        if (turn % 2 == 1) {
            return players.get(0);
        }

        return players.get(1);
    }
}
