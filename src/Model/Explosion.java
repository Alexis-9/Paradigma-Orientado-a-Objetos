package Model;

import java.awt.*;

public class Explosion implements Updatable, Drawable {

    private double x;
    private double y;
    private int radius;
    private int duration;
    private int currentFrame;

    public Explosion (double x, double y){
        this.x = x;
        this.y = y;
        radius = 150;
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

        if (distance > 150){
            return new ExplosionResult(40, 0, false);
        } else if (distance >= 80){
            return new ExplosionResult(20, 20, false);
        } else if (distance >= 20){
            return new ExplosionResult(0, 40, false);
        } else {
            return new ExplosionResult(0, 0, true);
        }
    }

    /**
     * Calculates the distance between the explosion center and the plane.
     *
     * PRE:
     * - plane != null.
     *
     * POST:
     * - Returns the Euclidean distance between the explosion and the plane.
     * - The explosion state is not modified.
     *
     * @param plane Plane whose distance is calculated.
     * @return Distance between the explosion center and the plane center.
     */
    public double calculateDistance(Plane plane){
        double dx = plane.getCenterX() - x;
        double dy = plane.getCenterY() - y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}