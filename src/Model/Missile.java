package Model;

import javax.swing.*;
import java.awt.*;

public class Missile extends Entity {

    private int screenHeight;
    private int explosionY;

    private Explosion explosion;

    private boolean exploding;
    private boolean finished;
    private boolean damageApplied;

    public Missile(double startX, double startY, double speed, int screenHeight) {
        setPosition(startX, startY);
        setSize(16, 32);
        setSpeed(speed);

        this.screenHeight = screenHeight;

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
        if (!exploding && getImage() != null){
            g.drawImage(getImage(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        }
        if (explosion != null){
            explosion.draw(g);
        }
    }

    @Override
    public void setDefaultValues() {
        // REVISAR: usa Images.PLANE (imagen del avion). Deberia ser Images.MISSILE.
        setImage(new ImageIcon(getClass().getResource(Images.MISSILE.getPath())).getImage());
    }

    public void move(float delta){
        moveBy(0, getSpeed() * delta);
    }

    public void explode(){
        explosion = new Explosion((int) getX(), (int) getY());
    }

    public boolean shouldExplode(){
        return getY() >= explosionY;
    }

    public void setExplosionY(){
        explosionY = (int)(Math.random() * (screenHeight - 200)) + 200;
    }

    public boolean isExploding(){ return exploding; }

    public void triggerExplosion(){
        if(!exploding){
            exploding = true;
            explode();
        }
    }

    public boolean isFinished(){ return finished; }

    public boolean isDamageApplied(){ return damageApplied; }

    public void setDamageApplied(boolean damageApplied){ this.damageApplied = damageApplied; }

    public Explosion getExplosion(){ return explosion; }

    public boolean collidesWith(Entity other){
        return getBounds().intersects(other.getBounds());
    }
}