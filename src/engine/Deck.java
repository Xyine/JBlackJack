package engine;

import java.util.*;

public class Deck {

    private final Stack<Card> deckCards;
    public Deck(){
        this.deckCards = initDeck();
        Collections.shuffle(this.deckCards);
    }
    private Stack<Card> initDeck(){
        final Stack<Card> deckCards = new Stack<>();
        for(final Card.Suit suit : Card.Suit.values()) {
            for(final Card.Rank rank : Card.Rank.values()){
                deckCards.push(Card.getCard(rank, suit));
            }
        }
        return deckCards;
    }
    public Optional<Card> deal(){
        return this.deckCards.empty() ? Optional.empty() : Optional.of(this.deckCards.pop());
    }
}
