package entity;
import game.Direction;

import javax.swing.*;
import java.awt.*;

public class Drone extends Entity {
    int droneWidth;
    int droneHeight;
    Direction direction;

    Image droneImg;

    int shootCounter = 0;
    double shootCooldown;

    public Drone(int startX, int startY, Direction direction, int speed, int shootCooldown){

        this.x = startX;
        this.y = startY;

        this.direction = direction;

        droneWidth = 70;
        droneHeight = 70;

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
        g.drawImage(droneImg, x, y, droneWidth, droneHeight, null);
    }

    public boolean outOfScreen(int screenWidth){

        return x < -droneWidth || x > screenWidth;
    }

    public boolean canShoot(){
        return shootCounter >= shootCooldown;
    }

    public void resetShootCounter(){

        shootCounter = 0;
    }

    public int getCenterX(){
        return x + droneWidth / 2;
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