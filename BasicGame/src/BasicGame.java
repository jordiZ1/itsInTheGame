import nl.saxion.app.SaxionApp;

import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class BasicGame implements GameLoop {

    private int dummy1Position = 1000;
    private int dummy2Position = 160;

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1500, 750, 40);
    }

    String currentScreen = "startScreen";

    God dummy = new God();
    God dummy2 = new God();

    ArrayList<String> players = new ArrayList<>();
    ArrayList<Player> arenaPlayers = new ArrayList<>();
    int turn;
    boolean turnPlayer1;
    boolean turnPlayer2 = false;

    boolean gameActive;

    Connection connection = null;

    @Override
    public void init() {

        connection = new ConnectDB().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM god");
            while (results.next()) {
                System.out.println(results.getString("name"));
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
        switch (currentScreen) {
            case "startScreen" -> startScreenLoop();
            case "battleScreen" -> battleScreenLoop();
            case "menuScreen" -> menuScreenLoop();
            case "inventoryScreen" -> inventoryScreenLoop();
            case "profileScreen" -> profileScreenLoop();
            case "instructionScreen" -> instructionScreenLoop();
        }

    }

    public void startScreenLoop() {
        drawStarterScreen();
    }
    public void menuScreenLoop() {
        drawMenu();
    }
    public void battleScreenLoop() {
        drawGameBoard();
        characters();

        String currentPlayer = getCurrentPlayer();

        // Draws Dummy HP and names
        /* SaxionApp.drawText(dummy.name, 300, 100, 50);
        SaxionApp.drawText(String.valueOf(dummy.hp), 300, 200, 50);
        SaxionApp.drawText(dummy2.name, 1000, 100, 50);
        SaxionApp.drawText(String.valueOf(dummy2.hp), 1000, 200, 50); */

        SaxionApp.drawText("Turn: " + turn, 700, 80, 20);
        SaxionApp.drawText(currentPlayer, 700, 100, 20);

        if (dummy.hp == 0 || dummy2.hp == 0) {
            gameActive = false;
            SaxionApp.drawBorderedText("GAME OVER", 600, 375, 50);
            if (dummy.hp == 0) {
                SaxionApp.drawBorderedText(dummy2.name + " Wins!", 650, 425, 30);
            } else if (dummy2.hp == 0) {
                SaxionApp.drawBorderedText(dummy.name + " Wins!", 650, 425, 30);
            }
        }
    }

    public void inventoryScreenLoop(){
        SaxionApp.clear();
        SaxionApp.drawText("inv", 300, 300, 100);

    }

    public void profileScreenLoop(){
        SaxionApp.clear();
        SaxionApp.drawText("profile", 300, 300, 100);
    }

    public void instructionScreenLoop(){
        SaxionApp.clear();
        SaxionApp.drawText("instr", 300, 300, 100);
    }


    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        switch (currentScreen) {
            case "menuScreen" -> menuScreenKeyboardEvent(keyboardEvent);
            case "battleScreen" -> battleScreenKeyboardEvent(keyboardEvent);
            case "startScreen" -> startScreenKeyboardEvent(keyboardEvent);
            case "inventoryScreen" -> inventoryScreenKeyboardEvent(keyboardEvent);
            case "profileScreen" -> profileScreenKeyboardEvent(keyboardEvent);
            case "instructionScreen" -> instructionScreenKeyboardEvent(keyboardEvent);
        }

    }

    public void startScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_SPACE) {
            currentScreen = "menuScreen";
        }
    }


    public void menuScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_1) {
            setupBattleArena();
            currentScreen = "battleScreen";
        }
        else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_2) {
            currentScreen = "inventoryScreen";
        }
        else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_3) {
            currentScreen = "profileScreen";
        }
        else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_4) {
            currentScreen = "instructionScreen";
        }

    }

    public void battleScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
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
        } else if (turnPlayer2 && gameActive) {
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

    public void inventoryScreenKeyboardEvent(KeyboardEvent keyboardEvent){
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        }
    }

    public void profileScreenKeyboardEvent(KeyboardEvent keyboardEvent){
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        }

    }

    public void instructionScreenKeyboardEvent(KeyboardEvent keyboardEvent){
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        }

    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }

    private void setupBattleArena() {
        Player player1 = getPlayerFromDB(1); //temporary hardcoded id
        Player player2 = getPlayerFromDB(2); //temporary hardcoded id
        arenaPlayers.add(player1);
        arenaPlayers.add(player2);
    }

    private Player getPlayerFromDB(int id) {
        Player player = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT player_id, name FROM player WHERE player_id = ?");
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                player = new Player(results.getInt("player_id"), results.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return player;
    }

    public String getCurrentPlayer() {
        if (turn % 2 == 1) {
            return players.get(0);
        }

        return players.get(1);
    }

    private void drawStarterScreen() {

        SaxionApp.clear();
        SaxionApp.drawImage("BasicGame/starterscreen.jpg", 0, 0, 1500, 750); //prints background
        SaxionApp.drawImage("BasicGame/botg.png", 250, 50); // Prints game logo
        SaxionApp.drawImage("BasicGame/starttext1.png", 400, 600);//Prints press SPACE to start image
    }


    private void drawMenu() {
        SaxionApp.clear();
        SaxionApp.drawImage("BasicGame/menubackground.jpg", 0,0, 1500, 750);
        SaxionApp.drawImage("BasicGame/botg.png", 250, 50);
        SaxionApp.drawImage("BasicGame/menuoptions.png", 50,250);
        /* SaxionApp.drawBorderedText("1. Battle", 600, 200, 50);
        SaxionApp.drawBorderedText("2. Inventory", 600, 300, 50);
        SaxionApp.drawBorderedText("3. Choose profile", 600, 400, 50);
        SaxionApp.drawBorderedText("4. Instructions", 600, 500, 50); */
    }

    private void drawGameBoard() {
        SaxionApp.clear();
        SaxionApp.drawImage("BasicGame/BattleArena1.jpg", 0, 0, 1500, 750);

        SaxionApp.drawImage("BasicGame/redAbility.png",130,580,100,200);
        SaxionApp.drawImage("BasicGame/greenAbility.png",250,640,135,80);
        SaxionApp.drawImage("BasicGame/blueAbility.png",395,640,110,80);

        SaxionApp.drawImage("BasicGame/redAbility.png",1260,580,100,200);
        SaxionApp.drawImage("BasicGame/greenAbility.png",1115,640,135,80);
        SaxionApp.drawImage("BasicGame/blueAbility.png",995,640,110,80);

        SaxionApp.drawImage("BasicGame/healthBar.png", 200, 35, 345, 35);

        SaxionApp.turnBorderOff();
        SaxionApp.setFill(Color.green);
        SaxionApp.drawRectangle(227, 49, getHealthBarWidth(dummy.hp), 12);

        SaxionApp.drawImage("BasicGame/healthBar.png", 920, 35, 345, 35);
        SaxionApp.turnBorderOff();
        SaxionApp.setFill(Color.green);
        SaxionApp.drawRectangle(947, 49, getHealthBarWidth(dummy2.hp), 12);//*/

        SaxionApp.drawImage("BasicGame/HeBoCard.png",50,130,80,120);
        SaxionApp.drawImage("BasicGame/AtlasCard.png",50,320,80,120);
        SaxionApp.drawImage("BasicGame/blueCard.png",50,510,80,120);


        SaxionApp.drawImage("BasicGame/redCard.png", 1370, 130, 80, 120);
        SaxionApp.drawImage("BasicGame/greenCard.png", 1370, 320, 80, 120);
        SaxionApp.drawImage("BasicGame/blueCard.png", 1370, 510, 80, 120);
    }

    private void characters () {
        SaxionApp.drawImage(dummy.image,dummy1Position,360,320,270);
        SaxionApp.drawImage(dummy2.image,dummy2Position,365,470,270);
    }

    public int getHealthBarWidth(int hp) {
        double width = ((double) hp / 100) * 300;
        return (int) width;

    }
}
