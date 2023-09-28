import engine.BlackJack;
import gui.Table;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        BlackJack blackJack = new BlackJack();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BlackJack Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Table(blackJack.getDeck()));
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);
        });
    }
}
