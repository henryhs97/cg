package cardGame;

import java.awt.Dimension;

import javax.swing.JFrame;

import cardGame.controller.ButtonBar;
import cardGame.controller.SolitaireDragger;
import cardGame.game.Solitaire;
import cardGame.view.SolitairePanel;


/**
 * Runs the game.
 */
public class Main {
    public static void main(String[] args) {
        Solitaire solitaire = new Solitaire();
        JFrame frame = new JFrame("Card game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(new ButtonBar(solitaire));
        SolitairePanel panel = new SolitairePanel(solitaire);
        @SuppressWarnings("unused")
		SolitaireDragger sd = new SolitaireDragger(solitaire, panel);
        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setMinimumSize(new Dimension(166,150));
        frame.pack();
        frame.setLocationRelativeTo (null); // Center on screen.
        frame.setVisible(true);
    }
}
