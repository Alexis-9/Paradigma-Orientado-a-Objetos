package entity;
import game.Direction;

import javax.swing.*;
import java.awt.*;

public class Drone extends Entity {
    private Direction direction;

    private Image droneImg;

    private int shootCounter = 0;
    private double shootCooldown;

    public Drone(int startX, int startY, Direction direction, int speed, int shootCooldown){

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

    public void draw(Graphics g){
        g.drawImage(droneImg, x, y, width, height, null);
    }

    public boolean outOfScreen(int screenWidth){

        return x < -width || x > screenWidth;
    }

    public boolean canShoot(){
        return shootCounter >= shootCooldown;
    }

    public void resetShootCounter(){
        shootCounter = 0;
    }

    public int getCenterX(){
        return x + width / 2;
    }

    public int getY(){
        return y;
    }


    public void move(){
        if (direction == Direction.Left){
            x -= speed;
        } else if (direction == Direction.Right) {
            x += speed;
        }
    }
}