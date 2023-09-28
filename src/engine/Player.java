package engine;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void addToHand(Card card) {
        hand.add(card);
    }
    public void clearHand(){
        hand.clear();
    }
    public int calculateHandValue() {
        int value = 0;
        int numAces = 0;

        for (Card card : hand) {
            value += card.getRank().getRankValue();
            if (card.getRank() == Card.Rank.ACE) {
                numAces++;
            }
        }

        // Adjust for aces (if the player has aces and the total is over 21)
        while (numAces > 0 && value > 21) {
            value -= 10; // Treat an Ace as 1 instead of 11
            numAces--;
        }

        return value;
    }

}

