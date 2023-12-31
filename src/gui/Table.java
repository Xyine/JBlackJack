package gui;

import engine.Card;
import engine.Deck;
import engine.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class Table extends JPanel {
    private static final int FRAME_WIDTH = 750;
    private static final int FRAME_HEIGHT = 800;
    private static final int TILE_WIDTH = 150;
    private static final int TILE_HEIGHT = 200;
    private static final int NUM_TILES = 20;
    private int TILE_DEALER_ID = 0;
    private int TILE_PLAYER_ID = 10;
    private int maxPass = 0;
    private boolean turn = true;
    private boolean winMessage = false;
    private final Player dealer = new Player();
    private final Player joueur = new Player();
    private final JLabel[] tiles; // Array to store tile labels
    private final Deck deck;

    public Table(Deck deck) {
        this.deck = deck;

        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setLayout(new BorderLayout());
        JPanel tilePanel = new JPanel();
        tilePanel.setLayout(new GridLayout(4, 5));
        tiles = new JLabel[NUM_TILES]; // Initialize the array for tiles

        // Create 20 tiles
        for (int i = 0; i < NUM_TILES; i++) {
            tiles[i] = createTile(i);
            tilePanel.add(tiles[i]);
        }
        add(tilePanel, BorderLayout.CENTER);
        // Create a control panel for buttons and game log
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        JButton dealToDealer = new JButton("Deal to dealer");
        JButton passButton = new JButton("Pass");
        JButton dealToPlayer = new JButton(("Deal to player"));
        JTextArea gameLog = new JTextArea(10, 30);
        gameLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameLog);
        JPanel buttonPanel = new JPanel();
        JButton newGameButton = new JButton("New Game");
        buttonPanel.add(newGameButton);
        buttonPanel.add(dealToDealer);
        buttonPanel.add(dealToPlayer);
        buttonPanel.add(passButton);
        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        controlPanel.add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        newGameButton.addActionListener(e -> {
            resetGame();
            gameLog.setText(""); // Clear the game log
            gameLog.append(HandValue(dealer));
            gameLog.append(HandValue(joueur));
        });

        // Add action listeners to the buttons
        dealToDealer.addActionListener(e -> {
            if ( dealer.calculateHandValue() < 21 && joueur.calculateHandValue() < 21 && dealer.calculateHandValue() > 2 && joueur.calculateHandValue() > 2) {
                if (turn) {
                    dealCard(0, dealer);
                    if (calculateWin(dealer)) {
                        if(!winMessage) {
                            gameLog.append("Dealer Win !!! \n");
                            winMessageSent();
                        }
                    }
                    gameLog.append(HandValue(dealer));
                } else {
                    gameLog.append(" it's the player turn\n");
                }
            }
        });

        dealToPlayer.addActionListener(e -> {
            if (dealer.calculateHandValue() < 21 && joueur.calculateHandValue() < 21 && dealer.calculateHandValue() > 2 && joueur.calculateHandValue() > 2) {
                if (!turn) {
                    dealCard(10, joueur);
                    if (calculateWin(joueur)) {
                        if(!winMessage) {
                            gameLog.append("Joueur Win !!!\n");
                            winMessageSent();
                        }
                    }
                    gameLog.append(HandValue(joueur));
                } else {
                    gameLog.append("it's the dealer turn\n");
                }
            }
        });
        passButton.addActionListener(e -> {
            if(dealer.calculateHandValue() > 2 && joueur.calculateHandValue() > 2) {
                gameLog.append(calculateLose());
                if (maxPass == 1) {
                    gameLog.append(ff());
                    gameLog.append("You forfeit ?!\n");
                } else {
                    pass();
                }
            }
        });
    }
    private void dealInitialCards() {
        for (int i = 0; i < 2; i++) {
            dealCard(0, dealer);
            dealCard(10, joueur);
        }
    }
    private void winMessageSent(){
        winMessage = true;
    }
    private void resetGame() {
        // Clear the player's and dealer's hands
        dealer.clearHand();
        joueur.clearHand();

        // Clear the displayed card images on the tiles
        for (int i = 0; i < NUM_TILES; i++) {
            tiles[i].setIcon(null);
        }

        // Reset other game-related variables
        TILE_DEALER_ID = 0;
        TILE_PLAYER_ID = 10;
        maxPass = 0;
        turn = true;

        // Start a new game by dealing the initial cards
        dealInitialCards();
    }


    private Boolean calculateWin(Player player){
        if( player.calculateHandValue() == 21 ){
            return true;
        } else {
            return false;
        }
    }
    private String HandValue(Player player){
        return name(player) + "hand value is " + player.calculateHandValue() + "\n";
    }
    public String name(Player player){
        if(player == joueur){
            return "Joueur ";
        } else if(player == dealer){
            return "Dealer ";
        }
        return "";
    }
    private String calculateLose(){
        if(joueur.calculateHandValue() > 21){
            return "Joueur lose :( \n";
        } else if(dealer.calculateHandValue() > 21){
            return "Dealer lose :(\n";
        }
        return "";
    }

    private String ff(){
        if (joueur.calculateHandValue() > dealer.calculateHandValue()){
            return "Joueur win !!!\n";
        } else if (dealer.calculateHandValue() > joueur.calculateHandValue()){
            return "dealer win !!!\n";
        }
        return "";
    }
    private void pass(){
        maxPass++;
        turn = !turn;
    }
    private void dealCard(int OFFSET, Player player) {
        maxPass = 0;
        Optional<Card> optionalCard = deck.deal();
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            String cardName = card.toString().trim(); // Get the card's name as a string
            String imagePath = "CARDS/" + cardName + ".jpg";
            if (OFFSET == 0 && TILE_DEALER_ID < 10) {
                    displayCardImage(imagePath, TILE_DEALER_ID);
                    player.addToHand(card);
                    TILE_DEALER_ID++;
                    turn = false;
            } else if (OFFSET == 10 && TILE_PLAYER_ID < 20) {
                displayCardImage(imagePath, TILE_PLAYER_ID);
                player.addToHand(card);
                TILE_PLAYER_ID++;
                turn = true;
            }
        }
    }
    private void displayCardImage(String imagePath, int tileIndex) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(110, 140, Image.SCALE_SMOOTH); // Scale the image
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        tiles[tileIndex].setIcon(scaledIcon); // Set the scaled image for the specified tile
    }


    private JLabel createTile(int tile_id) {
        JLabel tile = new JLabel();
        tile.setPreferredSize(new Dimension(TILE_WIDTH, TILE_HEIGHT));
        tile.setOpaque(true); // Set the label to be opaque
        if (tile_id < 10) {
            tile.setBackground(Color.RED);
        }else{
            tile.setBackground(Color.BLUE);
        }
        tile.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Add a border for visibility
        return tile;
    }
}
