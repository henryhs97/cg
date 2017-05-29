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

    private void setupGame() {
		decks.add(makeCompleteDeck());

    	 /* makes 7 decks on table */
		for(int i = 1; i < 8; i++) {
			decks.add(makeEmptyDeck());
			for(int j = 0; j < i; j++) {
				getDeck(i).addOnTop(getDeck(0).draw());
			}
		}

		for(int i = 8; i < 12; i++) {
			decks.add(makeEmptyDeck());
			//getDeck(i).addOnTop(getDeck(0).draw());
		}


        /* create them movable on top */
		for(int i = 0; i < 12; i++) {
			movables.add(createMovablePile(getDeck(i)));
		}
	}
    public Solitaire() {
    	setupGame();
    }

    public AbstractDeck getDeck(int deckNumber) {
        return decks.get(deckNumber);
    }

    public Movable getMovableCard(int deckNumber) {
        return movables.get(deckNumber);
    }

    public MovablePile getMovablePile(int deckNumber) { return movables.get(deckNumber); }

    public int getNumOfTotalDecks() { return decks.size(); }
    
    public int getNumOfColumns() { return decks.size()-3; }

    public void move(int from, int to, int index) {
    	if(from == 0 && to == 0 && !movables.get(0).isEmpty()) {
    		decks.get(0).addOnBottom(movables.get(0).removeCard());
    		movables.get(0).addOnTop(decks.get(0).draw());
    		setChanged();
	        notifyObservers();
    	}
    	
    	if(validMove(from, to, index)) 
    	{		
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
    	case 8: case 9: case 10: case 11: 
    		if(validMoveToBuildingPiles(movingTo, selectedCard)) {
    			System.out.println("valid move indeed");
    			if(didYouWin()) {
    				//do something
    			} else {
    				return true;
    			}  				
    		}
    	default: return validMoveToTableauDeck(movingTo, selectedCard);
    	}
    }

    public boolean validMoveToTableauDeck(Card movingTo, Card selectedCard) {   
    	if(movingTo != null && selectedCard != null) {
	    	int numberOfMovingTo = movingTo.getFace().ordinal();
	    	int numberOfSelectedCard = selectedCard.getFace().ordinal();
			if(numberOfMovingTo-1 == numberOfSelectedCard
					&& movingTo.getColour() != selectedCard.getColour()) { //only two card colors
				return true;
			}	
    	}
		if(movingTo == null && selectedCard.getFace() == Card.Face.KING) { //is it the first one? only king allowed			
			return true;
		}
		return false;
	}

    @Override
	public boolean validMoveToBuildingPiles(Card movingTo, Card selectedCard) {
		if(movingTo == null && selectedCard.getFace() == Card.Face.ACE) { //is it the first one? 
			return true;
		} else if(movingTo == null)
			return false;
	
		if(selectedCard.compareTo(movingTo) == 1 && movingTo.getSuit() == selectedCard.getSuit()) {
			return true;
		}	
		return false;
	}

	@Override
	public boolean didYouWin() {		
		for(int i = 8 ; i < 12 ; i++) {
			Card topCard = movables.get(i).getCard();
			if(topCard == null || topCard.getFace() != Card.Face.ACE) {
				return false;
			}
		}
		return true;	
	}

	public void reset()	{
    	decks.clear();
    	movables.clear();
		setupGame();

		setChanged();
		notifyObservers();
	}
}
