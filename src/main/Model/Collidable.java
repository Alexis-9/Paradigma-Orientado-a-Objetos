package main.Model;
import java.awt.*;

public interface Collidable {
    double getX();
    double getY();
    int getWidth();
    int getHeight();

    default Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
    }
}