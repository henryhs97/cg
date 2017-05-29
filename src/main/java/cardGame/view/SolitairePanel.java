package cardGame.view;

import cardGame.game.MovablePile;
import cardGame.game.Solitaire;
import cardGame.model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 
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
        setBackground(new Color(116, 199, 203));
        setVisible(true);
        setOpaque(true);
        for(int i = 0; i < solitaire.getNumOfTotalDecks(); i++){  //initialize
            movablesX.add(0);
            movablesY.add(0);
        }
    }

    public int inArea(Point point) { //returns in which area you're in
    	int columnDeck= (int) point.getX() / (getWidth() / solitaire.getNumOfColumns());
    	if(columnDeck == 8) { //area for side decks is 8,9,10,11
    		return 8 + (int) point.getY() / (getHeight() / 4);
    	}
        return (int) point.getX() / (getWidth() / solitaire.getNumOfColumns());
    }

    private void paintAreas(Graphics g) {
        int numDecks = solitaire.getNumOfColumns();
        g.setColor(Color.YELLOW);
        for(int i = 0; i < numDecks; i++) {
            g.drawRect(i * getWidth() / numDecks, 0, getWidth() / numDecks, getHeight() - 1);
            if(i == numDecks-1) {
            	for(int j = 0; j < 4; j++) {
            		g.drawRect(i * getWidth() / numDecks, j*getHeight()/4, getWidth() / numDecks, getHeight()/4);
            	}
            }
        }
        g.setColor(Color.BLACK);
    }

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

    public int cardWidth() {
        if((getHeight() * 1000) / (getWidth() * 600) < 1.0)
            return (int) ((cardHeight() * 436.0) / 600.0);
        return getWidth()/9 - 2 * getSpacing();
    }

    public int cardHeight() {
        if((getHeight() * 1000) / (getWidth() * 600) >= 1.0)
            return (int) ((cardWidth() * 600.0) / 436.0);
        return getHeight() / 4 - (getSpacing() * 5)/2;
    }

    private void paintDecks(Graphics g) {
        int depth;
        int currMovable = -1;
        int numColumnDecks = solitaire.getNumOfTotalDecks()-3; //9
        int numTotalDecks = solitaire.getNumOfTotalDecks();
        Integer movableX = 0;
        Integer movableY = 0;
        
        /* draws the decks with backwards suit */
        for(int deckNum = 0; deckNum < numColumnDecks-1; deckNum++) {
            for (depth = 0; depth < solitaire.getDeck(deckNum).size(); ++depth) {
                int posX = getSpacing()  + deckNum * getWidth()/numColumnDecks;
                
                int posY = deckNum==0 ? getSpacing()  + SMALL_CARD_SPACING * depth : getSpacing()  + getCardSpacing() * depth;
                g.drawImage(CardBackTextures.getTexture(CardBack.CARD_BACK_BLUE)
                        , posX, posY, cardWidth(), cardHeight(), this);
                g.drawRect(posX, posY, cardWidth(), cardHeight());
            }
        }

        
        /* for each deck on the table, it creates a movable pile */
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
        
        //for side decks
        int posX = getSpacing()  + (numColumnDecks-1) * getWidth()/numColumnDecks;    
        for(int deckNum = numColumnDecks-1; deckNum < numTotalDecks; deckNum++) {
            MovablePile dependency = solitaire.getMovablePile(deckNum);
         // something moved?
            if(dependency != null && (dependency.getRelativeX() != 0 || dependency.getRelativeY() != 0)) { 
                currMovable = deckNum;
                //nothing moved, so draw normally
            } else if (dependency != null) {
                for (depth = 0; depth < solitaire.getMovablePile(deckNum).size(); ++depth) {
                    movableX = posX;
                    movableY = getSpacing()  + (deckNum-8)*getHeight()/4;
                    
                    g.drawImage(CardTextures.getTexture(solitaire.getMovablePile(deckNum).getCardAt(depth))
                            , movableX, movableY, cardWidth(), cardHeight(), this);
                    g.drawRect(posX, movableY, cardWidth(), cardHeight());
                }
                movablesX.set(deckNum, movableX);
                movablesY.set(deckNum, movableY);
            }
        }

        //something is currently moving, so draw it while it moves
        if(currMovable != -1) {
            MovablePile dependency = solitaire.getMovablePile(currMovable);
            int index = solitaire.getMovablePile(currMovable).getIndex(); //start moving from where?
            for (depth = 0; depth < index; ++depth) { //only paint until before the index
            		movableX = getSpacing() + currMovable * getWidth() / numColumnDecks;
                
                if(currMovable == 8 || currMovable == 9 || currMovable == 10 || currMovable == 11) {
                	 movableY = getSpacing()  + (currMovable-8)*getHeight()/4;
                }
                else if(currMovable == 0) {
                	movableY = getSpacing() + SMALL_CARD_SPACING * solitaire.getDeck(currMovable).size() + SMALL_CARD_SPACING * depth;
                } else {
                	movableY =  getSpacing() + getCardSpacing() * solitaire.getDeck(currMovable).size() + getCardSpacing() * depth;
                }
                
                
                g.drawImage(CardTextures.getTexture(solitaire.getMovablePile(currMovable).getCardAt(depth))
                        , movableX, movableY, cardWidth(), cardHeight(), this);
                g.drawRect(movableX, movableY, cardWidth(), cardHeight());
            } //after the index you move these
            for (depth = index; depth < solitaire.getMovablePile(currMovable).size(); ++depth) {
                movableX = currMovable> 7 ? getSpacing() + 8 * getWidth() / numColumnDecks + dependency.getRelativeX()
                		: getSpacing() + currMovable * getWidth() / numColumnDecks + dependency.getRelativeX();
                
                
               if(currMovable == 8 || currMovable == 9 || currMovable == 10 || currMovable == 11) {
               	 movableY = getSpacing()  + (currMovable-8)*getHeight()/4 + dependency.getRelativeY();
               }
               else if(currMovable == 0) {
               	movableY = getSpacing() + SMALL_CARD_SPACING * solitaire.getDeck(currMovable).size() + SMALL_CARD_SPACING * depth
                        + dependency.getRelativeY();
               } else {
               	movableY = getSpacing() + getCardSpacing() * solitaire.getDeck(currMovable).size() + getCardSpacing() * depth
                        + dependency.getRelativeY();
               }

               
                g.drawImage(CardTextures.getTexture(solitaire.getMovablePile(currMovable).getCardAt(depth))
                        , movableX, movableY, cardWidth(), cardHeight(), this);
                g.drawRect(movableX, movableY, cardWidth(), cardHeight());
            }
            movablesX.set(currMovable, movableX);
            movablesY.set(currMovable, movableY);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintAreas(g);
        paintDecks(g);
    }

    @Override
    public void update(Observable observed, Object message) {
        repaint();
        System.out.println((getHeight() * 1000)/(getWidth() * 600));
        System.out.println(getWidth() + " " + getHeight());
        System.out.println(cardWidth() + " " + cardHeight());
        System.out.println(getCardSpacing());
    }
}
