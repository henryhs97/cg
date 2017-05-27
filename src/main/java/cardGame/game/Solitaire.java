package cardGame.game;

import cardGame.model.AbstractDeck;
import cardGame.model.Card;
import cardGame.model.CompleteDeck;
import cardGame.model.EmptyDeck;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class Solitaire extends Observable implements Observer, SolitaireRules{

    private List<AbstractDeck> decks = new ArrayList<>();
    private List<MovablePile> movables = new ArrayList<>();


    private static AbstractDeck makeCompleteDeck() {
        AbstractDeck deck = new CompleteDeck();
        deck.shuffle();
        return deck;
    }
    
    private static AbstractDeck makeEmptyDeck() {
        AbstractDeck deck = new EmptyDeck();
        return deck;
    }

    private MovablePile createMovablePile(AbstractDeck deck) {
        MovablePile movable = null;
        if(!deck.isEmpty()) {
            movable = new MovablePile(deck.draw());
            movable.addObserver(this);
        }
        return movable;
    }
    
    private MovablePile createMainMovablePile(AbstractDeck deck) {
        MovablePile movable = null;
        if(!deck.isEmpty()) {
            movable = new MovablePile(deck.getDeck());
            movable.addObserver(this);
        }
        return movable;
    }

    public Solitaire() {
    	 decks.add(makeCompleteDeck());    	
    	 
    	 /* makes 7 decks on table */
        for(int i = 1; i <= 7; i++) {     
        	decks.add(makeEmptyDeck());  
        	for(int j = 0; j < i; j++) {
        		getDeck(i).addOnTop(getDeck(0).draw());
        	}
        }
        

        /* create them movable on top */
        for(int i = 0; i <= 7; i++) {
            movables.add(createMovablePile(getDeck(i)));
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
    	if(validMove(from, to, index)) {
	        movables.get(to).addOnTop(movables.get(from).splitAt(index));
	        if(movables.get(from).size() == 0 && !decks.get(from).isEmpty()){
	            movables.get(from).addOnTop(decks.get(from).draw());
	        }
	        setChanged();
	        notifyObservers();
    	}
    }

    @Override
    public void update(Observable observable, Object message) {
        setChanged();
        notifyObservers();
    }
    
    public boolean validMove(int from, int to, int index) {
    	Card movingTo = movables.get(to).getCard();
    	Card selectedCard = movables.get(from).getCardAt(index);
    	switch(to) {
    	case 0: 
    		return false; //to main deck
    	case 8: 
    	case 9:
    	case 10:
    	case 11: 
    		return validMovetoBuildingPiles(movingTo, selectedCard);
    	default: return validMovetoTableauDeck(movingTo, selectedCard);
    	}
    }

    public boolean validMovetoTableauDeck(Card movingTo, Card selectedCard) {   	
    	int numberOfMovingTo = movingTo.getFace().ordinal();
    	int numberOfSelectedCard = selectedCard.getFace().ordinal();
		if(movingTo != null && numberOfMovingTo-1 == numberOfSelectedCard
				&& movingTo.getColour() != selectedCard.getColour()) { //only two card colors
			return true;
		}		
		return false;
	}

	@Override
	public boolean validMovetoBuildingPiles(Card movingTo, Card selectedCard) {
		int numberOfMovingTo = movingTo.getFace().ordinal();
    	int numberOfSelectedCard = selectedCard.getFace().ordinal();
    	
		if(movingTo == null && numberOfSelectedCard == 0) //is it the first one?
			return true;
		
		if(numberOfMovingTo-1 == numberOfSelectedCard && movingTo.getSuit() == selectedCard.getSuit())
			return true;
			
		return false;
	}

	@Override
	public boolean didYouWin() {
		// TODO Auto-generated method stub
		return false;
	}


}
