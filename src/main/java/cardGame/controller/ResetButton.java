package cardGame.controller;

import cardGame.game.Solitaire;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Button that shuffles all cards into the deck. It uses the Action API to 
 * perform its action which means that this is merely a default configuration 
 * for this button.
 */
public class ResetButton extends JButton {
  
	private static final long serialVersionUID = 1L;

	/**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
        setMnemonic(KeyEvent.VK_R);
        setToolTipText("Reset the game");
    }
    
    /**
     * Create a reset button
     */
    public ResetButton(Solitaire solitaire) {
        super(new ResetAction(solitaire));
        setButtonProperties();
    }

}
