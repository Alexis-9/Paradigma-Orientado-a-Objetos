package Model;

import java.awt.*;

public abstract class Entity implements Updatable, Drawable, Collidable {

    private double speed;

    private double x;
    private double y;

    private int width;
    private int height;

    private Image img;

    protected void setPosition(double x, double y) { this.x = x; this.y = y; }
    protected void setSize(int width, int height) { this.width = width; this.height = height; }
    protected void moveBy(double dx, double dy) { this.x += dx; this.y += dy; }
    protected void setSpeed(double speed) { this.speed = speed; }
    protected double getSpeed() { return speed; }
    protected void setImage(Image img) { this.img = img; }
    protected Image getImage() { return img; }

    public Rectangle getBounds(){
        return new Rectangle((int) x, (int) y, width, height);
    }

    public double getX(){return x;}
    public double getY(){return y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public double getCenterX(){return x + (double) width / 2;}
    public double getCenterY(){return y + (double) height / 2;}

    public abstract void update(float delta);
    public abstract void draw(Graphics g);
    public abstract void setDefaultValues();
}