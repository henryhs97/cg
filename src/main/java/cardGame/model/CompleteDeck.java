package cardGame.model;

/**
 * A deck that has all possible cards
 */
public class CompleteDeck extends AbstractDeck {

    /**
     * Add all possible cards
     */
    protected void addCards() {
        addOnTop(Card.ACE_CLUBS);
    }
}
