package cardGame.view;

import cardGame.game.Movable;
import cardGame.game.MovablePile;
import cardGame.game.Solitaire;
import cardGame.model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by sibdoo on 22/05/2017.
 */
public class SolitairePanel extends JPanel implements Observer {


    private static final int CARD_SPACING = 20; //pixels
    private static final int Y_OFFSET = Card.values().length * CARD_SPACING;

    private Solitaire solitaire;

    private List<Integer> movablesX = new ArrayList<>();

    private List<Integer> movablesY = new ArrayList<>();

    public int getMovableX (int card){ return movablesX.get(card); }

    public int getMovableY (int card){ return movablesY.get(card); }

    public SolitairePanel(Solitaire solitaire){
        this.solitaire = solitaire;
        solitaire.addObserver(this);
        setBackground(new Color(116, 199, 203));
        setVisible(true);
        setOpaque(true);
        for(int i = 0; i < solitaire.getNumOfDecks(); i++){
            movablesX.add(0);
            movablesY.add(0);
        }
    }

    public int inArea(Point point) {
        return (int) point.getX() / (getWidth() / solitaire.getNumOfDecks());
    }

    private void paintAreas(Graphics g) {
        int numDecks = solitaire.getNumOfDecks();
        g.setColor(Color.YELLOW);
        for(int i = 0; i < numDecks; i++) {
            g.drawRect(i * getWidth() / numDecks, 0, getWidth() / numDecks, getHeight() - 1);
        }
        g.setColor(Color.BLACK);
    }

    private int getSpacing() {
        return (int) ((getHeight() * 20) / 600.0);
    }

    public int getCardSpacing() { return CARD_SPACING; }

    public int cardWidth() {
        if((getHeight() * 600.0) / (getWidth() * 436.0) <= 1.0)
            return (int) ((cardHeight() * 436.0) / 600.0);
        return (getWidth() - getSpacing() * 3 - 2 * Card.values().length) / 8;
    }

    public int cardHeight() {
        if((getHeight() * 600.0) / (getWidth() * 436.0) > 1.0)
            return (int) ((cardWidth() * 600.0) / 436.0);
        return (getHeight() - getSpacing() * 2 - 2 * Card.values().length) / 4;
    }

    private void paintDecks(Graphics g) {
        int depth;
        int currMovable = -1;
        int numDecks = solitaire.getNumOfDecks();
        Integer movableX = 0;
        Integer movableY = 0;
        for(int deckNum = 0; deckNum < numDecks; deckNum++) {
            for (depth = 0; depth < solitaire.getDeck(deckNum).size(); ++depth) {
                int posX = getSpacing()  + deckNum * getWidth()/numDecks;
                int posY = getSpacing()  + CARD_SPACING * depth;
                g.drawImage(CardBackTextures.getTexture(CardBack.CARD_BACK_BLUE)
                        , posX, posY, cardWidth(), cardHeight(), this);
                g.drawRect(posX, posY, cardWidth(), cardHeight());
            }
        }

        for(int deckNum = 0; deckNum < numDecks; deckNum++) {
            MovablePile dependency = solitaire.getMovablePile(deckNum);
            if(dependency != null && (dependency.getRelativeX() != 0 || dependency.getRelativeY() != 0)) {
                currMovable = deckNum;
            }else if (dependency != null) {
                for (depth = 0; depth < solitaire.getMovablePile(deckNum).size(); ++depth) {
                    movableX = getSpacing() + deckNum * getWidth() / numDecks;
                    movableY = getSpacing() + CARD_SPACING * solitaire.getDeck(deckNum).size() + CARD_SPACING * depth;
                    g.drawImage(CardTextures.getTexture(solitaire.getMovablePile(deckNum).getCardAt(depth))
                            , movableX, movableY, cardWidth(), cardHeight(), this);
                    g.drawRect(movableX, movableY, cardWidth(), cardHeight());
                }
                movablesX.set(deckNum, movableX);
                movablesY.set(deckNum, movableY);
            }
        }
        if(currMovable != -1) {
            MovablePile dependency = solitaire.getMovablePile(currMovable);
            int index = solitaire.getMovablePile(currMovable).getIndex();
            for (depth = 0; depth < index; ++depth) {
                movableX = getSpacing() + currMovable * getWidth() / numDecks;
                movableY = getSpacing() + CARD_SPACING * solitaire.getDeck(currMovable).size() + CARD_SPACING * depth;
                g.drawImage(CardTextures.getTexture(solitaire.getMovablePile(currMovable).getCardAt(depth))
                        , movableX, movableY, cardWidth(), cardHeight(), this);
                g.drawRect(movableX, movableY, cardWidth(), cardHeight());
            }
            for (depth = index; depth < solitaire.getMovablePile(currMovable).size(); ++depth) {
                movableX = getSpacing() + currMovable * getWidth() / numDecks + dependency.getRelativeX();
                movableY = getSpacing() + CARD_SPACING * solitaire.getDeck(currMovable).size() + CARD_SPACING * depth
                        + dependency.getRelativeY();
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
    }
}
