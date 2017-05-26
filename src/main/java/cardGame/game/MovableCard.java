package cardGame.game;

import cardGame.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Represents a card that may be moved between piles
 */
public class MovableCard extends Movable {

    private Card card;

    public MovableCard(Card card){
        super();
        this.card = card;
    }

    public Card getCard(){
        return card;
    }
}
