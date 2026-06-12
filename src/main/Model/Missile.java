package main.Model;

import javax.swing.*;
import java.awt.*;

public class Missile extends Entity {

    private int explosionY;

    private Explosion explosion;

    private boolean exploding;
    private boolean finished;
    private boolean damageApplied;

    public Missile(double startX, double startY, double speed, int explosionY) {
        setPosition(startX, startY);
        setSize(16, 32);
        setSpeed(speed);

        this.explosionY = explosionY;

        exploding = false;
        finished = false;
        damageApplied = false;

        setDefaultValues();
    }

    /**
     * Updates the missile state each frame.
     *
     * PRE:
     * - delta >= 0.
     *
     * POST:
     * - If not exploding, the missile moves according to its speed.
     * - If the explosion condition is met, the missile enters exploding state and explosion is created.
     * - If an explosion exists, it is updated.
     * - If the explosion finishes, the missile is marked as finished.
     *
     * @param delta Time elapsed since the last update.
     */
    @Override
    public void update(float delta) {
        if (!exploding){
            move(delta);

            if (shouldExplode()){
                exploding = true;
                explode();
            }
        }

        if (explosion != null){
            explosion.update(delta);
            if (explosion.finished()){
                finished = true;
            }
        }
    }

    /**
     * Draws the missile or its explosion.
     *
     * PRE:
     * - g != null.
     *
     * POST:
     * - If not exploding, the missile image is rendered.
     * - If explosion exists, it is rendered.
     * - The internal state of the missile is not modified.
     *
     * @param g Graphics context used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        if (!exploding && getImage() != null){
            g.drawImage(getImage(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        }
        if (explosion != null){
            explosion.draw(g);
        }
    }

    /**
     * Initializes the default visual representation of the missile.
     *
     * POST:
     * - The missile image is loaded from resources.
     * - The internal state is not logically modified beyond initialization.
     */
    @Override
    public void setDefaultValues() {
        setImage(new ImageIcon(getClass().getResource(Images.MISSILE.getPath())).getImage());
    }

    /**
     * Moves the missile vertically based on its speed.
     *
     * PRE:
     * - delta >= 0.
     *
     * POST:
     * - The Y position increases by getSpeed() * delta.
     * - The X position remains unchanged.
     *
     * @param delta Time elapsed since the last update.
     */
    public void move(float delta){
        moveBy(0, getSpeed() * delta);
    }

    /**
     * Creates an explosion at the missile's current position.
     *
     * POST:
     * - An Explosion object is created at the missile position.
     *
     * The missile state is not directly modified beyond setting the explosion reference.
     */
    public void explode(){
        explosion = new Explosion(getCenterX(), getCenterY());
    }

    /**
     * Determines whether the missile should explode based on its position.
     *
     * POST:
     * - Returns true if getY() >= explosionY.
     * - Returns false otherwise.
     * - The missile state is not modified.
     *
     * @return true if the missile should explode; false otherwise.
     */
    public boolean shouldExplode(){
        return getY() >= explosionY;
    }

    /**
     * Triggers the missile explosion if it has not exploded yet.
     *
     * POST:
     * - If exploding is false:
     *   - exploding is set to true.
     *   - explode() is invoked.
     * - If exploding is true:
     *   - No state is modified.
     *
     * - After execution, exploding is true.
     */
    public void triggerExplosion(){
        if(!exploding){
            exploding = true;
            explode();
        }
    }

    /**
     * Getters.
     */
    public boolean isExploding(){ return exploding; }
    public boolean isFinished(){ return finished; }
    public boolean isDamageApplied(){ return damageApplied; }
    public Explosion getExplosion(){ return explosion; }

    /**
     * Setter.
     */
    public void setDamageApplied(boolean damageApplied){ this.damageApplied = damageApplied; }

    /**
     * Checks collision between this missile and another collidable object.
     *
     * PRE:
     * - other != null.
     *
     * POST:
     * - Returns true if bounding boxes intersect.
     * - Returns false otherwise.
     * - The state of the missile is not modified.
     *
     * @param other another collidable object.
     * @return true if a collision is detected; false otherwise.
     */
    public boolean collidesWith(Collidable other){
        return getBounds().intersects(other.getBounds());
    }
}