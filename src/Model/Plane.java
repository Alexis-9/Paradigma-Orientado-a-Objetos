package Model;

import javax.swing.*;
import java.awt.*;

public class Plane extends Entity{
    private int maxEnergy;
    private int currentEnergy;

    public Plane(float startX, float startY, int width, int height) {
        setPosition(startX, startY);
        setSize(width, height);
        setDefaultValues();
    }

    public void setDefaultValues() {
        this.maxEnergy = 100;
        this.currentEnergy = maxEnergy;
        setSpeed(4);

        setImage(new ImageIcon(getClass().getResource(Images.PLANE.getPath())).getImage());

        //hitboxWidth = getWidth() - 40;
        //hitboxHeight = getHeight() - 40;
        //hitboxOffsetX = 20;
        //hitboxOffsetY = 20;
    }

    public void move(int dx, int dy, float delta){
        if (dx != 0 && dy != 0) { setSpeed(3); }

        moveBy(dx * getSpeed() * delta, dy * getSpeed() * delta);
    }

    @Override
    public void update(float delta) {
        setSpeed(4);
    }

    @Override
    public void draw(Graphics g) {
        if (getImage() != null) {
            g.drawImage(getImage(), (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        }
    }

    public void reduceEnergy(int amount){
        currentEnergy -= amount;
        if (currentEnergy <= 0) {
            currentEnergy = 0;
        }
    }

    public void restoreEnergy(){
        currentEnergy = maxEnergy;
    }

    public int getCurrentEnergy(){return currentEnergy;}

    public int getMaxEnergy(){return maxEnergy;}
}