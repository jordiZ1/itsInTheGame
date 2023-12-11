import nl.saxion.app.SaxionApp;

import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.awt.*;
import java.util.ArrayList;

public class BasicGame implements GameLoop {

    //private int remainingHealth = 360;
    public static void main(String[] args) {SaxionApp.startGameLoop(new BasicGame(), 1500, 750, 40);
    }

    God dummy = new God();
    God dummy2 = new God();

    ArrayList<String> players = new ArrayList<>();
    int turn;

    @Override
    public void init() {

        turn = 1;
        players.add("Player 1");
        players.add("Player 2");

        dummy.characterId = 1;
        dummy.hp = 100;
        dummy.name = "Zeus";
        dummy.abilityDamage1 = 20;

        dummy2.characterId = 2;
        dummy2.hp = 100;
        dummy2.name = "Odin";
        dummy2.abilityDamage1 = 25;

    }

    @Override
    public void loop() {
        drawGameBoard();
        characters();

        String currentPlayer = getCurrentPlayer();
        SaxionApp.drawText(dummy.name, 100, 100, 50);
        SaxionApp.drawText(String.valueOf(dummy.hp),100,200,50);
        SaxionApp.drawText(dummy2.name, 1300, 100,50);
        SaxionApp.drawText(String.valueOf(dummy2.hp),1300,200,50);

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

    private void drawGameBoard() {


        SaxionApp.clear();
        SaxionApp.drawImage("BasicGame/BattleArena1.jpg", 0,0, 1500, 750);

        SaxionApp.drawImage("BasicGame/redAbility.png",130,580,110,200);
        SaxionApp.drawImage("BasicGame/greenAbility.png",250,640,135,80);
        SaxionApp.drawImage("BasicGame/blueAbility.png",395,640,110,80);

        SaxionApp.drawImage("BasicGame/redAbility.png",1260,580,110,200);
        SaxionApp.drawImage("BasicGame/greenAbility.png",1115,640,135,80);
        SaxionApp.drawImage("BasicGame/blueAbility.png",995,640,110,80);


        /*SaxionApp.drawImage("BasicGame/healthBar.png",200,35,remainingHealth,35);
        SaxionApp.turnBorderOff();
        SaxionApp.setFill(Color.green);
        SaxionApp.drawRectangle(227,49,313,12);

        //SaxionApp.drawImage("BasicGame/healthBar.png",920,35,remainingHealth,35);
        SaxionApp.turnBorderOff();
        SaxionApp.setFill(Color.green);
        SaxionApp.drawRectangle(947,49,313,12);//*/


        SaxionApp.drawImage("BasicGame/redCard.png",50,130,80,120);
        SaxionApp.drawImage("BasicGame/greenCard.png/",50,320,80,120);
        SaxionApp.drawImage("BasicGame/blueCard.png",50,510,80,120);

        SaxionApp.drawImage("BasicGame/redCard.png",1370,130,80,120);
        SaxionApp.drawImage("BasicGame/greenCard.png",1370,320,80,120);
        SaxionApp.drawImage("BasicGame/blueCard.png",1370,510,80,120);



    }

    private void characters () {
        SaxionApp.drawImage("BasicGame/dummy1.png",1100,390,150,230);
        SaxionApp.drawImage("BasicGame/dummy2.png",280,385,200,240);
    }


}
