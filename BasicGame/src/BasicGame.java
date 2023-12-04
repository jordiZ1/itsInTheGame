import nl.saxion.app.SaxionApp;

import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;


public class BasicGame implements GameLoop {

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1500, 750, 40);
    }




    @Override
    public void init() {


    }

    @Override
    public void loop() {
        SaxionApp.drawImage("BasicGame/BattleArena1.jpg", 0,0, 1500, 750);

        SaxionApp.drawImage("BasicGame/redAbility.png",130,580,110,200);
        SaxionApp.drawImage("BasicGame/greenAbility.png",250,640,135,80);
        SaxionApp.drawImage("BasicGame/blueAbility.png",395,640,110,80);

        SaxionApp.drawImage("BasicGame/redAbility.png",1260,580,110,200);
        SaxionApp.drawImage("BasicGame/greenAbility.png",1120,640,135,80);
        SaxionApp.drawImage("BasicGame/blueAbility.png",1000,640,110,80);

        SaxionApp.drawImage("BasicGame/healthBar.png",200,-50,360,190);
        SaxionApp.drawImage("BasicGame/healthBar.png",920,-50,360,190);

        SaxionApp.drawImage("BasicGame/redCard.png",50,130,80,120);
        SaxionApp.drawImage("BasicGame/greenCard.png/",50,320,80,120);
        SaxionApp.drawImage("BasicGame/blueCard.png",50,510,80,120);

        SaxionApp.drawImage("BasicGame/redCard.png",1370,130,80,120);
        SaxionApp.drawImage("BasicGame/greenCard.png",1370,320,80,120);
        SaxionApp.drawImage("BasicGame/blueCard.png",1370,510,80,120);


    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {

    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }
}






