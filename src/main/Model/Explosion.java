package main.Model;

import javax.swing.*;
import java.awt.*;

public class Explosion extends Entity {

    private double x;
    private double y;
    private double size;
    private int radius;
    private int duration;
    private int currentFrame;

    public Explosion (double x, double y, double size){
        this.x = x;
        this.y = y;
        this.size = size;
        radius = (int) size;

        setDefaultValues();
    }

    /**
     * Initializes the default duration and current frame of the Explosion for drawing.
     *
     * POST:
     * - Duration is set to 30 and currentFrame to 0 after initialization.
     */
    @Override
    public void setDefaultValues() {
        duration = 30;
        currentFrame = 0;
    }

    /**
     * Updates the explosion state.
     *
     * PRE:
     * - delta >= 0.
     *
     * POST:
     * - currentFrame is incremented by one.
     *
     * @param delta Time elapsed since the previous update.
     */
    @Override
    public void update(float delta){
        currentFrame++;
    }

    /**
     * Determines whether the explosion animation has finished.
     *
     * POST:
     * - Returns true if currentFrame >= duration.
     * - Returns false otherwise.
     * - The explosion state is not modified.
     *
     * @return true if the explosion has finished; false otherwise.
     */
    public boolean finished(){
        return currentFrame >= duration;
    }

    /**
     * Draws the explosion effect.
     *
     * PRE:
     * - g != null.
     *
     * POST:
     * - A circular explosion effect is rendered on the provided graphics context.
     * - The explosion state is not modified.
     *
     * @param g Graphics context used for rendering.
     */
    @Override
    public void draw(Graphics g){
        g.setColor(new Color(255, 0, 0, 100));
        g.fillOval((int) x - radius, (int) y - radius, radius * 2, radius * 2);
    }

    /**
     * Resolves the effects of the explosion on a plane based on its distance.
     *
     * PRE:
     * - plane != null.
     *
     * POST:
     * - Returns an ExplosionResult according to the distance between the
     *   explosion center and the plane.
     * - The explosion state is not modified.
     *
     * @param plane Plane affected by the explosion.
     * @return ExplosionResult describing damage, score and destruction state.
     */
    public ExplosionResult resolveImpact(Plane plane){
        double distance = calculateDistance(plane);

        if (distance > size){
            return new ExplosionResult(40, 0, false);
        } else if (distance >= (size * 0.80)){
            return new ExplosionResult(20, 20, false);
        } else if (distance >= (size * 0.20)){
            return new ExplosionResult(0, 40, false);
        } else {
            return new ExplosionResult(0, 0, true);
        }
    }

    /**
     * Calculates the shortest distance between the explosion center and the plane hitbox.
     *
     * PRE:
     * - plane != null
     *
     * POST:
     * - Returns the minimum distance between the explosion center and the plane collision area.
     * - Returns 0 if the explosion center is inside the plane hitbox.
     * - The returned value is always greater than or equal to 0.
     * - Neither the explosion nor the plane state is modified.
     *
     * @param plane the plane whose collision area is evaluated
     * @return the shortest distance between the explosion center and the plane hitbox
     */
    public double calculateDistance(Plane plane){
        Rectangle bounds = plane.getBounds();
        double nearestX = Math.max(bounds.x, Math.min(x, bounds.x + bounds.width));
        double nearestY = Math.max(bounds.y, Math.min(y, bounds.y + bounds.height));
        return Math.hypot(nearestX - x, nearestY - y);
    }
}