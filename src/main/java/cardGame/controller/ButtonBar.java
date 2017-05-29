package cardGame.controller;

import cardGame.game.Solitaire;

import javax.swing.*;

/**
 * Panel with the buttons for the draw-class controllers
 */
public class ButtonBar extends JMenuBar {

    /**
     * Create a new buttonpanel with all the necessary buttons
     */
    public ButtonBar(Solitaire solitaire) {
        add(new ResetButton(solitaire));
    }

}
