package gui;

import engine.BlackJack;
import engine.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Table extends JPanel {
    private static final int FRAME_WIDTH = 750;
    private static final int FRAME_HEIGHT = 800;
    private static final int TILE_WIDTH = 150;
    private static final int TILE_HEIGHT = 200;
    private static final int NUM_TILES = 20;
    private int TILE_ID = 0;
    private final int TILE_ID_OFFSET = 9;
    private JButton dealButton;
    private JButton passButton;
    private JTextArea gameLog;
    private JLabel[] tiles; // Array to store tile labels

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

        dealButton = new JButton("Deal");
        passButton = new JButton("Pass");

        gameLog = new JTextArea(10, 30);
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
                if(TILE_ID == 0){
                    displayCardImage("CARDS/ACE OF CLUBS.jpg", TILE_ID,110,140); // Display the card image in the first tile
                    TILE_ID++;
                } else if(TILE_ID < 11) {
                TILE_ID = TILE_ID + TILE_ID_OFFSET;
                    displayCardImage("CARDS/ACE OF CLUBS.jpg", TILE_ID,110,140); // Display the card image in the first tile
                    TILE_ID++;
                }else if(TILE_ID < 20){
                    TILE_ID = TILE_ID - 10;
                    displayCardImage("CARDS/ACE OF CLUBS.jpg", TILE_ID,110,140); // Display the card image in the first tile
                    TILE_ID++;
                }
            }
        });

        passButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the action when the "Pass" button is clicked
            }
        });
    }

    private void displayCardImage(String imagePath, int tileIndex, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Scale the image
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
