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

	private boolean won = false;
    private List<AbstractDeck> decks = new ArrayList<>();
    private List<MovablePile> movables = new ArrayList<>();
    
    public Solitaire() {
    	setupGame();
    }

    /**
     * Returns a shuffled complete deck of cards, excluding jokers. 
     */
    private static AbstractDeck makeCompleteDeck() {
        AbstractDeck deck = new CompleteDeck();
        deck.shuffle();
        return deck;
    }
    
    /**
     * Returns an empty deck of cards.
     */
    private static AbstractDeck makeEmptyDeck() {
        AbstractDeck deck = new EmptyDeck();
        return deck;
    }

    /**
     * Returns a movable pile given a deck.
     */
    private MovablePile createMovablePile(AbstractDeck deck) {
        MovablePile movable;
        if(!deck.isEmpty()) {
            movable = new MovablePile(deck.draw());
            movable.addObserver(this);
        } else {
        	movable = new MovablePile();
            movable.addObserver(this);
        }   
        return movable;
    }

    /**
     * Sets up decks and movable piles for a solitaire game.
     */
    public void setupGame() {
		decks.add(makeCompleteDeck());
    	 /* makes 7 decks on table */
		for(int i = 1; i < 8; i++) {
			decks.add(makeEmptyDeck());
			for(int j = 0; j < i; j++) {
				getDeck(i).addOnTop(getDeck(0).draw());
			}
		}
		/* Makes the side decks */
		for(int i = 8; i < 12; i++) {
			decks.add(makeEmptyDeck());
		}
        /* Make them movable */
		for(int i = 0; i < 12; i++) {
			movables.add(createMovablePile(getDeck(i)));
		}
	}  
    
    /**
     * Moves a pile of cards starting at index to another pile.
     */
    public void move(int from, int to, int index) {
    	/* Special case for main deck */
    	if(won){
    		return;
		}
    	if(from == 0 && to == 0 && !movables.get(0).isEmpty()) {
    		getDeck(0).addOnBottom(movables.get(0).removeTopCard());
    		movables.get(0).addOnTop(getDeck(0).draw());
    	}  	else if(validMove(from, to, index)) {		
	        movables.get(to).addOnTop(movables.get(from).splitAt(index));
	        if(movables.get(from).size() == 0 && !decks.get(from).isEmpty()) {
	            movables.get(from).addOnTop(decks.get(from).draw());
	        }        
    	}
    	setChanged();
        notifyObservers();
    }
    
    /**
     * Returns true if it is a valid solitaire move
     */
    public boolean validMove(int from, int to, int index) {
    	Card movingTo = movables.get(to).getTopCard();
    	Card selectedCard = movables.get(from).getCardAt(index); 	  	
    	switch(to) {
    	case 0: 
    		return false; //to main deck
    	case 8: case 9: case 10: case 11: 
    		if(validMoveToSideDecks(movingTo, selectedCard)) {
    			if(didYouWin()) {
    				won = true;
    			} else {
    				return true;
    			}  				
    		}
    		return false;
    	default: return validMoveToTableauDeck(movingTo, selectedCard);
    	}
    }
    
    public boolean getWinState() {
    	return won;
    }
   
	/**
     * Returns true if it's a valid move to a tableau deck.
     * A tableau deck is one of the 7 table decks.
     */
    public boolean validMoveToTableauDeck(Card movingTo, Card selectedCard) {   
    	if(movingTo != null && selectedCard != null) {
	    	int numberOfMovingTo = movingTo.getFace().ordinal();
	    	int numberOfSelectedCard = selectedCard.getFace().ordinal();
			if(numberOfMovingTo-1 == numberOfSelectedCard && movingTo.getColour() != selectedCard.getColour()) {
				return true;
			}	
    	}
		if(movingTo == null && selectedCard.getFace() == Card.Face.KING) {
			return true;
		}
		return false;
	}

    /**
     * Returns true if it's a valid move to a side deck.
     */
	public boolean validMoveToSideDecks(Card movingTo, Card selectedCard) {
		if(movingTo == null && selectedCard.getFace() == Card.Face.ACE) { 
			return true;
		} else if(movingTo == null)
			return false;
		if(selectedCard.compareTo(movingTo) == 1 && movingTo.getSuit() == selectedCard.getSuit()) {
			return true;
		}	
		return false;
	}

    /**
     * Returns true if you won.
     */
	public boolean didYouWin() {		
		for(int i = 8 ; i < 12 ; i++) {
			Card topCard = movables.get(i).getTopCard();
			if(topCard == null || topCard.getFace() != Card.Face.ACE) {
				return false;
			}
		}
		return true;	
	}

    /**
     * Resets this solitaire game. Makes a new game.
     */
	public void reset()	{
		won = false;
    	decks.clear();
    	movables.clear();
		setupGame();

		setChanged();
		notifyObservers();
	}
	
	/**
     * Returns the deck at the given deck number.
     */
    public AbstractDeck getDeck(int deckNumber) {
        return decks.get(deckNumber);
    }

    /**
     * Returns the movable pile at deck number.
     */
    public MovablePile getMovablePile(int deckNumber) { 
    	return movables.get(deckNumber); 
    }

    /**
     * Returns total number of decks in decks list.
     */
    public int getNumOfTotalDecks() { 
    	return decks.size(); 
    }
    
    /**
     * Returns the number of columns there are for decks.
     * Due to the layout, there are 9 columns in a standard solitaire
     * game when the 4 side decks are placed in one column.
     */
    public int getNumOfColumns() { 
    	return decks.size()-3; 
    }

    @Override
    public void update(Observable observable, Object message) {
        setChanged();
        notifyObservers();
    }
}
