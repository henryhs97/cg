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
    	int deckNum= panel.inArea(event.getPoint());
	    if(solitaire.getMovablePile(deckNum) != null) {
            for (int cardNum = 0; cardNum < solitaire.getMovablePile(deckNum).size(); cardNum++) {
                if (event.getX() > panel.getMovableX(deckNum) &&
                        event.getX() < panel.getMovableX(deckNum) + panel.cardWidth() &&
                        event.getY() > panel.getMovableY(deckNum) - panel.getCardSpacing() * (cardNum) &&
                        event.getY() < panel.getMovableY(deckNum) + panel.cardHeight() - panel.getCardSpacing() * (cardNum)
                        ) {
                    selectedDeck = deckNum;
                    System.out.println("the selected deck is " + selectedDeck);
                    index = solitaire.getMovablePile(deckNum).size() - 1 - cardNum;
                    System.out.println("THE INDEX IS" + index);
                    solitaire.getMovablePile(deckNum).setIndex(index);
                    System.out.println("card number is" + solitaire.getMovablePile(deckNum).getCard().getSuit() );
                    System.out.println(event.getY() + " " + panel.getMovableY(deckNum) + " " + (panel.getMovableY(deckNum) + panel.cardHeight()));
                    startX = event.getX();
                    startY = event.getY();
                    break;
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
    	if(selectedDeck!= -1) {
			int j = panel.inArea(event.getPoint());
				if (selectedDeck != j){
					solitaire.move(selectedDeck, j, index);
                    solitaire.getMovablePile(selectedDeck).setRelativeX(0);
                    solitaire.getMovablePile(selectedDeck).setRelativeY(0);
	            } else {
	                solitaire.getMovablePile(selectedDeck).setRelativeX(0);
	                solitaire.getMovablePile(selectedDeck).setRelativeY(0);       
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
    	for(int i = 0; i < solitaire.getNumOfTotalDecks(); i++) {
    		if(selectedDeck == i) {
                solitaire.getMovablePile(i).setRelativeX(event.getX() - startX);
                solitaire.getMovablePile(i).setRelativeY(event.getY() - startY);
            }
    	}
    }

}
