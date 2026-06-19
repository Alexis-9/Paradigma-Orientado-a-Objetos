package main.Model;

import java.awt.*;

public abstract class Entity {

    private double speed;

        private double x;
    private double y;

    private int width;
    private int height;

    private Image img;

    protected void moveBy(double dx, double dy) { this.x += dx; this.y += dy; }

    /**
     * Setters.
     */
    protected void setPosition(double x, double y) { this.x = x; this.y = y; }
    protected void setSize(int width, int height) { this.width = width; this.height = height; }
    protected void setSpeed(double speed) {  //ESTA VA EN MOVIMIENTO
        if (speed < 0) return;
        this.speed = speed; }

    /**
     * Getters.
     */
    protected double getSpeed() { return speed; } //ESTA NO VA
    protected void setImage(Image img) { this.img = img; }
    protected Image getImage() { return img; }

    public double getX(){return x;}
    public double getY(){return y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public double getCenterX(){return x + (double) width / 2;}
    public double getCenterY(){return y + (double) height / 2;}

    /**
     * Updates the entity state.
     *
     * PRE:
     * - delta >= 0.
     *
     * POST:
     * - The entity state is updated according to the implementation.
     *
     * @param delta Time elapsed since the previous update.
     */
    public abstract void update(float delta);

    /**
     * Draws the entity.
     *
     * PRE:
     * - g != null.
     *
     * POST:
     * - The entity is rendered on the provided graphics context.
     *
     * @param g Graphics context.
     */
    public abstract void draw(Graphics g);

    /**
     * Initializes the entity's default values.
     *
     * POST:
     * - The entity is left in a valid initial state.
     */
    public abstract void setDefaultValues();
}