package Model;

import java.awt.*;

public abstract class Entity {

    public double speed;

    protected double x;
    protected double y;

    protected int width;
    protected int height;

    Image img;

    public abstract void update(float delta);

    public Rectangle getBounds(){
        return new Rectangle((int) x, (int) y, width, height);
    }

    public double getX(){return x;}

    public double getY(){return y;}

    public int getWidth(){return width;}

    public int getHeight(){return height;}

    public double getCenterX(){return x + (double) width / 2;}

    public double getCenterY(){return y + (double) height / 2;}

    public abstract void draw(Graphics g);

    public abstract void setDefaultValues();

}
