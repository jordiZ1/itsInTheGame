import nl.saxion.app.SaxionApp;

import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.awt.*;
import java.util.ArrayList;

public class BasicGame implements GameLoop {

    private int dummy1Position = 1000;
    private int dummy2Position = 160;

    public static void main(String[] args) {SaxionApp.startGameLoop(new BasicGame(), 1500, 750, 40);
    }

    God dummy = new God();
    God dummy2 = new God();

    ArrayList<String> players = new ArrayList<>();
    int turn;
    boolean turnPlayer1;
    boolean turnPlayer2 = false;

    boolean gameActive;

    @Override
    public void init() {
        turn = 1;
        turnPlayer1 = true;
        turnPlayer2 = false;

        gameActive = true;


        players.add("Player 1");
        players.add("Player 2");

        dummy.characterId = 1;
        dummy.hp = 100;
        dummy.name = "Achilles";
        dummy.abilityDamage1 = 15;
        dummy.abilityDamage2 = 20;
        dummy.abilityDamage3 = 25;
        dummy.image = "BasicGame/AoKuangFaceLeft.png";

        dummy2.characterId = 2;
        dummy2.hp = 100;
        dummy2.name = "Ares";
        dummy2.abilityDamage1 = 25;
        dummy2.abilityDamage2 = 15;
        dummy2.abilityDamage3 = 10;
        dummy2.image = "BasicGame/AchillesFaceRight.png";

    }

    @Override
    public void loop() {
        drawGameBoard();
        characters();

        String currentPlayer = getCurrentPlayer();

        // Draws Dummy HP and names
        SaxionApp.drawText(dummy.name, 300, 100, 50);
        SaxionApp.drawText(String.valueOf(dummy.hp),300,200,50);
        SaxionApp.drawText(dummy2.name, 1000, 100,50);
        SaxionApp.drawText(String.valueOf(dummy2.hp),1000,200,50);

        SaxionApp.drawText("Turn: " + turn, 700, 80, 20);
        SaxionApp.drawText(currentPlayer, 700, 100, 20);

        if (dummy.hp == 0 || dummy2.hp == 0){
            gameActive = false;
            SaxionApp.drawBorderedText("GAME OVER", 600, 375, 50);
            if (dummy.hp == 0){
                SaxionApp.drawBorderedText(dummy2.name + " Wins!", 650, 425, 30);
            }
            else if (dummy2.hp == 0){
                SaxionApp.drawBorderedText(dummy.name + " Wins!", 650, 425, 30);
            }
        }
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        if (turnPlayer1 && gameActive) {
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_Q) {
                dummy2Position = dummy1Position - 160;
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                dummy2Position = 160;
                            }
                        }
                        , 1000);

                dummy2.hp = dummy2.hp - dummy.abilityDamage1;
                turnPlayer2 = true;
                turnPlayer1 = false;
                turn++;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_W) {
                dummy2Position = dummy1Position - 160;
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                dummy2Position = 160;
                            }
                        }
                        , 1000);
                dummy2.hp = dummy2.hp - dummy.abilityDamage2;
                turnPlayer2 = true;
                turnPlayer1 = false;
                turn++;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_E) {
                dummy2Position = dummy1Position - 160;
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                dummy2Position = 160;
                            }
                        }
                        , 1000);
                dummy2.hp = dummy2.hp - dummy.abilityDamage3;
                turnPlayer2 = true;
                turnPlayer1 = false;
                turn++;
            }
        }


        else if (turnPlayer2 && gameActive){
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_Z) {
                dummy1Position = dummy2Position + 150;
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                dummy1Position = 1000;
                            }
                        }
                        , 1000);

                dummy.hp = dummy.hp - dummy2.abilityDamage1;
                turnPlayer1 = true;
                turnPlayer2 = false;
                turn++;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_X) {
                dummy1Position = dummy2Position + 150;
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                dummy1Position = 1000;
                            }
                        }
                        , 1000);
                dummy.hp = dummy.hp - dummy2.abilityDamage1;
                turnPlayer1 = true;
                turnPlayer2 = false;
                turn++;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_C) {
                dummy1Position = dummy2Position + 150;
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                dummy1Position = 1000;
                            }
                        }
                        , 1000);
                dummy.hp = dummy.hp - dummy2.abilityDamage1;
                turnPlayer1 = true;
                turnPlayer2 = false;
                turn++;
            }
        }
    }



    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }

    public String getCurrentPlayer() {
        if (turn % 2 == 1) {
            return players.get(0);
        }

        return players.get(1);
    }

    private void drawGameBoard() {
        SaxionApp.clear();
        SaxionApp.drawImage("BasicGame/BattleArena1.jpg", 0,0, 1500, 750);

        SaxionApp.drawImage("BasicGame/redAbility.png",130,580,100,200);
        SaxionApp.drawImage("BasicGame/greenAbility.png",250,640,135,80);
        SaxionApp.drawImage("BasicGame/blueAbility.png",395,640,110,80);

        SaxionApp.drawImage("BasicGame/redAbility.png",1260,580,100,200);
        SaxionApp.drawImage("BasicGame/greenAbility.png",1115,640,135,80);
        SaxionApp.drawImage("BasicGame/blueAbility.png",995,640,110,80);

        SaxionApp.drawImage("BasicGame/healthBar.png",200,35,345,35);

        SaxionApp.turnBorderOff();
        SaxionApp.setFill(Color.green);
        SaxionApp.drawRectangle(227,49,getHealthBarWidth(dummy.hp),12);

        SaxionApp.drawImage("BasicGame/healthBar.png",920,35,345,35);
        SaxionApp.turnBorderOff();
        SaxionApp.setFill(Color.green);
        SaxionApp.drawRectangle(947,49,getHealthBarWidth(dummy2.hp),12);//*/

        SaxionApp.drawImage("BasicGame/HeBoCard.png",50,130,80,120);
        SaxionApp.drawImage("BasicGame/AtlasCard.png",50,320,80,120);
        SaxionApp.drawImage("BasicGame/blueCard.png",50,510,80,120);

        SaxionApp.drawImage("BasicGame/redCard.png",1370,130,80,120);
        SaxionApp.drawImage("BasicGame/greenCard.png",1370,320,80,120);
        SaxionApp.drawImage("BasicGame/blueCard.png",1370,510,80,120);
    }

    private void characters () {
        SaxionApp.drawImage(dummy.image,dummy1Position,360,320,270);
        SaxionApp.drawImage(dummy2.image,dummy2Position,365,470,270);
    }

    public int getHealthBarWidth(int hp){
        double width = ( (double) hp /100) * 300;
        return (int) width;

    }
}
