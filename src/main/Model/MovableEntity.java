package main.Model;

public abstract class MovableEntity extends Entity {

    private double speed;

    protected void setSpeed(double speed) {
        if (speed < 0) return;
        this.speed = speed;
    }

    protected double getSpeed() {
        return speed;
    }

    protected void moveBy(double dx, double dy) {
        setPosition(getX() + dx, getY() + dy);
    }
}