package Model;

import javax.swing.*;
import java.awt.*;

public class Drone extends Entity {
    private Direction direction;

    private int shootCounter = 0;
    private int shootCooldown;

    public Drone(int startX, int startY, int width, int height ,Direction direction, int shootCooldown){
        this.x = startX;
        this.y = startY;

        this.direction = direction;

        this.width = width;
        this.height = height;

        this.shootCooldown = shootCooldown;
    }

    @Override
    public void setDefaultValues(){
        this.speed = 3;
        this.img = new ImageIcon(getClass().getResource(Images.DRONE.getPath())).getImage();
    }


    @Override
    public void update(float delta) {
        //move(delta);
        shootCounter++;
    }

    @Override
    public void draw(Graphics g) {

    }

    public void move(float delta){
        if (direction == Direction.LEFT){
            x -= speed * delta;
        } else if (direction == Direction.RIGHT) {
            x += speed * delta;
        }
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