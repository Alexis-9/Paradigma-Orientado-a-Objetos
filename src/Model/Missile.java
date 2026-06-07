package Model;

import javax.swing.*;
import java.awt.*;

public class Missile extends Entity{

    private Explosion explosion;

    private boolean exploding;
    private boolean finished;
    private boolean damageApplied;

    public Missile(double startX, double startY, double speed, int screenHeight) {
        x = startX;
        y = startY;

        this.screenHeight = screenHeight;

        width = 16;
        height = 32;

        this.speed = speed;

        setExplosionY();

        exploding = false;
        finished = false;
        damageApplied = false;
    }

    @Override
    public void update(float delta) {
        if (!exploding){
            move(delta);

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

    }

    @Override
    public void setDefaultValues() {
        this.img = new ImageIcon(getClass().getResource(Images.PLANE.getPath())).getImage();
    }

    public void move(float delta){
        y += speed * delta;
    }

    public void explode(){
        explosion = new Explosion((int) x,(int) y);
    }

    public boolean shouldExplode(){
        return y >= explosionY;
    }

}
