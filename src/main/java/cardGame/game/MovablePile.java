package cardGame.game;

import cardGame.model.Card;

import java.util.ArrayList;
import java.util.List;

public class MovablePile extends Movable{

    List<Card> pile = new ArrayList<>();
    private int index;

    public MovablePile(){
        super();
    }

    public MovablePile(Card card){
        super();
        //if(card != null)
        	pile.add(card);
    }

    public Card getCard() {
        if(size() == 0)
            return null;
        return pile.get(pile.size() - 1);
    }

    public Card getCardAt(int index){
        if(index >= pile.size()) {
            return null;
        }
        return pile.get(index);
    }
    
    public Card removeCard(){
        if(index >= pile.size()) {
            return null;
        }
        return pile.remove(pile.size() - 1);
    }

    public int size() {
        return pile.size();
    }

    public List<Card> splitAt(int index){
        List<Card> removedCards = new ArrayList<>();
        if(pile.size()-index > 0) {
	        removedCards.addAll(pile.subList(index, pile.size()));
	        System.out.println("index split "+  index + " " + "size of split pile" + pile.size());
	        this.pile.removeAll(removedCards);
        }
        return removedCards;
    }

    public void addOnTop(Card card){
        this.pile.add(card);
    }

    public void addOnTop(List<Card> cards) {
    	if(cards.size()>0) {
    		this.pile.addAll(cards);
    	}
    }

    public void setIndex(int index){
        this.index = index;
    }
    
    public boolean isEmpty() {
    	return pile.isEmpty();
    }

    public int getIndex() {
        return index;
    }
}
