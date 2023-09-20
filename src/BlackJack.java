import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class BlackJack {
    public BlackJack() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            final Deck deck = new Deck();
            final int numCardToDeal = 4;
            int playerHandValue = 0;
            int dealerHandValue = 0;

            System.out.println("Welcome to Blackjack!");

            // Deal the initial two cards to the player
            for (int i = 0; i < numCardToDeal; i++) {
                Optional<Card> optionalCard = deck.deal();
                if (optionalCard.isPresent() && i <= 1) {
                    Card card = optionalCard.get();
                    System.out.println("You were dealt: " + card);
                    playerHandValue += card.getRank().getRankValue();
                } else if (optionalCard.isPresent()) {
                    Card card = optionalCard.get();
                    System.out.println("the dealer was dealt: " + card);
                    dealerHandValue += card.getRank().getRankValue();
                } else {
                    System.out.println("The deck is empty.");
                    return; // End the game if the deck is empty
                }
            }
            while (playerHandValue < 21 && dealerHandValue < 21) {
                System.out.println("Do you want another card? [Y/N]");
                String inputLine = scanner.nextLine().trim();
                if (Objects.equals(inputLine, "N") || Objects.equals(inputLine, "n")) {
                    if (dealerHandValue > playerHandValue) {
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
                        playerHandValue += card.getRank().getRankValue();
                        System.out.println("Your current hand value: " + playerHandValue);
                        if (playerHandValue > 21) {
                            break;
                        }
                    } else {
                        System.out.println("The deck is empty.");
                        break;
                    }
                }

                if (dealerHandValue < playerHandValue) {
                    Optional<Card> optionalCard = deck.deal();
                    if (optionalCard.isPresent()) {
                        Card card = optionalCard.get();
                        System.out.println("the dealer was dealt: " + card);
                        dealerHandValue += card.getRank().getRankValue();
                        System.out.println("Dealer current hand value: " + dealerHandValue);
                    } else {
                        System.out.println("The deck is empty.");
                        return; // End the game if the deck is empty
                    }
                } else {
                    System.out.println("dealer pass !");
                }
            }
            if ((playerHandValue > dealerHandValue && playerHandValue <= 21) || dealerHandValue > 21) {
                System.out.println("you win");
            }
            if (playerHandValue > 21) {
                System.out.println("Bust! Your hand value exceeds 21.");
            } else {
                System.out.println("Your final hand value: " + playerHandValue);
            }
            if (playerHandValue < dealerHandValue && dealerHandValue < 21) {
                System.out.println("You forfeit ?!");
            }
            if (playerHandValue == dealerHandValue) {
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
