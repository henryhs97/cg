package cardGame.controller;

import cardGame.game.Solitaire;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Represents an action made to shuffle all cards back into the deck. Although
 * useless on an empty discard pile, this action is always available.
 */
public class ResetAction extends AbstractAction {

    private Solitaire solitaire;

    /**
     * Creates a new action to shuffle all cards back into the deck
     */
    public ResetAction(Solitaire solitaire) {
        super("Reset game");
        this.solitaire = solitaire;
    }

    /**
     * Draws a card
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        solitaire.reset();
    }
    
}
