package engine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card {
    private final Rank rank;
    private final Suit suit;

    private final static Map<String, Card> CARD_CACHE = initCache();

    private static Map<String,Card> initCache() {
        final Map<String, Card> cache = new HashMap<>();
        for(final Suit suit : Suit.values()){
            for(final Rank rank : Rank.values()){
                cache.put(cardKey(rank, suit), new Card(rank, suit));
            }
        }
        return Collections.unmodifiableMap(cache);
    }

    public static Card getCard(final Rank rank, final Suit suit){
        final Card card = CARD_CACHE.get(cardKey(rank, suit));
        if(card != null){
            return card;
        }
        throw new RuntimeException("Invalid card !" + rank + " " + suit);
    }
    private static String cardKey(final Rank rank, final Suit suit){
        return String.format(" %s OF %s", rank, suit);
    }
    private Card(final Rank rank, final Suit suit){
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString(){
        return String.format(" %s OF %s", this.rank, this.suit);
    }

    public Rank getRank() {
        return this.rank;
    }

    enum Suit{
        DIAMONDS(1),
        CLUBS(2),
        HEARTS(3),
        SPADES(4);

        private final int suitValue;
        Suit(final int suitValue){
            this.suitValue = suitValue;
        }
    }
    enum Rank{
        TWO(2),
        THREE(3),
        FOUR(4),
        FIvE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10),
        ACE(11);

        private final int rankValue;
        Rank(final int rankValue){
            this.rankValue = rankValue;
        }
        public int getRankValue(){return this.rankValue;}
    }
}
