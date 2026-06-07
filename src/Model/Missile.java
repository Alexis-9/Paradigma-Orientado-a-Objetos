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
        if (!exploding && img != null){
            g.drawImage(img, (int) x, (int) y, width, height, null);
        }
        if (explosion != null){
            explosion.draw(g);
        }
    }

    @Override
    public void setDefaultValues() {
        // REVISAR: usa Images.PLANE (imagen del avion). Deberia ser Images.MISSILE.
        // Ademas los paths del enum Images estan rotos y la imagen no carga.
        this.img = new ImageIcon(getClass().getResource(Images.PLANE.getPath())).getImage();
    }

    public void move(float delta){
        y += speed * delta;
    }

    public void explode(){
        explosion = new Explosion((int) x, (int) y);
    }

    public boolean shouldExplode(){
        return y >= explosionY;
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