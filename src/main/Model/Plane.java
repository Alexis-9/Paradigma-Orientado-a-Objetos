package main.Model;

import javax.swing.*;
import java.awt.*;

public class Plane extends Entity{
    private int maxEnergy;
    private int currentEnergy;

    public Plane(float startX, float startY, int width, int height) {
        setPosition(startX, startY);
        setSize(width, height);
        setDefaultValues();
    }

    /**
     * Initializes the default values of the plane.
     *
     * POST:
     * - maxEnergy is set to 100.
     * - currentEnergy is set to maxEnergy.
     * - speed is set to 240.
     * - The plane image is loaded from resources.
     */
    public void setDefaultValues() {
        this.maxEnergy = 100;
        this.currentEnergy = maxEnergy;
        setSpeed(350);

        setImage(new ImageIcon(getClass().getResource(Images.PLANE.getPath())).getImage());
        //hitboxWidth = getWidth() - 40;
        //hitboxHeight = getHeight() - 40;
        //hitboxOffsetX = 20;
        //hitboxOffsetY = 20;
    }

    /**
     * Moves the plane according to input direction and time.
     *
     * PRE:
     * - delta >= 0.
     *
     * POST:
     * - If dx != 0 and dy != 0, speed is reduced to 180.
     * - The position is updated by dx * speed * delta and dy * speed * delta.
     *
     * @param dx horizontal direction (-1, 0, 1)
     * @param dy vertical direction (-1, 0, 1)
     * @param delta time elapsed since last update
     */
    public void move(int dx, int dy, float delta){
        if (dx != 0 && dy != 0) { setSpeed(260); }

        moveBy(dx * getSpeed() * delta, dy * getSpeed() * delta);
    }

    /**
     * Restricts the plane position inside screen boundaries.
     *
     * PRE:
     * - screenWidth > 0
     * - screenHeight > 0
     * - topBound >= 0
     *
     * POST:
     * - The plane position is clamped so it remains inside valid screen bounds.
     * - The plane state is otherwise not modified.
     *
     * @param screenWidth width of the screen in pixels
     * @param screenHeight height of the screen in pixels
     * @param topBound minimum Y position allowed
     */
    public void clampToScreen(int screenWidth, int screenHeight, int topBound){
        double newX = getX();
        double newY = getY();

        if (newX < 0) newX = 0;
        if (newX + getWidth() > screenWidth) newX = screenWidth - getWidth();
        if (newY < topBound) newY = topBound;
        if (newY + getHeight() > screenHeight) newY = screenHeight - getHeight();

        setPosition(newX, newY);
    }

    /**
     * Updates the plane state each frame.
     *
     * PRE:
     * - delta >= 0
     *
     * POST:
     * - Speed is reset to 240.
     * - No other state is modified.
     *
     * @param delta time elapsed since last update
     */
    @Override
    public void update(float delta) {
        setSpeed(350);
    }

    /**
     * Draws the plane on screen.
     *
     * PRE:
     * - g != null
     *
     * POST:
     * - The plane image is rendered at its current position.
     * - The plane state is not modified.
     *
     * @param g graphics context used for rendering
     */
    @Override
    public void draw(Graphics g) {
        if (getImage() != null) {
            g.drawImage(getImage(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        }
    }

    /**
     * Reduces the plane's energy.
     *
     * PRE:
     * - amount >= 0
     *
     * POST:
     * - currentEnergy is decreased by amount.
     * - currentEnergy never becomes negative.
     *
     * @param amount energy to subtract
     */
    public void reduceEnergy(int amount){
        if (amount < 0){return;}
        currentEnergy -= amount;
        if (currentEnergy <= 0) {
            currentEnergy = 0;
        }

    }

    /**
     * Restores the plane's energy to maximum.
     *
     * POST:
     * - currentEnergy is set to maxEnergy.
     */
    public void restoreEnergy(){
        currentEnergy = maxEnergy;
    }


    /**
     * Getters.
     */
    public int getCurrentEnergy(){return currentEnergy;}
    public int getMaxEnergy(){return maxEnergy;}
}