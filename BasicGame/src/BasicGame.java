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

    final int maximumValueUpY = 180;
    final int maximumValueDownY = 720;

    ArrayList<Integer> godsSelectedPlayer1 = new ArrayList<>();
    ArrayList<God> godsPlayer1 = new ArrayList<>();
    ArrayList<Integer> godsSelectedPlayer2 = new ArrayList<>();
    ArrayList<God> godsPlayer2 = new ArrayList<>();

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

    int winnerGame;


    @Override
    public void init() {
        connection = new ConnectDB().getConnection();
    }
    @Override
    public void loop() {
        switch (currentScreen) {
            case "startScreen" -> startScreenLoop();
            case "menuScreen" -> menuScreenLoop();
            case "inventoryScreen" -> inventoryScreenLoop();
            case "inventoryScreen2" -> inventoryScreen2Loop();
            case "profileScreen" -> profileScreenLoop();
            case "instructionScreen" -> instructionScreenLoop();
            case "secondInstructionScreen" -> secondInstructionScreenLoop();
            case "playSelectionScreen" -> playSelectionScreenLoop();
            case "newPlaySelectionScreen" -> newPlaySelectionScreenLoop();
            case "battleScreen" -> battleScreenLoop();
            case "inGameInfoScreen" -> inGameInfoScreenLoop();
            case "endScreen" -> endScreenLoop();
        }
    }
    public void startScreenLoop() {
        drawStarterScreen();
    }
    public void menuScreenLoop() {
        drawMenu();
    }
    public void inventoryScreenLoop() {
        SaxionApp.clear();
        drawInventoryScreen1();
    }
    public void inventoryScreen2Loop() {
        SaxionApp.clear();
        drawInventoryScreen2();
    }
    public void profileScreenLoop() {
        SaxionApp.clear();
        SaxionApp.drawImage("BasicGame/background endscreen.jpg", 0, 0, 1500, 750);
        SaxionApp.setFill(Color.yellow);
        SaxionApp.drawBorderedText("Coming Soon...", 450, 350, 100);
    }
    public void instructionScreenLoop() {
        SaxionApp.clear();
        drawInstructionScreen();
    }
    public void secondInstructionScreenLoop() {
        SaxionApp.clear();
        drawSecondInstructionScreen();

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
    public void battleScreenLoop() {
        drawGameBoard();
        characters();

        String currentPlayer = getCurrentPlayer();

        SaxionApp.drawText("Turn: " + turn, 700, 80, 20);
        SaxionApp.drawText(currentPlayer, 700, 100, 20);

        //Prevents HP from exceeding 100
        if (arenaPlayers.get(0).gods.get(activeGodPlayer1).hp > 100){
            arenaPlayers.get(0).gods.get(activeGodPlayer1).hp = 100;
        } else if (arenaPlayers.get(1).gods.get(activeGodPlayer1).hp > 100){
            arenaPlayers.get(1).gods.get(activeGodPlayer1).hp = 100;
        }

        //Ends game whenever HP is 0 and displays Game Over message
        if (arenaPlayers.get(0).gods.get(0).hp <= 0 && arenaPlayers.get(0).gods.get(1).hp <= 0 && arenaPlayers.get(0).gods.get(2).hp <= 0
                || arenaPlayers.get(1).gods.get(0).hp <= 0 && arenaPlayers.get(1).gods.get(1).hp <= 0 && arenaPlayers.get(1).gods.get(2).hp <= 0) {
            gameActive = false;
            SaxionApp.drawBorderedText("GAME OVER", 600, 250, 50);
            if (arenaPlayers.get(0).gods.get(0).hp <= 0 && arenaPlayers.get(0).gods.get(1).hp <= 0 && arenaPlayers.get(0).gods.get(2).hp <= 0) {
                winnerGame = 1;
            } else if (arenaPlayers.get(1).gods.get(0).hp <= 0 && arenaPlayers.get(1).gods.get(1).hp <= 0 && arenaPlayers.get(1).gods.get(2).hp <= 0) {
                winnerGame = 0;
            }

            SaxionApp.drawBorderedText("Press ENTER to continue...", 480, 350, 50);
        }
    }
    public void inGameInfoScreenLoop(){
        SaxionApp.clear();
        drawInGameInfoScreen();
    }
    public void endScreenLoop() {
        SaxionApp.clear();
        drawEndScreen();
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        switch (currentScreen) {
            case "startScreen" -> startScreenKeyboardEvent(keyboardEvent);
            case "menuScreen" -> menuScreenKeyboardEvent(keyboardEvent);
            case "inventoryScreen" -> inventoryScreenKeyboardEvent(keyboardEvent);
            case "inventoryScreen2" -> inventoryScreen2KeyboardEvent(keyboardEvent);
            case "profileScreen" -> profileScreenKeyboardEvent(keyboardEvent);
            case "instructionScreen" -> instructionScreenKeyboardEvent(keyboardEvent);
            case "secondInstructionScreen" -> secondInstructionScreenKeyboardEvent(keyboardEvent);
            case "playSelectionScreen" -> playSelectionScreenKeyboardEvent(keyboardEvent);
            case "newPlaySelectionScreen" -> newPlaySelectionScreenKeyboardEvent(keyboardEvent);
            case "battleScreen" -> battleScreenKeyboardEvent(keyboardEvent);
            case "inGameInfoScreen" -> inGameInfoScreenKeyboardEvent(keyboardEvent);
            case "endScreen" -> endScreenKeyboardEvent(keyboardEvent);
        }
    }


    public void startScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_SPACE) {
            currentScreen = "menuScreen";
        }
    }
    public void menuScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_1) {
            currentScreen = "playSelectionScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_2) {
            currentScreen = "inventoryScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_3) {
            currentScreen = "profileScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_4) {
            currentScreen = "instructionScreen";
        }
    }
    public void inventoryScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_RIGHT) {
            currentScreen = "inventoryScreen2";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_P) {
            currentScreen = "battleScreen";
        }
    }
    public void inventoryScreen2KeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_LEFT) {
            currentScreen = "inventoryScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        }
    }public void profileScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        }
    }
    public void instructionScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_RIGHT) {
            currentScreen = "secondInstructionScreen";
        }
    }
    public void secondInstructionScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_LEFT) {
            currentScreen = "instructionScreen";
        }
    }
    public void playSelectionScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ENTER) {
            if (godsSelectedPlayer1.size() == 3) {
                godsPlayer1 = getGodsFromDB(godsSelectedPlayer1);
                currentScreen = "newPlaySelectionScreen";
            }
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_LEFT && keyboardEvent.isKeyPressed()) {
            selectorX = Math.max(maximumValueLeft, currentPositionX - 160);
            currentPositionX = selectorX;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_RIGHT && keyboardEvent.isKeyPressed()) {
            selectorX = Math.min(maximumValueRight, currentPositionX + 160);
            currentPositionX = selectorX;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_DOWN && keyboardEvent.isKeyPressed()) {
            selectorY = Math.min(maximumValueDownY, currentPositionY + 180);
            currentPositionY = selectorY;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_UP && keyboardEvent.isKeyPressed()) {
            selectorY = Math.max(maximumValueUpY, currentPositionY - 180);
            currentPositionY = selectorY;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_SPACE && keyboardEvent.isKeyPressed()) {
            int godId = getGodIdFromSelector();

            if (godsSelectedPlayer1.size() < 3 && !godsSelectedPlayer1.contains(godId)) {
                godsSelectedPlayer1.add(godId);
            } else if (godsSelectedPlayer1.contains(godId)) {
                godsSelectedPlayer1.remove(Integer.valueOf(godId));
            }

            godsPlayer1 = getGodsFromDB(godsSelectedPlayer1);
        }
    }
    public void newPlaySelectionScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "menuScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ENTER && keyboardEvent.isKeyPressed()) {
            if (godsSelectedPlayer2.size() == 3) {
                godsPlayer2 = getGodsFromDB(godsSelectedPlayer2);
                setupBattleArena();
                currentScreen = "battleScreen";
            }
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_LEFT && keyboardEvent.isKeyPressed()) {
            selectorX = Math.max(maximumValueLeft, currentPositionX - 160);
            currentPositionX = selectorX;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_RIGHT && keyboardEvent.isKeyPressed()) {
            selectorX = Math.min(maximumValueRight, currentPositionX + 160);
            currentPositionX = selectorX;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_DOWN && keyboardEvent.isKeyPressed()) {
            selectorY = Math.min(maximumValueDownY, currentPositionY + 180);
            currentPositionY = selectorY;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_UP && keyboardEvent.isKeyPressed()) {
            selectorY = Math.max(maximumValueUpY, currentPositionY - 180);
            currentPositionY = selectorY;
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_SPACE && keyboardEvent.isKeyPressed()) {
            int godId = getGodIdFromSelector();

            if (godsSelectedPlayer2.size() < 3 && !godsSelectedPlayer2.contains(godId)) {
                godsSelectedPlayer2.add(godId);
            } else if (godsSelectedPlayer2.contains(godId)) {
                godsSelectedPlayer2.remove(Integer.valueOf(godId));
            }

            godsPlayer2 = getGodsFromDB(godsSelectedPlayer2);
        }
    }
    public void battleScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (gameActive) {
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_I){
                currentScreen = "inGameInfoScreen";
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_O) {
                currentScreen = "inventoryScreen";
            }
            if (turnPlayer1) {
                if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_Q && arenaPlayers.get(0).gods.get(activeGodPlayer1).hp > 0) {
                    attackAnimation(1);
                    SaxionApp.playSound("BasicGame/" + arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(0).audioFileName);
                    int baseDamage = arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(0).damage;
                    int counterBonus = getGodCounter(arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(0), arenaPlayers.get(1).gods.get(activeGodPlayer2));
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp -= baseDamage * counterBonus;
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp += arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(0).healing;
                    turnPlayer1 = false;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_W && arenaPlayers.get(0).gods.get(activeGodPlayer1).hp > 0) {
                    attackAnimation(1);
                    SaxionApp.playSound("BasicGame/" + arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(1).audioFileName);
                    int baseDamage = arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(1).damage;
                    int counterBonus = getGodCounter(arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(1), arenaPlayers.get(1).gods.get(activeGodPlayer2));
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp -= baseDamage * counterBonus;
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp += arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(1).healing;
                    turnPlayer1 = false;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_E && arenaPlayers.get(0).gods.get(activeGodPlayer1).hp > 0) {
                    attackAnimation(1);
                    SaxionApp.playSound("BasicGame/" + arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(2).audioFileName);
                    int baseDamage = arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(2).damage;
                    int counterBonus = getGodCounter(arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(2), arenaPlayers.get(1).gods.get(activeGodPlayer2));
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp -= baseDamage * counterBonus;
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp += arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(2).healing;
                    turnPlayer1 = false;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_1 && activeGodPlayer1 != 0 && !(arenaPlayers.get(0).gods.get(0).hp <= 0)) {
                    activeGodPlayer1 = 0;
                    turnPlayer1 = false;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_2 && activeGodPlayer1 != 1 && !(arenaPlayers.get(0).gods.get(1).hp <= 0)) {
                    activeGodPlayer1 = 1;
                    turnPlayer1 = false;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_3 && activeGodPlayer1 != 2 && !(arenaPlayers.get(0).gods.get(2).hp <= 0)) {
                    activeGodPlayer1 = 2;
                    turnPlayer1 = false;
                    turn++;
                }
            } else {
                if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_Z && arenaPlayers.get(1).gods.get(activeGodPlayer2).hp > 0) {
                    attackAnimation(2);
                    SaxionApp.playSound("BasicGame/" + arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(2).audioFileName);
                    int baseDamage = arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(0).damage;
                    int counterBonus = getGodCounter(arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(0), arenaPlayers.get(0).gods.get(activeGodPlayer1));
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp -= baseDamage * counterBonus;
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp += arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(0).healing;
                    turnPlayer1 = true;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_X && arenaPlayers.get(1).gods.get(activeGodPlayer2).hp > 0) {
                    attackAnimation(2);
                    SaxionApp.playSound("BasicGame/" + arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(1).audioFileName);
                    int baseDamage = arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(1).damage;
                    int counterBonus = getGodCounter(arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(1), arenaPlayers.get(0).gods.get(activeGodPlayer1));
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp -= baseDamage * counterBonus;
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp += arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(1).healing;
                    turnPlayer1 = true;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_C && arenaPlayers.get(1).gods.get(activeGodPlayer2).hp > 0) {
                    attackAnimation(2);
                    SaxionApp.playSound("BasicGame/" + arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(0).audioFileName);
                    int baseDamage = arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(2).damage;
                    int counterBonus = getGodCounter(arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(2), arenaPlayers.get(0).gods.get(activeGodPlayer1));
                    arenaPlayers.get(0).gods.get(activeGodPlayer1).hp -= baseDamage * counterBonus;
                    arenaPlayers.get(1).gods.get(activeGodPlayer2).hp += arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(2).healing;
                    turnPlayer1 = true;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_8 && activeGodPlayer2 != 0 && !(arenaPlayers.get(1).gods.get(0).hp <= 0)) {
                    activeGodPlayer2 = 0;
                    turnPlayer1 = true;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_9 && activeGodPlayer2 != 1 && !(arenaPlayers.get(1).gods.get(1).hp <= 0)) {
                    activeGodPlayer2 = 1;
                    turnPlayer1 = true;
                    turn++;
                } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_0 && activeGodPlayer2 != 2 && !(arenaPlayers.get(1).gods.get(2).hp <= 0)) {
                    activeGodPlayer2 = 2;
                    turnPlayer1 = true;
                    turn++;
                }
            }

        }
        if (!gameActive && keyboardEvent.getKeyCode() == KeyboardEvent.VK_ENTER) {
            currentScreen = "endScreen";
        }
    }

    public void inGameInfoScreenKeyboardEvent(KeyboardEvent keyboardEvent){
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
            currentScreen = "battleScreen";
        }
    }
    public void endScreenKeyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_SPACE) {
            resetBattleArena();
            currentScreen = "menuScreen";
        } else if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_R) {
            setupBattleArena();
            currentScreen = "playSelectionScreen";
        }
    }
    private int getGodIdFromSelector() {
        int counter = 1;

        for (int y = 180; y <= 720; y += 180) {
            for (int x = 1350; x >= 870; x -= 160) {
                if (y == currentPositionY && x == currentPositionX) {
                    return counter;
                }

                counter++;
            }
        }

        return 0;
    }

    private int getGodCounter(Attack attack, God defGod) {
        if (attack.element.equals("water") && defGod.element.equals("fire")) {
            return 3;
        } else if (attack.element.equals("fire") && defGod.element.equals("earth")) {
            return 3;
        } else if (attack.element.equals("earth") && (defGod.element.equals("light") || defGod.element.equals("water"))) {
            return 3;
        } else if (attack.element.equals("light") && defGod.element.equals("darkness")) {
            return 3;
        } else if (attack.element.equals("darkness") && defGod.element.equals("light")) {
            return 3;
        }

        return 1;
    }

    private void setupBattleArena() {
        Player player1;
        Player player2;
        try {
            player1 = getPlayerFromDB(1); //temporary hardcoded id
            player1.gods = godsPlayer1;
            player2 = getPlayerFromDB(2); //temporary hardcoded id
            player2.gods = godsPlayer2;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        arenaPlayers.clear();
        arenaPlayers.add(player1);
        arenaPlayers.add(player2);

        activeGodPlayer1 = 0;
        activeGodPlayer2 = 0;
        turnPlayer1 = true;
        turn = 1;
        gameActive = true;
        winnerGame = 99;
    }

    private void resetBattleArena() {
        godsPlayer1.clear();
        godsSelectedPlayer1.clear();
        godsPlayer2.clear();
        godsSelectedPlayer2.clear();
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

    private ArrayList<God> getGodsFromDB(ArrayList<Integer> ids) {
        ArrayList<God> gods = new ArrayList<>();

        for (int id : ids) {
            God god = null;
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM god WHERE god_id = ?");
                statement.setInt(1, id);
                ResultSet results = statement.executeQuery();
                while (results.next()) {
                    god = new God();
                    god.id = results.getInt("god_id");
                    god.name = results.getString("name");
                    god.category = results.getString("category");
                    god.element = results.getString("element");
                    god.hp = results.getInt("health");
                }
                results.close();
                statement.close();

                if (god == null) {
                    throw new RuntimeException();
                }

                god.attacks = getAttacksByGodIdFromDB(god.id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

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
            attack.element = results.getString("element");
            attack.damage = results.getInt("base_damage");
            attack.healing = results.getInt("healing");
            attack.audioFileName = results.getString("sound_file_name");
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
    public void drawInventoryScreen1() {
        SaxionApp.drawImage("BasicGame/inventoryScreen1.png", 0, -10, 1500, 830);
    }
    public void drawInventoryScreen2() {
        SaxionApp.drawImage("BasicGame/inventoryScreen2.png", 0, -10, 1500, 830);
    }
    private void drawInstructionScreen() {
        SaxionApp.clear();
        SaxionApp.setBackgroundColor(Color.black);
        SaxionApp.drawImage("BasicGame/instructionText.png", 425, 25, 630, 135);
        SaxionApp.drawImage("BasicGame/explanationText.png", 40, 300, 600, 330);
        SaxionApp.drawImage("BasicGame/elementCounterText.png", 1000, 300, 400, 30);
        SaxionApp.drawImage("BasicGame/countersText.png", 1080, 350, 230, 250);
    }

    private void drawSecondInstructionScreen() {
        SaxionApp.clear();
        SaxionApp.setTextDrawingColor(Color.white);
        SaxionApp.drawImage("BasicGame/images/gods/AchillesFaceLeft.png", 40, 20, 170, 140);
        SaxionApp.drawImage("BasicGame/images/attacks/Spear Throw.png", 25, 155, 40, 40);
        SaxionApp.drawImage("BasicGame/images/attacks/shield Bash.png", 100, 155, 40, 40);
        SaxionApp.drawImage("BasicGame/images/attacks/scorpion Slash.png", 175, 155, 40, 40);

        SaxionApp.drawText("spear attack", 17, 200, 11);
        SaxionApp.drawText("(fire) (5)", 22, 215, 11);
        SaxionApp.drawText("shield bash", 92, 200, 11);
        SaxionApp.drawText("(earth) (3)", 97, 215, 11);
        SaxionApp.drawText("scorpion slash", 165, 200, 11);
        SaxionApp.drawText("(fire) (10)", 173, 215, 11);

        SaxionApp.drawImage("BasicGame/images/gods/AresFaceLeft.png", 260, 20, 240, 140);
        SaxionApp.drawImage("BasicGame/images/attacks/Spear Throw.png", 302, 155, 40, 40);
        SaxionApp.drawImage("BasicGame/images/attacks/shield Bash.png", 377, 155, 40, 40);
        SaxionApp.drawImage("BasicGame/images/attacks/battle Call.png", 452, 155, 40, 40);

        SaxionApp.drawText("spear attack", 291, 200, 11);
        SaxionApp.drawText("(fire) (5)", 295, 215, 11);
        SaxionApp.drawText("shield bash", 370, 200, 11);
        SaxionApp.drawText("(earth) (3)", 370, 215, 11);
        SaxionApp.drawText("battle call", 450, 200, 11);
        SaxionApp.drawText("(basic) (10)", 447, 215, 11);

        SaxionApp.drawImage("BasicGame/images/gods/AtlasFaceLeft.png", 600, 20, 240, 140);
        SaxionApp.drawImage("BasicGame/images/attacks/Shoulder Press.png", 550, 155, 40, 40);
    }

    private void drawPlaySelectionScreen() {
        drawGenericPlaySelectionScreen();

        SaxionApp.drawImage("BasicGame/selectPlayer1.png", 65, 50, 280, 100);

        int xPos = 50;

        for (God god : godsPlayer1) {
            SaxionApp.drawImage("BasicGame/images/gods/" + god.name + "FaceLeft.png", xPos, 230, 180, 130);
            xPos += 160;
        }
    }

    private void drawNewSelectionScreen() {
        drawGenericPlaySelectionScreen();

        SaxionApp.drawImage("BasicGame/selectPlayer2.png", 65, 50, 280, 100);

        int xPos = 50;

        for (God god : godsPlayer2) {
            SaxionApp.drawImage("BasicGame/images/gods/" + god.name + "FaceLeft.png", xPos, 230, 180, 130);
            xPos += 160;
        }
    }

    private void drawGenericPlaySelectionScreen() {
        SaxionApp.drawImage("BasicGame/images/gods/AchillesFaceLeft.png", 1300, 43, 170, 140);
        SaxionApp.drawImage("BasicGame/images/gods/AresFaceLeft.png", 1100, 50, 210, 130);
        SaxionApp.drawImage("BasicGame/images/gods/AtlasFaceLeft.png", 954, 50, 230, 130);
        SaxionApp.drawImage("BasicGame/images/gods/ApolloFaceLeft.png", 762, 50, 230, 130);

        SaxionApp.drawImage("BasicGame/images/gods/AnhurFaceLeft.png", 1280, 230, 185, 140);
        SaxionApp.drawImage("BasicGame/images/gods/AnubisFaceLeft.png", 1100, 230, 210, 130);
        SaxionApp.drawImage("BasicGame/images/gods/SobekFaceLeft.png", 950, 230, 180, 130);
        SaxionApp.drawImage("BasicGame/images/gods/HorusFaceLeft.png", 800, 230, 180, 130);

        SaxionApp.drawImage("BasicGame/images/gods/OdinFaceLeft.png", 1285, 410, 180, 130);
        SaxionApp.drawImage("BasicGame/images/gods/ThorFaceLeft.png", 1100, 410, 230, 140);
        SaxionApp.drawImage("BasicGame/images/gods/TyrFaceLeft.png", 930, 410, 215, 130);
        SaxionApp.drawImage("BasicGame/images/gods/UllrFaceLeft.png", 777, 400, 225, 145);

        SaxionApp.drawImage("BasicGame/images/gods/Ao KuangFaceLeft.png", 1300, 585, 160, 130);
        SaxionApp.drawImage("BasicGame/images/gods/Erlang ShenFaceLeft.png", 1120, 587, 180, 130);
        SaxionApp.drawImage("BasicGame/images/gods/Guan YuFaceLeft.png", 954, 585, 190, 136);
        SaxionApp.drawImage("BasicGame/images/gods/He BoFaceLeft.png", 780, 588, 180, 130);

        SaxionApp.drawText("Selected Gods:", 65, 150, 50);
        SaxionApp.drawBorderedText("Use the arrow keys to navigate and SPACE to (de)select", 65,450,20);
        SaxionApp.drawBorderedText("Press ENTER when you have selected your deck to continue ...", 65,525,20);

    }


    private void drawGameBoard() {
        SaxionApp.clear();
        SaxionApp.drawImage("BasicGame/BattleArena1.jpg", 0, 0, 1500, 750);

        SaxionApp.drawImage("BasicGame/images/attacks/" + arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(0).name + ".png", 130, 640, 100, 100);
        SaxionApp.drawImage("BasicGame/images/attacks/" + arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(1).name + ".png", 250, 640, 100, 100);
        SaxionApp.drawImage("BasicGame/images/attacks/" + arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(2).name + ".png", 370, 640, 100, 100);

        SaxionApp.drawImage("BasicGame/images/attacks/" + arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(0).name + ".png",1030 , 640, 100, 100);
        SaxionApp.drawImage("BasicGame/images/attacks/" + arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(1).name + ".png", 1150, 640, 100, 100);
        SaxionApp.drawImage("BasicGame/images/attacks/" + arenaPlayers.get(1).gods.get(activeGodPlayer2).attacks.get(2).name + ".png", 1270, 640, 100, 100);

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

        SaxionApp.drawImage("BasicGame/iginfotext.png",625,700, 250, 50);
        drawElementSymbols();
    }

    public void drawInGameInfoScreen(){
        SaxionApp.drawImage("BasicGame/infoscreen.jpg",0, 0, 1500, 750);
        SaxionApp.drawBorderedText("Press ESC to continue...", 610,700,25);
    }

    public void drawEndScreen() {
        SaxionApp.drawImage("BasicGame/background endscreen.jpg", 0, 0, 1500, 750);

        SaxionApp.setBorderColor(Color.black);
        SaxionApp.setFill(Color.cyan);
        SaxionApp.drawBorderedText(arenaPlayers.get(winnerGame).name + " has won the game in " + turn + " turns!", 325, 200, 50);

        SaxionApp.drawBorderedText("Press SPACE to continue...", 525, 300, 40);
        SaxionApp.drawBorderedText("Press R for rematch", 525, 350, 40);

        SaxionApp.setFill(Color.black);
        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(winnerGame).gods.get(0).name + "Card.png", 350, 425, 100, 140);
        SaxionApp.drawBorderedText(arenaPlayers.get(winnerGame).gods.get(0).name, 360, 400, 25);
        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(winnerGame).gods.get(1).name + "Card.png", 700, 425, 100, 140);
        SaxionApp.drawBorderedText(arenaPlayers.get(winnerGame).gods.get(1).name, 710, 400, 25);
        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(winnerGame).gods.get(2).name + "Card.png", 1050, 425, 100, 140);
        SaxionApp.drawBorderedText(arenaPlayers.get(winnerGame).gods.get(2).name, 1060, 400, 25);
    }

    private void characters() {
        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(0).gods.get(activeGodPlayer1).name + "FaceRight.png", god1Position, 325, 320, 270);
        SaxionApp.drawImage("BasicGame/images/gods/" + arenaPlayers.get(1).gods.get(activeGodPlayer2).name + "FaceLeft.png", god2Position, 325, 470, 270);
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

    public void selector() {
        SaxionApp.setBorderColor(Color.blue);
        SaxionApp.setFill(Color.green);
        SaxionApp.drawRectangle(selectorX, selectorY, 60, 10);
    }


    public void drawElementSymbols() {
        switch (arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(0).element) {
            case "fire" -> SaxionApp.drawImage("BasicGame/images/elements/fire.png.png", 160, 600, 35, 35);
            case "water" -> SaxionApp.drawImage("BasicGame/images/elements/water.png.png", 160, 600, 35, 35);
            case "earth" -> SaxionApp.drawImage("BasicGame/images/elements/earth.png.png", 160, 600, 35, 35);
            case "light" -> SaxionApp.drawImage("BasicGame/images/elements/light.png.png", 160, 600, 35, 35);
            case "dark" -> SaxionApp.drawImage("BasicGame/images/elements/dark.png.png", 160, 600, 35, 35);
        }

        switch (arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(1).element) {
            case "fire" -> SaxionApp.drawImage("BasicGame/images/elements/fire.png.png", 280, 600, 35, 35);
            case "water" -> SaxionApp.drawImage("BasicGame/images/elements/water.png.png", 280, 600, 35, 35);
            case "earth" -> SaxionApp.drawImage("BasicGame/images/elements/earth.png.png", 280, 600, 35, 35);
            case "light" -> SaxionApp.drawImage("BasicGame/images/elements/light.png.png", 280, 600, 35, 35);
            case "dark" -> SaxionApp.drawImage("BasicGame/images/elements/dark.png.png", 280, 600, 35, 35);
        }

        switch (arenaPlayers.get(0).gods.get(activeGodPlayer1).attacks.get(2).element) {
            case "fire" -> SaxionApp.drawImage("BasicGame/images/elements/fire.png.png", 400, 600, 35, 35);
            case "water" -> SaxionApp.drawImage("BasicGame/images/elements/water.png.png", 400, 600, 35, 35);
            case "earth" -> SaxionApp.drawImage("BasicGame/images/elements/earth.png.png", 400, 600, 35, 35);
            case "light" -> SaxionApp.drawImage("BasicGame/images/elements/light.png.png", 400, 600, 35, 35);
            case "dark" -> SaxionApp.drawImage("BasicGame/images/elements/dark.png.png", 400, 600, 35, 35);
        }

        switch (arenaPlayers.get(1).gods.get(activeGodPlayer1).attacks.get(0).element) {
            case "fire" -> SaxionApp.drawImage("BasicGame/images/elements/fire.png.png", 1060, 600, 35, 35);
            case "water" -> SaxionApp.drawImage("BasicGame/images/elements/water.png.png", 1060, 600, 35, 35);
            case "earth" -> SaxionApp.drawImage("BasicGame/images/elements/earth.png.png", 1060, 600, 35, 35);
            case "light" -> SaxionApp.drawImage("BasicGame/images/elements/light.png.png", 1060, 600, 35, 35);
            case "dark" -> SaxionApp.drawImage("BasicGame/images/elements/dark.png.png", 1060, 600, 35, 35);
        }

        switch (arenaPlayers.get(1).gods.get(activeGodPlayer1).attacks.get(1).element) {
            case "fire" -> SaxionApp.drawImage("BasicGame/images/elements/fire.png.png", 1180, 600, 35, 35);
            case "water" -> SaxionApp.drawImage("BasicGame/images/elements/water.png.png", 1180, 600, 35, 35);
            case "earth" -> SaxionApp.drawImage("BasicGame/images/elements/earth.png.png", 1180, 600, 35, 35);
            case "light" -> SaxionApp.drawImage("BasicGame/images/elements/light.png.png", 1180, 600, 35, 35);
            case "dark" -> SaxionApp.drawImage("BasicGame/images/elements/dark.png.png", 1180, 600, 35, 35);
        }

        switch (arenaPlayers.get(1).gods.get(activeGodPlayer1).attacks.get(2).element) {
            case "fire" -> SaxionApp.drawImage("BasicGame/images/elements/fire.png.png", 1300, 600, 35, 35);
            case "water" -> SaxionApp.drawImage("BasicGame/images/elements/water.png.png", 1300, 600, 35, 35);
            case "earth" -> SaxionApp.drawImage("BasicGame/images/elements/earth.png.png", 1300, 600, 35, 35);
            case "light" -> SaxionApp.drawImage("BasicGame/images/elements/light.png.png", 1300, 600, 35, 35);
            case "dark" -> SaxionApp.drawImage("BasicGame/images/elements/dark.png.png", 1300, 600, 35, 35);
        }
    }



    @Override
    public void mouseEvent (MouseEvent mouseEvent){}
}
