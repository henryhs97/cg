package cardGame.game;

import cardGame.model.Card;

public interface SolitaireRules {

	public boolean validMoveToTableauDeck(Card top, Card current);

	public boolean validMoveToSideDecks(Card top, Card current);
	
	public boolean didYouWin();
	
}
