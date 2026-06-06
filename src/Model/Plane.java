package Model;

import javax.swing.*;
import java.awt.*;

public class Plane extends Entity{
    private int maxEnergy;
    private int currentEnergy;

    public Plane(float startX, float startY, int width, int height) {
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.img = planeImg;
        setDefaultValues();
    }

    public void setDefaultValues() {
        this.maxEnergy = 100;
        this.currentEnergy = maxEnergy;
        speed = 4;

        this.img = new ImageIcon(getClass().getResource(Images.PLANE.getPath())).getImage();

        //hitboxWidth = width - 40;
        //hitboxHeight = height - 40;
        //hitboxOffsetX = 20;
        //hitboxOffsetY = 20;
    }


    public void move(int dx, int dy, float delta){
        if (dx!=0 && dy!=0) {speed = 3;}

        x += dx * speed * delta;
        y += dy * speed * delta;
    }

    @Override
    public void update(float delta) {
        speed = 4;
    }

    @Override
    public void draw(Graphics g) {

    }

    public void reduceEnergy(int amount){
        currentEnergy -= amount;
        if (currentEnergy<=0) {
            currentEnergy =0;
        }
    }

    public void restoreEnergy(){
        currentEnergy = maxEnergy;
    }

    public int getCurrentEnergy(){return currentEnergy;}

    public int getMaxEnergy(){return maxEnergy;}

}
