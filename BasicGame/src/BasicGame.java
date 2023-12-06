import nl.saxion.app.SaxionApp;

import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.util.ArrayList;
import java.util.Collections;

public class BasicGame implements GameLoop {

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1500, 750, 40);
    }

    ArrayList<Player> players = new ArrayList<>();
    int turn;

    @Override
    public void init() {
        God dummy = new God();
        God dummy2 = new God();

        dummy.characterId = 1;
        dummy.hp = 100;
        dummy.name = "Zeus";
        dummy.abilityDamage1 = 20;

        dummy2.characterId = 2;
        dummy2.hp = 100;
        dummy2.name = "Odin";
        dummy2.abilityDamage1 = 25;

        Player player1 = new Player();
        Player player2 = new Player();

        player1.id = 1;
        player1.name = "Speler 1";
        player1.cards.add(dummy);
        player1.activeCard = 0;

        player2.id = 2;
        player2.name = "Speler 2";
        player2.cards.add(dummy2);
        player2.activeCard = 0;

        players.add(player1);
        players.add(player2);

        turn = 1;
    }

    @Override
    public void loop() {
        drawGameBoard();
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        Player currentPlayer = getCurrentPlayer();
        SaxionApp.drawText(player1.cards.get(player1.activeCard).name, 100, 100, 50);
        SaxionApp.drawText(String.valueOf(player1.cards.get(player1.activeCard).hp),100,200,50);
        SaxionApp.drawText(player2.cards.get(player2.activeCard).name, 1300, 100,50);
        SaxionApp.drawText(String.valueOf(player2.cards.get(player2.activeCard).hp),1300,200,50);

        SaxionApp.drawText("Turn: " + turn, 80, 80, 20);
        SaxionApp.drawText(currentPlayer.name, 100, 100, 20);

        SaxionApp.sleep(2);

        turn++;
        reversePlayersList();
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {

    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }

    private Player getCurrentPlayer() {
        return players.get(0);
//        if (turn % 2 == 1) {
//            return player1;
//        }
//
//        return player2;
    }

    private void reversePlayersList() {
        Collections.reverse(players);
    }

    private void doAttack() {

    }

    private void drawGameBoard() {
        SaxionApp.clear();
        SaxionApp.drawImage("BasicGame/BattleArena1.jpg", 0,0, 1500, 750);

        SaxionApp.drawImage("BasicGame/redAbility.png",130,580,110,200);
        SaxionApp.drawImage("BasicGame/greenAbility.png",250,640,135,80);
        SaxionApp.drawImage("BasicGame/blueAbility.png",395,640,110,80);

        SaxionApp.drawImage("BasicGame/redAbility.png",1260,580,110,200);
        SaxionApp.drawImage("BasicGame/greenAbility.png",1115,640,135,80);
        SaxionApp.drawImage("BasicGame/blueAbility.png",995,640,110,80);

        SaxionApp.drawImage("BasicGame/healthBar.png",200,-50,360,190);
        SaxionApp.drawImage("BasicGame/healthBar.png",920,-50,360,190);

        SaxionApp.drawImage("BasicGame/redCard.png",50,130,80,120);
        SaxionApp.drawImage("BasicGame/greenCard.png/",50,320,80,120);
        SaxionApp.drawImage("BasicGame/blueCard.png",50,510,80,120);

        SaxionApp.drawImage("BasicGame/redCard.png",1370,130,80,120);
        SaxionApp.drawImage("BasicGame/greenCard.png",1370,320,80,120);
        SaxionApp.drawImage("BasicGame/blueCard.png",1370,510,80,120);
    }
}
