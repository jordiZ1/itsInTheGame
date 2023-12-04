import nl.saxion.app.SaxionApp;

import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

public class BasicGame implements GameLoop {

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1500, 750, 40);
    }

    God dummy = new God();
    God dummy2 = new God();


    @Override
    public void init() {

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
        SaxionApp.drawText(dummy.name, 100, 100, 50);
        SaxionApp.drawText(String.valueOf(dummy.hp),100,200,50);
        SaxionApp.drawText(dummy2.name, 1300, 100,50);
        SaxionApp.drawText(String.valueOf(dummy2.hp),1300,200,50);




    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {


    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {


    }

}






