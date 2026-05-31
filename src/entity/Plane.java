package entity;

import javax.swing.*;
import java.awt.*;

public class Plane extends Entity{

    int hitboxWidth;
    int hitboxHeight;

    int hitboxOffsetX;
    int hitboxOffsetY;

    int screenWidth;
    int screenHeight;
    int tileSize;

    int maxEnergy;
    int currentEnergy;

    public Plane(
            int screenWidth,
            int screenHeight,
            int tileSize,
            Image planeImg
    ) {

        this.img = planeImg;

        this.screenHeight = screenHeight;

        this.screenWidth = screenWidth;

        this.tileSize = tileSize;

        setDefaultValues();
    }

    public void setDefaultValues(){
        width = tileSize;
        height = tileSize * 2;

        x = screenWidth / 2 - width / 2;
        y = screenHeight - height - tileSize;

        this.maxEnergy = 100;
        this.currentEnergy = maxEnergy;
        speed = 4;

        hitboxWidth = width - 40;
        hitboxHeight = height - 40;

        hitboxOffsetX = 20;
        hitboxOffsetY = 20;
    }

    public void move(int dx, int dy){

        int currentSpeed = speed;

        if(dx != 0 && dy != 0){
            currentSpeed = 3;
        }

        x += dx * currentSpeed;
        y += dy * currentSpeed;


        validateHorizontalBounds();

        validateVerticalBounds();
    }


    @Override
    public void update(){
    }

    public void draw(Graphics g){

        g.drawImage(img,x,y,width,height,null);

    }

    public int getY(){
        return y;
    }

    public int getX(){return x;}

    public int getCurrentEnergy(){return currentEnergy;}

    public int getMaxEnergy(){return maxEnergy;}

    public int getWidth(){return width;}

    public int getHeight(){return height;}

    public void reduceEnergy(int amount){
        currentEnergy -= amount;
        if (currentEnergy<=0) {
            currentEnergy =0;
        }
    }


    public void validateHorizontalBounds(){
        if (x + width > screenWidth){
            x = screenWidth - width;

        }

        if(x < 0){
            x = 0;
        }
    }

    public void validateVerticalBounds() {
        if(y < 200){
            y = 200;
        }

        if(y + height > screenHeight ){
            y = screenHeight - height ;
        }
    }

    public void restoreEnergy(){
        currentEnergy = maxEnergy;
    }

    @Override
    public Rectangle getBounds(){
        return new Rectangle(
                x + hitboxOffsetX,
                y + hitboxOffsetY,
                hitboxWidth,
                hitboxHeight
        );
    }
}
