package game;

import entity.Plane;
import java.awt.*;

public class Explosion {
    double x;
    double y;
    int radius;

    int duration;
    int currentFrame;

    public Explosion(double x, double y){
        this.x = x;
        this.y = y;

        radius = 150;

        duration = 30;
        currentFrame = 0;
    }

    public void update(){
        currentFrame++;
    }

    public boolean finished(){
        return currentFrame >= duration;
    }

    public void draw(Graphics g){

        g.setColor(new Color(255,0,0,100));

        g.fillOval(
                (int) x - radius,
                (int) y - radius,
                radius * 2,
                radius * 2
        );
    }

    public int calculateDamage(Plane plane){
        double distance = calculateDistance(plane);
        if (distance>=150){
            return 0;
        } else if (distance>=80 ) {
            return 20;
        } else if (distance>=20) {
            return 40;
        }
        return 100;
    }

    public double calculateDistance(Plane plane){
        double dx = plane.getCenterX() - x;
        double dy = plane.getCenterY() - y;

        return Math.sqrt(dx*dx + dy * dy);
    }
}
