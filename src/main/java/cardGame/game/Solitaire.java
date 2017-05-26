package cardGame.game;

import cardGame.model.AbstractDeck;
import cardGame.model.Card;
import cardGame.model.CompleteDeck;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class Solitaire extends Observable implements Observer{

    private List<AbstractDeck> decks = new ArrayList<>();

    private List<MovablePile> movables = new ArrayList<>();


    private static AbstractDeck makeDeck() {
        AbstractDeck deck = new CompleteDeck();
        deck.shuffle();
        return deck;
    }

    /**
     *
     */
    private MovableCard createMovableCard(AbstractDeck deck) {
        MovableCard movable = null;
        if(!deck.isEmpty()) {
            movable = new MovableCard(deck.draw());
            movable.addObserver(this);
        }
        return movable;
    }

    private MovablePile createMovablePile(AbstractDeck deck) {
        MovablePile movable = null;
        if(!deck.isEmpty()) {
            movable = new MovablePile(deck.draw());
            movable.addObserver(this);
        }
        return movable;
    }

    public Solitaire() {
        for(int i = 0; i < 7; i++) {
            decks.add(makeDeck());
        }
        decks.get(0).addOnTop(Card.ACE_CLUBS);
        decks.get(1).addOnTop(Card.ACE_DIAMONDS);

        decks.get(2).addOnTop(Card.ACE_HEARTS);

        decks.get(3).addOnTop(Card.ACE_SPADES);

        decks.get(4).addOnTop(Card.TEN_CLUBS);

        decks.get(5).addOnTop(Card.TWO_CLUBS);
        decks.get(6).addOnTop(Card.THREE_CLUBS);
        for(int i = 0; i < 7; i++) {
            movables.add(createMovablePile(decks.get(i)));
        }

    }

    public AbstractDeck getDeck(int deckNumber) {
        return decks.get(deckNumber);
    }

    public Movable getMovableCard(int deckNumber) {
        return movables.get(deckNumber);
    }

    public MovablePile getMovablePile(int deckNumber) { return movables.get(deckNumber); }

    public int getNumOfDecks() { return decks.size(); }

    public void move(int from, int to, int index) {
        movables.get(to).addOnTop(movables.get(from).splitAt(index));
        if(movables.get(from).size() == 0 && !decks.get(from).isEmpty()){
            movables.get(from).addOnTop(decks.get(from).draw());
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void update(Observable observable, Object message) {
        setChanged();
        notifyObservers();
    }
}
