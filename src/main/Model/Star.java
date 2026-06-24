package main.Model;

import java.awt.Color;
import java.awt.Graphics;

public class Star extends MovableEntity {

    private static final int MIN_BRIGHTNESS = 0;
    private static final int MAX_BRIGHTNESS = 255;

    private final int brightness;   // gray level 0-255

    public Star(double x, double y, double speed, int size, int brightness) {
        setPosition(x, y);
        setSize(size, size);
        setSpeed(speed);
        this.brightness = clamp(brightness);

        setDefaultValues();
    }

    /**
     * Updates the star position each frame.
     *
     * PRE:
     * - delta >= 0.
     *
     * POST:
     * - The Y position increases by getSpeed() * delta.
     * - The X position remains unchanged.
     *
     * @param delta Time elapsed since the last update.
     */
    @Override
    public void update(float delta) {
        moveBy(0, getSpeed() * delta);
    }

    /**
     * Draws the star.
     *
     * PRE:
     * - g != null.
     *
     * POST:
     * - A square of the star's size and brightness is rendered.
     * - The internal state of the star is not modified.
     *
     * @param g Graphics context used for rendering.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(brightness, brightness, brightness));
        g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
    }

    /**
     * Repositions the star to a new location.
     *
     * PRE:
     * - none.
     *
     * POST:
     * - The star is placed at (x, y).
     *
     * @param x new horizontal position.
     * @param y new vertical position.
     */
    public void reposition(double x, double y) {
        setPosition(x, y);
    }

    /**
     * Initializes the star's default values.
     *
     * POST:
     * - A star has no image; it is drawn as a colored square,
     *   so no resource is loaded here.
     */
    @Override
    public void setDefaultValues() {
        // Intentionally empty: stars are rendered procedurally, not from an image.
    }

    private int clamp(int value) {
        return Math.max(MIN_BRIGHTNESS, Math.min(MAX_BRIGHTNESS, value));
    }
}