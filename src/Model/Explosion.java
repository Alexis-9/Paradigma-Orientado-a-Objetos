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

    @Override
    public void update(float delta){
        currentFrame++;
    }

    public boolean finished(){
        return currentFrame >= duration;
    }

    @Override
    public void draw(Graphics g){
        g.setColor(new Color(255, 0, 0, 100));
        g.fillOval((int) x - radius, (int) y - radius, radius * 2, radius * 2);
    }

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

    public double calculateDistance(Plane plane){
        double dx = plane.getCenterX() - x;
        double dy = plane.getCenterY() - y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}