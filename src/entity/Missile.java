package entity;

import game.Explosion;

import javax.swing.*;
import java.awt.*;

public class Missile extends Entity {

    int screenHeight;

    int explosionY;
    Explosion explosion;

    boolean exploding;
    boolean finished;
    boolean damageApplied;


    public Missile(double startX, double startY, double speed, int screenHeight) {
        x = startX;
        y = startY;

        this.screenHeight = screenHeight;

        width = 16;
        height = 32;

        this.speed = speed;

        img = new ImageIcon(
                getClass().getResource("/Images/Missile/Missile.png")
        ).getImage();

        setExplosionY();

        exploding = false;
        finished = false;
        damageApplied = false;
    }

    @Override
    public void update() {

        if (!exploding){
            move();

            if (shouldExplode()){
                exploding = true;
                explode();
            }
        }

        if (explosion != null){

            explosion.update();

            if (explosion.finished()){
                finished = true;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        if (!exploding){
            g.drawImage(img, (int)x, (int)y, width, height, null);

        }

        if (explosion != null){
            explosion.draw(g);
        }
    }


    public void move(){
        y+= speed;
    }

    public void explode(){
        explosion = new Explosion((int) x,(int) y);
    }

    public boolean shouldExplode(){
        return y >= explosionY;
    }

    public void setExplosionY(){
        explosionY =  (int)(Math.random() * (screenHeight - 200)) + 200;
    }

    public boolean isExploding(){
        return exploding;
    }

    public void triggerExplosion(){

        if(!exploding){

            exploding = true;

            explode();
        }
    }
    public boolean isFinished(){
        return finished;
    }

    public boolean isDamageApplied(){
        return damageApplied;
    }

    public void setDamageApplied(boolean damageApplied){
        this.damageApplied = damageApplied;
    }

    public Explosion getExplosion(){
        return explosion;
    }

    public boolean collidesWith(Entity other){
        return getBounds().intersects(other.getBounds());
    }

}