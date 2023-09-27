package gui;

import engine.Card;
import engine.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class Table extends JPanel {
    private static final int FRAME_WIDTH = 750;
    private static final int FRAME_HEIGHT = 800;
    private static final int TILE_WIDTH = 150;
    private static final int TILE_HEIGHT = 200;
    private static final int NUM_TILES = 20;
    private int TILE_ID = 0;
    private final int TILE_ID_OFFSET_MAX = 10;
    private final int TILE_ID_OFFSET_MIN = - 9;
    private int hasPass = 0;
    private int maxPass = 0;
    private final JLabel[] tiles; // Array to store tile labels

    Deck deck = new Deck();

    public Table() {

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
        JButton dealButton = new JButton("Deal");
        JButton passButton = new JButton("Pass");
        JTextArea gameLog = new JTextArea(10, 30);
        gameLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameLog);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(dealButton);
        buttonPanel.add(passButton);
        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        controlPanel.add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Add action listeners to the buttons
        dealButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dealCardAndUpdateTile();
            }
        });

        passButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(maxPass == 1){
                    System.exit(0);
                }else{
                    pass();
                }
            }
        });
    }

    private void pass(){
        maxPass++;
        if( TILE_ID < 10){
            TILE_ID = TILE_ID + TILE_ID_OFFSET_MAX;
            System.out.println("1 : " + TILE_ID);
            hasPass++ ;
        } else if(TILE_ID < 20){
            TILE_ID = TILE_ID + TILE_ID_OFFSET_MIN;
            System.out.println("2 : " + TILE_ID);
            hasPass--;
        }
    }
    private void dealCardAndUpdateTile() {
        maxPass = 0;
        Optional<Card> optionalCard = deck.deal();
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            String cardName = card.toString().trim(); // Get the card's name as a string
            String imagePath = "CARDS/" + cardName + ".jpg";
            if (TILE_ID < 10) {
                displayCardImage(imagePath, TILE_ID);
                TILE_ID = TILE_ID + TILE_ID_OFFSET_MAX + hasPass;
                System.out.println("3 : " + TILE_ID);
            } else if (TILE_ID < 20) {
                displayCardImage(imagePath, TILE_ID);
                TILE_ID = TILE_ID + TILE_ID_OFFSET_MIN - hasPass;
                System.out.println("4 : " + TILE_ID);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BlackJack Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Table());
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);
        });
    }
}
