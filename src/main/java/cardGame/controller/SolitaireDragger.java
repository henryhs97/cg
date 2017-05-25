package cardGame.controller;

import cardGame.game.Solitaire;
import cardGame.view.SolitairePanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by sibdoo on 25/05/2017.
 */
public class SolitaireDragger extends MouseInputAdapter {

    private Solitaire solitaire;
    private SolitairePanel panel;

    private int selected;
    private int startX;
    private int startY;

    /**
     * Create a new card dragger that receives mouse events from the DrawPanel
     * supplied to this constructor
     */
    public SolitaireDragger(Solitaire solitaire, SolitairePanel panel) {
        this.solitaire = solitaire;
        this.panel = panel;
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        selected = -1;
    }

    /**
     * If the mouse button is pressed in the area where the top card is
     * drawn (obviously a lack of drawable cards makes this impossible)
     * that card is 'selected' so it can be dragged.
     */
    @Override
    public void mousePressed(MouseEvent event) {
        if(solitaire.getMovableCard(0) != null) {
            if(     event.getX() > panel.getMovableX(0) &&
                    event.getX() < panel.getMovableX(0) + panel.cardWidth() &&
                    event.getY() > panel.getMovableY(0) &&
                    event.getY() < panel.getMovableY(0) + panel.cardHeight()
                    ) {
                selected = 0;
                startX = event.getX();
                startY = event.getY();
            }
        }
        if(solitaire.getMovableCard(1) != null) {
            if(     event.getX() > panel.getMovableX(1) &&
                    event.getX() < panel.getMovableX(1) + panel.cardWidth() &&
                    event.getY() > panel.getMovableY(1) &&
                    event.getY() < panel.getMovableY(1) + panel.cardHeight()
                    ) {
                selected = 1;
                startX = event.getX();
                startY = event.getY();
            }
        }
        if(solitaire.getMovableCard(2) != null) {
            if(     event.getX() > panel.getMovableX(2) &&
                    event.getX() < panel.getMovableX(2) + panel.cardWidth() &&
                    event.getY() > panel.getMovableY(2) &&
                    event.getY() < panel.getMovableY(2) + panel.cardHeight()
                    ) {
                selected = 2;
                startX = event.getX();
                startY = event.getY();
            }
        }
    }

    /**
     * When the top card is released with the mouse in the discard square,
     * the card is moved.
     *
     * TODO: detect action
     * TODO: fire action
     */
    @Override
    public void mouseReleased(MouseEvent event) {
        if(selected == 0) {
            if (panel.inArea(event.getPoint()) == 1){
                solitaire.move(selected, 1);
            }else if(panel.inArea(event.getPoint()) == 2){
                solitaire.move(selected, 2);
            }
            else {
                solitaire.getMovableCard(0).setRelativeX(0);
                solitaire.getMovableCard(0).setRelativeY(0);
            }
        }
        if(selected == 1) {
            if(panel.inArea(event.getPoint()) == 0) {
                solitaire.move(selected, 0);
            }else if(panel.inArea(event.getPoint()) == 2){
                solitaire.move(selected, 2);
            }
            else {
                solitaire.getMovableCard(1).setRelativeX(1);
                solitaire.getMovableCard(1).setRelativeY(1);
            }
        }
        if(selected == 2) {
            if(panel.inArea(event.getPoint()) == 0) {
                solitaire.move(selected, 0);
            }else if(panel.inArea(event.getPoint()) == 1){
                solitaire.move(selected, 1);
            }
            else {
                solitaire.getMovableCard(2).setRelativeX(2);
                solitaire.getMovableCard(2).setRelativeY(2);
            }
        }
        selected = -1;
    }

    /**
     * If a card is selected it is moved relative to the positions the mouse
     * was first pressed.
     */
    @Override
    public void mouseDragged(MouseEvent event) {
        if(selected == 0) {
            solitaire.getMovableCard(0).setRelativeX(event.getX() - startX);
            solitaire.getMovableCard(0).setRelativeY(event.getY() - startY);
        }
        if(selected == 1) {
            solitaire.getMovableCard(1).setRelativeX(event.getX() - startX);
            solitaire.getMovableCard(1).setRelativeY(event.getY() - startY);
        }
        if(selected == 2) {
            solitaire.getMovableCard(2).setRelativeX(event.getX() - startX);
            solitaire.getMovableCard(2).setRelativeY(event.getY() - startY);
        }
    }

}
