package cardGame.game;

import cardGame.model.Card;

import java.util.ArrayList;
import java.util.List;

public class MovablePile extends Movable{

    List<Card> pile = new ArrayList<>();

    public MovablePile(Card card){
        super();
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

    public int size() {
        return pile.size();
    }

    public List<Card> splitAt(int index){
        List<Card> removedCards = new ArrayList<>();
        removedCards.addAll(pile.subList(pile.size() - (index + 1),pile.size()));
        this.pile.removeAll(removedCards);
        return removedCards;
    }

    public void addOnTop(List<Card> cards) {
        this.pile.addAll(cards);
    }
}
