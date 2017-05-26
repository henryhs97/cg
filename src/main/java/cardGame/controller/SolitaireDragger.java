package cardGame.controller;

import cardGame.game.Solitaire;
import cardGame.view.SolitairePanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by sibdoo on 25/05/2017.
 */
public class SolitaireDragger extends MouseInputAdapter {

    private Solitaire solitaire;
    private SolitairePanel panel;

    private int selectedDeck;
    private int index;
    private int startX;
    private int startY;

    /**
     * Create a new card dragger that receives mouse events from the DrawPanel
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
     * If the mouse button is pressed in the area where the top card is
     * drawn (obviously a lack of drawable cards makes this impossible)
     * that card is 'selected' so it can be dragged.
     */
    @Override
    public void mousePressed(MouseEvent event) {
    	for(int deckNum = 0; deckNum < solitaire.getNumOfDecks(); deckNum++) {
    	    if(solitaire.getMovablePile(deckNum) != null) {
                int pileHeight = solitaire.getMovablePile(deckNum).size() * panel.getCardSpacing();
                for (int cardNum = 0; cardNum < solitaire.getMovablePile(deckNum).size(); cardNum++) {
                    if (event.getX() > panel.getMovableX(deckNum) &&
                            event.getX() < panel.getMovableX(deckNum) + panel.cardWidth() &&
                            event.getY() > panel.getMovableY(deckNum) +
                                    (pileHeight - panel.getCardSpacing() * (cardNum + 1)) &&
                            event.getY() < panel.getMovableY(deckNum) + panel.cardHeight() +
                                    (pileHeight - panel.getCardSpacing() * (cardNum + 1))
                            ) {
                        selectedDeck = deckNum;
                        index = cardNum;
                        System.out.println(index + " " + selectedDeck);
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    }
                }
            }
        }
    }

    /**
     * When the top card is released with the mouse in the discard square,
     * the card is moved.
     *
     * TODO: detect action
     * TODO: fire action
     */
    @Override
    public void mouseReleased(MouseEvent event) {
    	for(int i = 0; i < solitaire.getNumOfDecks(); i++) {
    		if(i == selectedDeck) {
    			for(int j = 0; j < solitaire.getNumOfDecks(); j++) {
    				if(panel.inArea(event.getPoint()) == j) {
		                if (i != j){
		                    solitaire.move(selectedDeck, j, index);
		                    break;
		                } else {
		                    solitaire.getMovablePile(i).setRelativeX(0);
		                    solitaire.getMovablePile(i).setRelativeY(0);
		                    break;
		                }
    				}
    			}
    			break;
    		}
    	}
        selectedDeck = -1;
    }

    /**
     * If a card is selected it is moved relative to the positions the mouse
     * was first pressed.
     */
    @Override
    public void mouseDragged(MouseEvent event) {
    	for(int i = 0; i < solitaire.getNumOfDecks(); i++) {
    		if(selectedDeck == i) {
                solitaire.getMovablePile(i).setRelativeX(event.getX() - startX);
                solitaire.getMovablePile(i).setRelativeY(event.getY() - startY);
            }
    	}
    }

}
