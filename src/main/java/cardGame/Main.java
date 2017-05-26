package cardGame;

import cardGame.controller.SolitaireDragger;
import cardGame.game.Draw;

import cardGame.game.Solitaire;
import cardGame.model.Card;
import cardGame.view.DrawPanel;

import cardGame.controller.ButtonBar;
import cardGame.controller.CardDragger;
import cardGame.view.SolitairePanel;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;

/**
 * Runs the game. Although technically a controller this class can be found
 * more easily if it's not in that package
 */
public class Main {
    public static void main(String[] args) {
        Solitaire solitaire = new Solitaire();
        JFrame frame = new JFrame("Card game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SolitairePanel panel = new SolitairePanel(solitaire);
        SolitaireDragger sd = new SolitaireDragger(solitaire, panel);
        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo (null); // Center on screen.
        frame.setVisible(true);
    }
}
