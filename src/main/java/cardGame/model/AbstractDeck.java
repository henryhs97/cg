package cardGame.model;

import cardGame.util.Emptiable;
import cardGame.util.Sized;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Collection;

/**
 * An arbitrary deck of cards. Cards in a deck are usually closed.
 */
abstract public class AbstractDeck implements Emptiable, Sized {
    
    private static int seed = 2;
    /**
     * To allow slight variation in the way games play out, the seed used
     * is changed every time, but it is seeded to allow reproducible results
     */
    private static int nextSeed() {
        return seed++;
    }
    
    /**
     * For the purpose of digital shuffling the list-interface, or rather
     * the Collections function for swapping that requires the list-interface
     * is essential, as the algorithm used for it is also used in the
     * shuffle-method.
     */
    protected List<Card> cards;
    private Random random;
    
    /**
     * Returns the list of cards
     */
    public List<Card> getDeck() {
        return cards;
    }
    
    /**
     * Returns the list of cards from index 0 to idx, 
     * excluding idx. Also removes them from the original deck
     */
    public List<Card> splitDeckAt(int idx) {      	
    	List<Card> removedCards = new ArrayList<>(); 
    	removedCards.addAll(cards.subList(0, cards.size()));
    	this.cards.removeAll(removedCards);
        return removedCards;
    }
    
    
    /**
     * Create a new deck with no cards
     */
    public AbstractDeck() {
        cards = new ArrayList<>();
        random = new Random(nextSeed());
        addCards();
    }
    
    /**
     * reset the deck and put in the cards it has by default.
     */
    public void reset() {
        empty();
        addCards();
    }
    
    /**
     * Adds the cards this deck has by default
     */
    abstract protected void addCards();
    
    /**
     * Place a card back on the deck at the top of the deck
     */
    public void addOnTop(Card card) {
        if(card != null) {
            cards.add(card);
        }
    }
    
    /**
     * Place a card at the back of the deck. Due to the backing data structure
     * this is slower than putting the card on top.
     */
    public void addOnBottom(Card card) {
        if(card != null) {
        	cards.add(0, card);
        }
    }
    
    /**
     * Puts a card anywhere in the deck
     */
    public void addAnywhere(Card card) {
    	if(card != null) {
    		cards.add(random.nextInt(cards.size()), card);
    	}
    }
    
    /**
     * Add all cards in the discard pile back into the deck. The deck is 
     * shuffled afterwards to eliminate the possibility of 
     */
    public void addAll(Collection<Card> pile) {
        cards.addAll(pile);
    }
    
    /**
     * Shuffle the deck using a Knuth/Fisher-Yates shuffle. Every card is
     * swapped with an arbitrary card from the cards after it.
     */
    public void shuffle() {
        for(int index = 0; index < cards.size() - 1; ++index) {
            int swapIndex = index + random.nextInt(cards.size() - index);
            Collections.swap(cards, index, swapIndex);
        }
    }
    
    /**
     * Check the number of cards in this deck
     */
    @Override
    public int size() {
        return cards.size();
    }
    
    /**
     * Check if there are any cards left in this deck.
     */
    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }
    
    /**
     * Make this deck empty (e.g. make isEmpty() return true)
     */
    @Override
    public void empty() {
        cards.clear();
    }
    
    /**
     * Draw a card from the deck. This method will return null if the
     * deck is empty,
     */
    public Card draw() {
        if(!isEmpty())
            return cards.remove(cards.size() - 1);
        return null;
    }
    
    public Card getTopCard() {
        if(!isEmpty())
            return cards.get(cards.size() - 1);
        return null;
    }
    
    
    
}
