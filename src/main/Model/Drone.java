package main.Model;

import javax.swing.*;
import java.awt.*;


public class Drone extends Entity {
    private Direction direction;

    private int shootCounter;
    private int shootCooldown;

    public Drone(int startX, int startY, int width, int height, Direction direction, int shootCooldown){
        setPosition(startX, startY);
        setSize(width, height);

        this.direction = direction;
        this.shootCounter = (int) (Math.random() * shootCooldown);
        this.shootCooldown = shootCooldown;
    }

    /**
     * Initializes the drone's default values.
     *
     * PRE:
     * - The main.Images.DRONE resource must exist in the project's resources.
     *
     * POST:
     * - speed = 180.
     * - image references the image associated with main.Images.DRONE.
     *
     * @implSpec
     * Sets the base speed and assigns the drone's default sprite.
     */
    @Override
    public void setDefaultValues(){
        setSpeed(180);
        setImage(new ImageIcon(getClass().getResource(Images.DRONE.getPath())).getImage());
    }

    /**
     * Updates the entity's logic.
     *
     * PRE:
     * - delta > 0.
     *
     * POST:
     * - move(delta) is executed.
     * - shootCounter = previousShootCounter + 1.
     *
     * @param delta Time elapsed since the last update.
     */
    @Override
    public void update(float delta) {
        move(delta);
        shootCounter++;
    }

    /**
     * Draws the entity on the screen.
     *
     * PRE:
     * - The image resource must be available.
     * - g != null.
     *
     * POST:
     * - If getImage() != null, the image is rendered at the entity's
     *   current position using its current width and height.
     * - The entity state is not modified.
     *
     * @param g Graphics context used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        if (getImage() != null) {
            g.drawImage(getImage(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        }
    }

    /**
     * Moves the drone horizontally according to its current direction.
     *
     * PRE:
     * - delta >= 0.
     * - direction is not null.
     * - getSpeed() >= 0.
     *
     * POST:
     * - If direction == Direction.LEFT, the drone's X position is decreased
     *   by getSpeed() * delta.
     * - If direction == Direction.RIGHT, the drone's X position is increased
     *   by getSpeed() * delta.
     * - The Y position remains unchanged.
     *
     * @param delta Time elapsed since the last update.
     */
    public void move(float delta){
        if (direction == Direction.LEFT){
            moveBy(-getSpeed() * delta, 0);
        } else if (direction == Direction.RIGHT) {
            moveBy(getSpeed() * delta, 0);
        }
    }


    /**
     * Determines whether the drone is outside the visible screen bounds.
     *
     * PRE:
     * - screenWidth > 0.
     * - getWidth() >= 0.
     *
     * POST:
     * - Returns true if the drone is completely outside the screen bounds.
     * - Returns false otherwise.
     * - The state of the drone is not modified.
     *
     * @param screenWidth Width of the screen in pixels.
     * @return true if the drone is outside the screen; false otherwise.
     */
    public boolean outOfScreen(int screenWidth){
        return getX() < -getWidth() || getX() > screenWidth;
    }

    /**
     * Attempts to fire a missile if the cooldown has elapsed.
     *
     * PRE:
     * - missileSpeed > 0.
     * - explosionY >= 0.
     * - shootCooldown >= 0.
     *
     * POST:
     * - If shootCounter < shootCooldown:
     *   - Returns null.
     *   - shootCounter remains unchanged.
     *
     * - If shootCounter >= shootCooldown:
     *   - shootCounter is reset to 0.
     *   - Returns a new Missile instance.
     *   - The missile is created at the drone's current position.
     *
     * @param missileSpeed Speed of the missile.
     * @param explosionY Y-coordinate at which the missile explodes.
     * @return A new Missile if the drone can shoot; null otherwise.
     */
    public Missile tryShoot(double missileSpeed, int explosionY){
        if(shootCounter < shootCooldown){return null;}

        shootCounter = 0;

        return new Missile(
                getCenterX(),
                getY(),
                missileSpeed,
                explosionY
        );
    }
}