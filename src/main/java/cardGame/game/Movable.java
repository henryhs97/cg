package cardGame.game;

import java.util.Observable;

/**
 * Abstract class for a movable object.
 */
public abstract class Movable extends Observable{

    private int relativeX;
    private int relativeY;

    
    public Movable() {
        relativeX = 0;
        relativeY = 0;
    }

    /**
     * Change the number of pixels this object has been moved
     */
    public void setRelativeX(int relativeX) {
        this.relativeX = relativeX;
        setChanged();
        notifyObservers();
    }

    /**
     * Change the number of pixels this object has been moved
     */
    public void setRelativeY(int relativeY) {
        this.relativeY = relativeY;
        setChanged();
        notifyObservers();
    }

    /**
     * Get the number of pixels in X this object has been moved
     */
    public int getRelativeX() {
        return relativeX;
    }

    /**
     * Get the number of pixels in Y this object has been moved
     */
    public int getRelativeY() {
        return relativeY;
    }

}
