package cardGame.controller;

import cardGame.game.Solitaire;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Represents an action made to shuffle all cards back into the deck. Although
 * useless on an empty discard pile, this action is always available.
 */
public class ResetAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private Solitaire solitaire;

    /**
     * Resets the game
     */
    public ResetAction(Solitaire solitaire) {
        super("Reset game");
        this.solitaire = solitaire;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        solitaire.reset();
    }
    
}
