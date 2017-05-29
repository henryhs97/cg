package cardGame.view;

import cardGame.game.MovablePile;
import cardGame.game.Solitaire;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class in order to draw the decks and panel on the screen.
 */
public class SolitairePanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private static final int BIG_CARD_SPACING = 20; //pixels
    private static final int SMALL_CARD_SPACING = 2; //pixels

    private Solitaire solitaire;

    private List<Integer> movablesX = new ArrayList<>();

    private List<Integer> movablesY = new ArrayList<>();

    public int getMovableX (int card) { 
    	return movablesX.get(card); 
    }

    public int getMovableY (int card) { 
    	return movablesY.get(card); 
    }

    public SolitairePanel(Solitaire solitaire) {
        this.solitaire = solitaire;
        solitaire.addObserver(this);
        setBackground(new Color(0,117,9));
        setVisible(true);
        setOpaque(true);
        for(int i = 0; i < solitaire.getNumOfTotalDecks(); i++){  //initialize
            movablesX.add(0);
            movablesY.add(0);
        }
    }

    /**
     * Returns in which area you are in. (0-11)
     */
    public int inArea(Point point) { 
    	int columnDeck= (int) point.getX() / (getWidth() / solitaire.getNumOfColumns());
    	/* If the column is 8, then there are 4 areas in this column, so they are divided appropriately */
    	if(columnDeck == 8) {

    		return 8 + (int) point.getY() / (getHeight() / 4);
    	}
        return (int) point.getX() / (getWidth() / solitaire.getNumOfColumns());
    }

    /**
     * Paints the rectangles to indicate the location of side decks.
     */
    private void paintSideDeckAreas(Graphics g) {
        int numDecks = solitaire.getNumOfColumns();
        g.setColor(Color.darkGray);
    	for(int j = 0; j < 4; j++) {
    		g.drawRect((numDecks-1) * getWidth() / numDecks, j*getHeight()/4, getWidth() / numDecks, getHeight()/4);
    	}
        g.setColor(Color.BLACK);
    }
    
    /**
     * Get the scaled spacing between edges and cards
     */
    private int getSpacing() {
        if((getHeight() * 1000) / (getWidth() * 600) < 1.0)
            return (int) ((getHeight() * 20) / 600.0);
        return (getWidth() * 20)/1000;
    }

    //Assures that a pile of 21 (largest possible size) cards can fit onto the screen
    public int getCardSpacing() {
        if(getHeight() < 2 * getSpacing() + 19 * BIG_CARD_SPACING)
    	    return (getHeight() - 2 * getSpacing())/19;
        return BIG_CARD_SPACING;
    }

    /**
     * Get the scaled width of cards. 
     * Cards are scaled depending their which dimension limits
     */
    public int cardWidth() {
        if((getHeight() * 1000) / (getWidth() * 600) < 1.0)
            return (int) ((cardHeight() * 436.0) / 600.0);
        return getWidth()/9 - 2 * getSpacing();
    }

    /**
     * Get the scaled height of cards. 
     * Cards are scaled depending their which dimension limits
     */
    public int cardHeight() {
        if((getHeight() * 1000) / (getWidth() * 600) >= 1.0)
            return (int) ((cardWidth() * 600.0) / 436.0);
        return getHeight() / 4 - (getSpacing() * 5)/2;
    }

    /**
     * Paints the decks on the screen.
     */
    private void paintDecks(Graphics g) {
        int depth;
        int currMovable = -1; //which movable needs to be moved? -1 for none, otherwise corresponds to a deck number.
        int numColumnDecks = solitaire.getNumOfColumns(), numTotalDecks = solitaire.getNumOfTotalDecks();
        Integer movableX = 0, movableY = 0;
        
        /* Draws the main deck, and the 7 tableau decks with cards back-faced.*/
        for(int deckNum = 0; deckNum < numColumnDecks-1; deckNum++) {
            for (depth = 0; depth < solitaire.getDeck(deckNum).size(); ++depth) {
                int posX = getSpacing()  + deckNum * getWidth()/numColumnDecks;
                
                int posY = deckNum==0 ? getSpacing()  + SMALL_CARD_SPACING * depth : getSpacing()  + getCardSpacing() * depth;
                g.drawImage(CardBackTextures.getTexture(CardBack.CARD_BACK_BLUE)
                        , posX, posY, cardWidth(), cardHeight(), this);
                g.drawRect(posX, posY, cardWidth(), cardHeight());
            }
        }
      
        /* Draws the face-up movable cards for the above decks. */
        for(int deckNum = 0; deckNum < numColumnDecks-1; deckNum++) {
            MovablePile dependency = solitaire.getMovablePile(deckNum);
         // something moved?
            if(dependency != null && (dependency.getRelativeX() != 0 || dependency.getRelativeY() != 0)) { 
                currMovable = deckNum;
                //nothing moved, so draw normally
            } else if (dependency != null) {
                for (depth = 0; depth < solitaire.getMovablePile(deckNum).size(); ++depth) {
                    movableX = getSpacing() + deckNum * getWidth() / numColumnDecks;
                    
                    movableY = deckNum==0 ? getSpacing() + SMALL_CARD_SPACING * solitaire.getDeck(deckNum).size() + SMALL_CARD_SPACING * depth
                    		: getSpacing() + getCardSpacing() * solitaire.getDeck(deckNum).size() + getCardSpacing() * depth;
                    g.drawImage(CardTextures.getTexture(solitaire.getMovablePile(deckNum).getCardAt(depth))
                            , movableX, movableY, cardWidth(), cardHeight(), this);
                    g.drawRect(movableX, movableY, cardWidth(), cardHeight());
                }
                movablesX.set(deckNum, movableX);
                movablesY.set(deckNum, movableY);
            }
        }
        
        /* Draws the side decks. movableX remains constant.*/
        movableX = getSpacing()  + (numColumnDecks-1) * getWidth()/numColumnDecks;    
        for(int deckNum = numColumnDecks-1; deckNum < numTotalDecks; deckNum++) {
            MovablePile dependency = solitaire.getMovablePile(deckNum);
         // something moved?
            if(dependency != null && (dependency.getRelativeX() != 0 || dependency.getRelativeY() != 0)) { 
                currMovable = deckNum;
                //nothing moved, so draw normally
            } else if (dependency != null) {
                for (depth = 0; depth < solitaire.getMovablePile(deckNum).size(); ++depth) {
                    movableY = getSpacing()  + (deckNum-8)*getHeight()/4;
                    
                    g.drawImage(CardTextures.getTexture(solitaire.getMovablePile(deckNum).getCardAt(depth))
                            , movableX, movableY, cardWidth(), cardHeight(), this);
                    g.drawRect(movableX, movableY, cardWidth(), cardHeight());
                }
                movablesX.set(deckNum, movableX);
                movablesY.set(deckNum, movableY);
            }
        }

        /* If currMovable is not false, then something is moving. This draws it. */
        if(currMovable != -1) {
            MovablePile dependency = solitaire.getMovablePile(currMovable);
            int index = solitaire.getMovablePile(currMovable).getIndex(); 
            /* Draws the cards on the bottom of this pile. i.e. the faced-back cards not selected */
            for (depth = 0; depth < index; ++depth) { //only paint until before the index
            	if(currMovable>8) {
            		movableX = getSpacing() + 8 * getWidth() / numColumnDecks;
            	} else {
            		movableX = getSpacing() + currMovable * getWidth() / numColumnDecks;
            	}     
            	movableY= setMovableY(currMovable, depth);
 
                g.drawImage(CardTextures.getTexture(solitaire.getMovablePile(currMovable).getCardAt(depth))
                        , movableX, movableY, cardWidth(), cardHeight(), this);
                g.drawRect(movableX, movableY, cardWidth(), cardHeight());
            }
            /* Draws the cards that are moving. */
            for (depth = index; depth < solitaire.getMovablePile(currMovable).size(); depth++) {
                movableX = currMovable> 7 ? getSpacing() + 8 * getWidth() / numColumnDecks + dependency.getRelativeX()
                		: getSpacing() + currMovable * getWidth() / numColumnDecks + dependency.getRelativeX();      
                movableY= setMovableY(currMovable, depth) + dependency.getRelativeY();
      
                g.drawImage(CardTextures.getTexture(solitaire.getMovablePile(currMovable).getCardAt(depth))
                        , movableX, movableY, cardWidth(), cardHeight(), this);
                g.drawRect(movableX, movableY, cardWidth(), cardHeight());
            }
            movablesX.set(currMovable, movableX);
            movablesY.set(currMovable, movableY);
        }
    }
    
    /**
     * Returns the appropriate valuable for movableY 
     * depending on the deck needed.
     */
    private int setMovableY(int currMovable, int depth) {
    	int movableY;
    	if(currMovable > 7) {
       	 movableY = getSpacing()  + (currMovable-8)*getHeight()/4;

       } else if(currMovable == 0) {
       	movableY = getSpacing() + SMALL_CARD_SPACING * solitaire.getDeck(currMovable).size() + SMALL_CARD_SPACING * depth;

       } else {
       	movableY =  getSpacing() + getCardSpacing() * solitaire.getDeck(currMovable).size() + getCardSpacing() * depth;
       }
    	return movableY;
    }

    @Override
    public void paintComponent(Graphics g) {
        if(solitaire.getWinState()) {
            g.setColor(new Color(255, 255, 255));
            g.fillRect(getWidth()/2 - 20,getHeight()/2, 65, 20);
            g.setColor(new Color(0,0,0));
        	g.drawString("YOU WIN!",getWidth()/2 - 18,getHeight()/2 + 13);
        }else{
            super.paintComponent(g);
            paintSideDeckAreas(g);
            paintDecks(g);
        }
    }

    @Override
    public void update(Observable observed, Object message) {
        repaint();
    }
}
