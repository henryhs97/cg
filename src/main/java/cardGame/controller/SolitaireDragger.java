package cardGame.controller;

import cardGame.game.Solitaire;
import cardGame.view.SolitairePanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

/**
 * The class used in order to properly drag cards.
 * selectedDeck indicates which deck the mouse clicked on, -1 is for none. 
 * index is to know which part of a deck was clicked.
 */
public class SolitaireDragger extends MouseInputAdapter {

    private Solitaire solitaire;
    private SolitairePanel panel;

    private int selectedDeck;
    private int index;
    private int startX;
    private int startY;

    /**
     * Create a new card dragger that receives mouse events from SolitairePanel
     * supplied to this constructor
     */
    public SolitaireDragger(Solitaire solitaire, SolitairePanel panel) {
        this.solitaire = solitaire;
        this.panel = panel;
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        selectedDeck = -1;
    }

    /**
     * Sets the correct parameters when the mouse is pressed.
     */
    @Override
    public void mousePressed(MouseEvent event) {   	
    	int deckNum= panel.inArea(event.getPoint());
	    if(solitaire.getMovablePile(deckNum) != null) {
            for(int cardNum = 0; cardNum < solitaire.getMovablePile(deckNum).size(); cardNum++) {
                if (    event.getX() > panel.getMovableX(deckNum) &&
                        event.getX() < panel.getMovableX(deckNum) + panel.cardWidth() &&
                        event.getY() > panel.getMovableY(deckNum) - panel.getCardSpacing() * (cardNum) &&
                        event.getY() < panel.getMovableY(deckNum) + panel.cardHeight() - panel.getCardSpacing() * (cardNum)
                        ) {
                    selectedDeck = deckNum;
                    if(selectedDeck < 8) {
                        index = solitaire.getMovablePile(deckNum).size() - 1 - cardNum;
                    }else{
                        index = solitaire.getMovablePile(deckNum).size() - 1;
                    }
                    solitaire.getMovablePile(deckNum).setIndex(index);                    
                    startX = event.getX();
                    startY = event.getY();
                    break;
                }
            }
        }
        
    }

    /**
     * If a deck has been selected (not -1) the pile is moved appropriately.
     * selectedDeck becomes false afterwards (-1)
     */
    @Override
    public void mouseReleased(MouseEvent event) {
    	if(selectedDeck != -1) {
			int toWhichDeck = panel.inArea(event.getPoint());
            if ((selectedDeck != toWhichDeck || (toWhichDeck == 0 && selectedDeck == 0)))
                solitaire.move(selectedDeck, toWhichDeck, index);

            solitaire.getMovablePile(selectedDeck).setRelativeX(0);
            solitaire.getMovablePile(selectedDeck).setRelativeY(0);
    	}
        selectedDeck = -1;
    }

    /**
     * If a pile is selected it is moved relative to the positions the mouse was first pressed.
     */
    @Override
    public void mouseDragged(MouseEvent event) {
    		if(selectedDeck != -1 && selectedDeck < solitaire.getNumOfTotalDecks() ) {
                solitaire.getMovablePile(selectedDeck).setRelativeX(event.getX() - startX);
                solitaire.getMovablePile(selectedDeck).setRelativeY(event.getY() - startY);
            }
    }
    
}
