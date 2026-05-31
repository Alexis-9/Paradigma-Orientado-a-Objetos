package entity;
import game.Direction;

import javax.swing.*;
import java.awt.*;

public class Drone extends Entity {
    Direction direction;

    Image droneImg;

    int shootCounter = 0;
    double shootCooldown;

    public Drone(int startX, int startY, Direction direction, double speed, double shootCooldown){

        this.x = startX;
        this.y = startY;

        this.direction = direction;

        width = 70;
        height = 70;

        this.speed = speed;
        this.shootCooldown = shootCooldown;

        droneImg = new ImageIcon(
                getClass().getResource("/Images/Drone/Drone.png")
        ).getImage();
    }

    @Override
    public void update() {
        move();
        shootCounter++;
    }

    @Override
    public void draw(Graphics g){
        g.drawImage(droneImg, (int) x, (int) y, width, height, null);
    }

    public boolean outOfScreen(int screenWidth){return x < -width || x > screenWidth;}

    public boolean canShoot(){
        return shootCounter >= shootCooldown;
    }

    public void resetShootCounter(){
        shootCounter = 0;
    }

    public void move(){
        if (direction == Direction.Left){
            x -= speed;
        } else if (direction == Direction.Right) {
            x += speed;
        }
    }
}