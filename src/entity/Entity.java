package entity;

import java.awt.*;

public abstract class Entity {

    public int speed;

    protected int x;
    protected int y;

    protected int width;
    protected int height;

    Image img;

    public abstract void update();

    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
}
