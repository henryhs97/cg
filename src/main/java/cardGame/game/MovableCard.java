package cardGame.game;

import cardGame.model.Card;

/**
 * Represents a card that may be moved between piles
 */
public class MovableCard extends Movable {

    private Card card;

    public MovableCard(Card card){
        super();
        this.card = card;
    }

    /**
     * Returns the movable card
     */
    public Card getCard(){
        return card;
    }
}
