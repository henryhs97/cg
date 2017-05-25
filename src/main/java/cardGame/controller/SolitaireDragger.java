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

    private int selected;
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
        selected = -1;
    }

    /**
     * If the mouse button is pressed in the area where the top card is
     * drawn (obviously a lack of drawable cards makes this impossible)
     * that card is 'selected' so it can be dragged.
     */
    @Override
    public void mousePressed(MouseEvent event) {
    	for(int cardNum = 0; cardNum<solitaire.getNumOfDecks(); cardNum++) {
    	       if(     event.getX() > panel.getMovableX(cardNum) &&
                       event.getX() < panel.getMovableX(cardNum) + panel.cardWidth() &&
                       event.getY() > panel.getMovableY(cardNum) &&
                       event.getY() < panel.getMovableY(cardNum) + panel.cardHeight()
                       ) {
                   selected = cardNum;
                   startX = event.getX();
                   startY = event.getY();
                   break;
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
    		if(i == selected) {	
    			for(int j = 0; j < solitaire.getNumOfDecks(); j++) {
    				if(panel.inArea(event.getPoint()) == j) {
		                if (i != j){
		                    solitaire.move(selected, j);
		                    break;
		                } else {
		                    solitaire.getMovableCard(i).setRelativeX(i);
		                    solitaire.getMovableCard(i).setRelativeY(i);
		                    break;
		                }
    				}
    			}
    			break;
    		}
    	}
        selected = -1;
    }

    /**
     * If a card is selected it is moved relative to the positions the mouse
     * was first pressed.
     */
    @Override
    public void mouseDragged(MouseEvent event) {
    	for(int i = 0; i < solitaire.getNumOfDecks(); i++) {
    		if(selected == i) {
                solitaire.getMovableCard(i).setRelativeX(event.getX() - startX);
                solitaire.getMovableCard(i).setRelativeY(event.getY() - startY);
            }
    	}
    }

}
