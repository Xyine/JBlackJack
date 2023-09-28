package engine;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class BlackJack {
    Player joueur = new Player();
    Player dealer = new Player();
    private final Deck deck;

    public Deck getDeck(){
        return deck;
    }

    public BlackJack() {
        Scanner scanner = new Scanner(System.in);
        deck = new Deck();

    }
}
