package cardGame.game;

import cardGame.model.Card;

public interface SolitaireRules {

	public boolean validMovetoTableauDeck(Card top, Card current);

	public boolean validMovetoBuildingPiles(Card top, Card current);
	
	public boolean didYouWin();
}
