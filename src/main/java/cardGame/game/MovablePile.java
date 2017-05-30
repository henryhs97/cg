package cardGame.game;

import cardGame.model.Card;
import cardGame.util.Emptiable;
import cardGame.util.Sized;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pile of cards (deck) which may be moved.
 */
public class MovablePile extends Movable implements Sized, Emptiable{

    List<Card> pile = new ArrayList<>();
    private int index;

    public MovablePile(){
        super();
    }
    
    public MovablePile(Card card){
        super();
        pile.add(card);
    }

    /**
     * Returns the top card of this pile,
     * if it is empty, returns null.
     */
    public Card getTopCard() {
        if(size() == 0) {
            return null;
        }
        return pile.get(size() - 1);
    }

    /**
     * Returns the card at given index of this pile,
     * if out of bounds, returns null.
     */
    public Card getCardAt(int index){
        if(index >= size() || index < 0) {
            return null;
        }
        return pile.get(index);
    }
    
    /**
     * Removes the top card of this pile, if out of bounds, 
     * returns null. Returns the removed card
     */
    public Card removeTopCard(){
        if(index >= size() || index < 0) {
            return null;
        }
        return pile.remove(pile.size() - 1);
    }

    /**
     * Return the size of this pile
     */
    public int size() {
        return pile.size();
    }

    /**
     * Splits this deck at given index:
     * Removes the cards from this pile, and returns them in
     * a List of cards. If index out of bounds, returns null List.
     */
    public List<Card> splitAt(int index){
        List<Card> removedCards = new ArrayList<>();
        if(size()-index > 0) {
	        removedCards.addAll(pile.subList(index, pile.size()));
	        this.pile.removeAll(removedCards);
        }
        return removedCards;
    }

    /**
     * Adds given card on top of pile. 
     */
    public void addOnTop(Card card){
        pile.add(card);
    }

    /**
     * Adds given List of cards to this pile
     */
    public void addOnTop(List<Card> cards) {
    	if(cards.size() > 0) {
    		pile.addAll(cards);
    	}
    }

    /**
     * Setter for index of this pile of where to split
     */
    public void setIndex(int index){
        this.index = index;
    }
    
    /**
     * Returns true if this pile is empty.
     */
    public boolean isEmpty() { return pile.isEmpty(); }

    /**
     * Getter for the current index this pile has, used to split.
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * Empties this pile.
     */
    public void empty() {
        pile.clear();
    }
    
}
