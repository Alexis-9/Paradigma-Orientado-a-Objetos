package entity;

import game.Explosion;

import javax.swing.*;
import java.awt.*;

public class Missile extends Entity {

    private int screenHeight;

    private int explosionY;
    private Explosion explosion;

    private boolean exploding;
    private boolean finished;
    private boolean damageApplied;


    public Missile(int startX, int startY, int speed, int screenHeight) {
        x = startX;
        y = startY;

        this.screenHeight = screenHeight;

        width = 16;
        height = 32;

        this.speed = speed;

        img = new ImageIcon(
                getClass().getResource("/Images/Missile/Missile.png")
        ).getImage();


        setExplosionAltitude();

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


    public void draw(Graphics g) {
        if (!exploding){
            g.drawImage(img, x, y, width, height, null);

        }

        if (explosion != null){
            explosion.draw(g);
        }
    }


    public void move(){
        y+= speed;
    }

    public void explode(){
        explosion = new Explosion(x,y);
    }

    public boolean shouldExplode(){
        return y >= explosionY;
    }

    public void setExplosionAltitude(){
        explosionY = 200 + (int) (Math.random() * (screenHeight-200));
    }

    public int getAltitude(){return y;}

    public int getX(){return x;}

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