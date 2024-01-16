import nl.saxion.app.SaxionApp;

import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class BasicGame implements GameLoop {

    private int selectorX = 1350;
    private int selectorY = 180;

    int currentPositionX = 1350;
    int currentPositionY = 180;

    final int maximumValueRight = 1350;
    final int maximumValueLeft = 870;

    int maximumValueUpY = 180;
    int maximumValueDownY = 720;

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1500, 750, 40);
    }

    Connection connection = null;
    String currentScreen = "startScreen";

    ArrayList<Player> arenaPlayers = new ArrayList<>();
    int turn;
    boolean turnPlayer1;
    private int god1Position = 160;
    private int god2Position = 1000;
    private int activeGodPlayer1 = 0;
    private int activeGodPlayer2 = 0;
    boolean gameActive;


    @Override
    public void init() {
        connection = new ConnectDB().getConnection();
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
            case "playSelectionScreen" -> playSelectionScreenLoop();
            case "newPlaySelectionScreen" -> newPlaySelectionScreenLoop();
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

        SaxionApp.drawText("Turn: " + turn, 700, 80, 20);
        SaxionApp.drawText(currentPlayer, 700, 100, 20);

        /*if (dummy.hp == 0 || dummy2.hp == 0) {
            gameActive = false;
            SaxionApp.drawBorderedText("GAME OVER", 600, 375, 50);
            if (dummy.hp == 0) {
                SaxionApp.drawBorderedText(dummy2.name + " Wins!", 650, 425, 30);
            } else if (dummy2.hp == 0) {
                SaxionApp.drawBorderedText(dummy.name + " Wins!", 650, 425, 30);
            }
        }*/
    }

    public void inventoryScreenLoop() {
        SaxionApp.clear();
        SaxionApp.drawText("inv", 300, 300, 100);
    }

    public void profileScreenLoop() {
        SaxionApp.clear();
        SaxionApp.drawText("profile", 300, 300, 100);
    }

    public void instructionScreenLoop() {
        SaxionApp.clear();
        SaxionApp.drawText("instr", 300, 300, 100);
    }

    public void playSelectionScreenLoop() {
        SaxionApp.clear();
        drawPlaySelectionScreen();
        selector();
    }

    public void newPlaySelectionScreenLoop() {
        SaxionApp.clear();
        drawNewSelectionScreen();
        selector();
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
            case "playSelectionScreen" ->  playSelectionScreenKeyboardEvent(keyboardEvent);
            case "newPlaySelectionScreen" -> newPlaySelectionScreenKeyboardEvent(keyboardEvent);
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
            currentScreen = "playSelectionScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_2) {
            currentScreen = "inventoryScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_3) {
            currentScreen = "profileScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_4) {
            currentScreen = "instructionScreen";
        }
    }

    public void battleScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (gameActive) {
            if (turnPlayer1) {
                if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_Q) {
                    attackAnimation(1);
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp -= arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(0).damage;
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp += arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(0).healing;
                    turnPlayer1 = false;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_W) {
                    attackAnimation(1);
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp -= arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(1).damage;
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp += arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(1).healing;
                    turnPlayer1 = false;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_E) {
                    attackAnimation(1);
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp -= arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(2).damage;
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp += arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(2).healing;
                    turnPlayer1 = false;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_1 && activeGodPlayer1 != 0) {
                    activeGodPlayer1 = 0;
                    turnPlayer1 = false;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_2 && activeGodPlayer1 != 1) {
                    activeGodPlayer1 = 1;
                    turnPlayer1 = false;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_3 && activeGodPlayer1 != 2) {
                    activeGodPlayer1 = 2;
                    turnPlayer1 = false;
                    turn++;
                }
            } else {
                if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_Z) {
                    attackAnimation(2);
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp -= arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(0).damage;
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp += arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(0).healing;
                    turnPlayer1 = true;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_X) {
                    attackAnimation(2);
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp -= arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(1).damage;
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp += arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(1).healing;
                    turnPlayer1 = true;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_C) {
                    attackAnimation(2);
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp -= arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(2).damage;
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp += arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(2).healing;
                    turnPlayer1 = true;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_8 && activeGodPlayer2 != 0) {
                    activeGodPlayer2 = 0;
                    turnPlayer1 = true;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_9 && activeGodPlayer2 != 1) {
                    activeGodPlayer2 = 1;
                    turnPlayer1 = true;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_0 && activeGodPlayer2 != 2) {
                    activeGodPlayer2 = 2;
                    turnPlayer1 = true;
                    turn++;
                }
            }
        }
    }

    public void inventoryScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        }
    }

    public void profileScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        }
    }

    public void instructionScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        }
    }

    public void playSelectionScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ENTER ) {
            currentScreen = "newPlaySelectionScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_LEFT && keyboardEvent.isKeyPressed()) {
            selectorX = Math.max(maximumValueLeft, currentPositionX - 160);
            currentPositionX = selectorX;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_RIGHT && keyboardEvent.isKeyPressed()) {
            selectorX = Math.min(maximumValueRight, currentPositionX + 160);
            currentPositionX =  selectorX;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_DOWN && keyboardEvent.isKeyPressed()) {
            selectorY = Math.min(maximumValueDownY, currentPositionY + 180);
            currentPositionY = selectorY;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_UP && keyboardEvent.isKeyPressed()) {
            selectorY = Math.max(maximumValueUpY, currentPositionY - 180);
            currentPositionY = selectorY;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_SPACE) {

        }
    }

    public void newPlaySelectionScreenKeyboardEvent (KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ENTER && keyboardEvent.isKeyPressed()) {
            currentScreen = "battleScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_LEFT && keyboardEvent.isKeyPressed()) {
            selectorX = Math.max(maximumValueLeft, currentPositionX - 160);
            currentPositionX = selectorX;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_RIGHT && keyboardEvent.isKeyPressed()) {
            selectorX = Math.min(maximumValueRight, currentPositionX + 160);
            currentPositionX =  selectorX;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_DOWN && keyboardEvent.isKeyPressed()) {
            selectorY = Math.min(maximumValueDownY, currentPositionY + 180);
            currentPositionY = selectorY;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_UP && keyboardEvent.isKeyPressed()) {
            selectorY = Math.max(maximumValueUpY, currentPositionY - 180);
            currentPositionY = selectorY;
        }
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }

    private void setupBattleArena() {
        Player player1;
        Player player2;
        try {
            player1 = getPlayerFromDB(1); //temporary hardcoded id
            int[] player1ids = {10, 2, 3};
            player1.gods = getGodsFromDB(player1ids);
            player2 = getPlayerFromDB(2); //temporary hardcoded id
            int[] player2ids = {4, 5, 9};
            player2.gods = getGodsFromDB(player2ids);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        arenaPlayers.add(player1);
        arenaPlayers.add(player2);

        activeGodPlayer1 = 0;
        activeGodPlayer2 = 0;
        turnPlayer1 = true;
        turn = 1;
        gameActive = true;
    }

    private Player getPlayerFromDB(int id) throws SQLException {
        Player player = null;
        PreparedStatement statement = connection.prepareStatement("SELECT player_id, name FROM player WHERE player_id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            player = new Player(results.getInt("player_id"), results.getString("name"));
        }
        results.close();
        statement.close();
        return player;
    }

    private ArrayList<God> getGodsFromDB(int[] ids) throws SQLException {
        ArrayList<God> gods = new ArrayList<>();

        for (int id : ids) {
            God god = null;
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM god WHERE god_id = ?");
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                god = new God();
                god.id = results.getInt("god_id");
                god.name = results.getString("name");
                god.category = results.getString("category");
                god.elementId = results.getInt("element_id");
                god.hp = results.getInt("health");
            }
            results.close();
            statement.close();

            if (god == null) {
                throw new RuntimeException();
            }

            god.attacks = getAttacksByGodIdFromDB(god.id);

            gods.add(god);
        }

        return gods;
    }

    private ArrayList<Attack> getAttacksByGodIdFromDB(int id) throws SQLException {
        ArrayList<Attack> attacks = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT god_attack.god_id, attack.* FROM god_attack JOIN attack ON god_attack.attack_id = attack.attack_id WHERE god_attack.god_id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            Attack attack = new Attack();
            attack.id = results.getInt("attack_id");
            attack.name = results.getString("name");
            attack.elementId = results.getInt("element_id");
            attack.damage = results.getInt("base_damage");
            attack.healing = results.getInt("healing");
            attacks.add(attack);
        }
        results.close();
        statement.close();

        return attacks;
    }

    public String getCurrentPlayer() {
        if (turn % 2 == 1) {
            return arenaPlayers.get(0).name;
        }

        return arenaPlayers.get(1).name;
    }

    private void drawStarterScreen() {
        SaxionApp.clear();
        SaxionApp.drawImage("BasicGame/starterscreen.jpg", 0, 0, 1500, 750); //prints background
        SaxionApp.drawImage("BasicGame/botg.png", 250, 50); // Prints game logo
        SaxionApp.drawImage("BasicGame/starttext1.png", 400, 600);//Prints press SPACE to start image
    }

    private void drawMenu() {
        SaxionApp.clear();
        SaxionApp.drawImage("BasicGame/menubackground.jpg", 0, 0, 1500, 750);
        SaxionApp.drawImage("BasicGame/botg.png", 250, 50);
        SaxionApp.drawImage("BasicGame/menuoptions.png", 50, 250);
    }

    private void drawPlaySelectionScreen () {
        SaxionApp.drawImage("BasicGame/images/gods/AchillesFaceLeft.png", 1300,43, 170,140);
        SaxionApp.drawImage("BasicGame/images/gods/AresFaceLeft.png", 1100,50, 210,130);
        SaxionApp.drawImage("BasicGame/images/gods/AtlasFaceLeft.png", 954,50, 230,130);
        SaxionApp.drawImage("BasicGame/images/gods/ApolloFaceLeft.png", 762,50, 230,130);

        SaxionApp.drawImage("BasicGame/images/gods/AnhurFaceLeft.png", 1280,230, 185,140);
        SaxionApp.drawImage("BasicGame/images/gods/AnubisFaceLeft.png", 1100,230, 210,130);
        SaxionApp.drawImage("BasicGame/images/gods/SobekFaceLeft.png", 950,230, 180,130);
        SaxionApp.drawImage("BasicGame/images/gods/HorusFaceLeft.png", 800,230, 180,130);

        SaxionApp.drawImage("BasicGame/images/gods/OdinFaceLeft.png", 1285,410, 180,130);
        SaxionApp.drawImage("BasicGame/images/gods/ThorFaceLeft.png", 1100,410, 230,140);
        SaxionApp.drawImage("BasicGame/images/gods/TyrFaceLeft.png", 930,410, 215,130);
        SaxionApp.drawImage("BasicGame/images/gods/UllrFaceLeft.png", 777,400, 225,145);

        SaxionApp.drawImage("BasicGame/images/gods/AoKuangFaceLeft.png", 1300,585, 160,130);
        SaxionApp.drawImage("BasicGame/images/gods/ErlangShenFaceLeft.png", 1120,587, 180,130);
        SaxionApp.drawImage("BasicGame/images/gods/GuanYuFaceLeft.png", 954,585, 190,136);
        SaxionApp.drawImage("BasicGame/images/gods/HeBoFaceLeft.png", 780,588, 180,130);

        SaxionApp.drawImage("BasicGame/selectPlayer1.png", 65,50,280,100);
    }

    private void drawNewSelectionScreen () {
        SaxionApp.drawImage("BasicGame/images/gods/AchillesFaceLeft.png", 1300,43, 170,140);
        SaxionApp.drawImage("BasicGame/images/gods/AresFaceLeft.png", 1100,50, 210,130);
        SaxionApp.drawImage("BasicGame/images/gods/AtlasFaceLeft.png", 954,50, 230,130);
        SaxionApp.drawImage("BasicGame/images/gods/ApolloFaceLeft.png", 762,50, 230,130);

        SaxionApp.drawImage("BasicGame/images/gods/AnhurFaceLeft.png", 1280,230, 185,140);
        SaxionApp.drawImage("BasicGame/images/gods/AnubisFaceLeft.png", 1100,230, 210,130);
        SaxionApp.drawImage("BasicGame/images/gods/SobekFaceLeft.png", 950,230, 180,130);
        SaxionApp.drawImage("BasicGame/images/gods/HorusFaceLeft.png", 800,230, 180,130);

        SaxionApp.drawImage("BasicGame/images/gods/OdinFaceLeft.png", 1285,410, 180,130);
        SaxionApp.drawImage("BasicGame/images/gods/ThorFaceLeft.png", 1100,410, 230,140);
        SaxionApp.drawImage("BasicGame/images/gods/TyrFaceLeft.png", 930,410, 215,130);
        SaxionApp.drawImage("BasicGame/images/gods/UllrFaceLeft.png", 777,400, 225,145);

        SaxionApp.drawImage("BasicGame/images/gods/AoKuangFaceLeft.png", 1300,585, 160,130);
        SaxionApp.drawImage("BasicGame/images/gods/ErlangShenFaceLeft.png", 1120,587, 180,130);
        SaxionApp.drawImage("BasicGame/images/gods/GuanYuFaceLeft.png", 954,585, 190,136);
        SaxionApp.drawImage("BasicGame/images/gods/HeBoFaceLeft.png", 780,588, 180,130);

        SaxionApp.drawImage("BasicGame/selectPlayer2.png", 65,50,280,100);
    }

    private void drawGameBoard() {
        SaxionApp.clear();
        SaxionApp.drawImage("BasicGame/BattleArena1.jpg", 0, 0, 1500, 750);

        SaxionApp.drawImage("BasicGame/redAbility.png", 130, 580, 100, 200);
        SaxionApp.drawImage("BasicGame/greenAbility.png", 250, 640, 135, 80);
        SaxionApp.drawImage("BasicGame/blueAbility.png", 395, 640, 110, 80);

        SaxionApp.drawImage("BasicGame/redAbility.png", 1260, 580, 100, 200);
        SaxionApp.drawImage("BasicGame/greenAbility.png", 1115, 640, 135, 80);
        SaxionApp.drawImage("BasicGame/blueAbility.png", 995, 640, 110, 80);

        SaxionApp.turnBorderOff();
        SaxionApp.setFill(Color.green);

        SaxionApp.drawImage("BasicGame/healthBar.png", 200, 35, 345, 35);
        SaxionApp.drawRectangle(227, 49, getHealthBarWidth(arenaPlayers.get(0).gods.get(activeGodPlayer1).hp), 12);

        SaxionApp.drawImage("BasicGame/healthBar.png", 920, 35, 345, 35);
        SaxionApp.drawRectangle(947, 49, getHealthBarWidth(arenaPlayers.get(1).gods.get(activeGodPlayer2).hp), 12);

        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(0).gods.get(0).name + "Card.png", 50, 130, 80, 120);
        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(0).gods.get(1).name + "Card.png", 50, 320, 80, 120);
        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(0).gods.get(2).name + "Card.png", 50, 510, 80, 120);

        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(1).gods.get(0).name + "Card.png", 1370, 130, 80, 120);
        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(1).gods.get(1).name + "Card.png", 1370, 320, 80, 120);
        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(1).gods.get(2).name + "Card.png", 1370, 510, 80, 120);
    }

    private void characters() {
        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(0).gods.get(activeGodPlayer1).name + "FaceRight.png", god1Position, 360, 320, 270);
        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(1).gods.get(activeGodPlayer2).name + "FaceLeft.png", god2Position, 365, 470, 270);
    }

    private void attackAnimation(int godNumber) {
        if (godNumber == 1) {
            god1Position = 840;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            god1Position = 160;
                        }
                    },
                    1000
            );
        } else {
            god2Position = 320;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            god2Position = 1000;
                        }
                    },
                    1000
            );
        }
    }

    public int getHealthBarWidth(int hp) {
        double width = ((double) hp / 100) * 300;
        return (int) width;
    }

    public void selector () {
        SaxionApp.setBorderColor(Color.blue);
        SaxionApp.setFill(Color.green);
        SaxionApp.drawRectangle(selectorX,selectorY,60,10);
    }
}
