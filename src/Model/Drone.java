package Model;

import javax.swing.*;
import java.awt.*;

public class Drone extends Entity {
    private Direction direction;

    private int shootCounter = 0;
    private int shootCooldown;

    public Drone(int startX, int startY, int width, int height, Direction direction, int shootCooldown){
        setPosition(startX, startY);
        setSize(width, height);

        this.direction = direction;
        this.shootCooldown = shootCooldown;
    }

    @Override
    public void setDefaultValues(){
        setSpeed(180);
        setImage(new ImageIcon(getClass().getResource(Images.DRONE.getPath())).getImage());
    }

    @Override
    public void update(float delta) {
        move(delta);
        shootCounter++;
    }

    @Override
    public void draw(Graphics g) {
        if (getImage() != null) {
            g.drawImage(getImage(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        }
    }

    public void move(float delta){
        if (direction == Direction.LEFT){
            moveBy(-getSpeed() * delta, 0);
        } else if (direction == Direction.RIGHT) {
            moveBy(getSpeed() * delta, 0);
        }
    }

    public boolean outOfScreen(int screenWidth){
        return getX() < -getWidth() || getX() > screenWidth;
    }

    public Missile tryShoot(double missileSpeed, int screenHeight){
        if(shootCounter < shootCooldown){return null;}

        shootCounter = 0;

        return new Missile(
                getCenterX(),
                getY(),
                missileSpeed,
                screenHeight
        );
    }
}