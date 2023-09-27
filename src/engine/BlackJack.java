package engine;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class BlackJack {
    Player joueur = new Player();
    Player dealer = new Player();
    public BlackJack() {
        Scanner scanner = new Scanner(System.in);
        final Deck deck = new Deck();

        while (true) {

            final int numCardToDeal = 4;

            System.out.println("Welcome to Blackjack!");

            // Deal the initial two cards to the player
            for (int i = 0; i < numCardToDeal; i++) {
                Optional<Card> optionalCard = deck.deal();
                if (optionalCard.isPresent() && i <= 1) {
                    Card card = optionalCard.get();
                    System.out.println("You were dealt: " + card);
                    joueur.addToHand(card); ;
                } else if (optionalCard.isPresent()) {
                    Card card = optionalCard.get();
                    System.out.println("the dealer was dealt: " + card);
                    dealer.addToHand(card);
                } else {
                    System.out.println("The deck is empty.");
                    return; // End the game if the deck is empty
                }
            }
            while (joueur.calculateHandValue() < 21 && dealer.calculateHandValue() < 21) {
                System.out.println("Do you want another card? [Y/N]");
                String inputLine = scanner.nextLine().trim();
                if (Objects.equals(inputLine, "N") || Objects.equals(inputLine, "n")) {
                    if (dealer.calculateHandValue() > joueur.calculateHandValue()) {
                        break;
                    } else {
                        System.out.println("Dealer play :");
                    }
                } else if (!Objects.equals(inputLine, "Y") && !Objects.equals(inputLine, "y")) {
                    System.out.println("Please enter 'Y' or 'N'.");
                } else {
                    Optional<Card> optionalPlayerCard = deck.deal();
                    if (optionalPlayerCard.isPresent()) {
                        Card card = optionalPlayerCard.get();
                        System.out.println("You were dealt: " + card);
                        joueur.addToHand(card);
                        System.out.println("Your current hand value: " + joueur.calculateHandValue());
                        if (joueur.calculateHandValue() > 21) {
                            break;
                        }
                    } else {
                        System.out.println("The deck is empty.");
                        break;
                    }
                }

                if (dealer.calculateHandValue() < joueur.calculateHandValue()) {
                    Optional<Card> optionalCard = deck.deal();
                    if (optionalCard.isPresent()) {
                        Card card = optionalCard.get();
                        System.out.println("the dealer was dealt: " + card);
                        dealer.addToHand(card);
                        System.out.println("Dealer current hand value: " + dealer.calculateHandValue());
                    } else {
                        System.out.println("The deck is empty.");
                        return; // End the game if the deck is empty
                    }
                } else {
                    System.out.println("dealer pass !");
                }
            }
            if ((joueur.calculateHandValue() > dealer.calculateHandValue() && joueur.calculateHandValue() <= 21) || dealer.calculateHandValue() > 21) {
                System.out.println("you win");
            }
            if (joueur.calculateHandValue() > 21) {
                System.out.println("Bust! Your hand value exceeds 21.");
            } else {
                System.out.println("Your final hand value: " + joueur.calculateHandValue());
            }
            if (joueur.calculateHandValue() < dealer.calculateHandValue() && dealer.calculateHandValue() < 21) {
                System.out.println("You forfeit ?!");
            }
            if (joueur.calculateHandValue() == dealer.calculateHandValue()) {
                System.out.println("Equality");
            }
            System.out.println("Do you want to play again? [Y/N]");
            String playAgainInput = scanner.nextLine().trim();

            if (!Objects.equals(playAgainInput, "Y") && !Objects.equals(playAgainInput, "y")) {
                break;
            }
        }

        System.out.println("Thanks for playing! Goodbye.");
        scanner.close();
    }
}
