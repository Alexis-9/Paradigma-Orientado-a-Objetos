package main.Model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Starfield {

    private static final int LAYERS = 3;
    private static final double BASE_SPEED = 30.0;          // px/s for the farthest layer
    private static final double LAYER_SPEED_STEP = 50.0;
    private static final int BASE_BRIGHTNESS = 100;
    private static final int LAYER_BRIGHTNESS_STEP = 50;

    private final List<Star> stars;
    private final Random rng;
    private final int screenWidth;
    private final int screenHeight;

    public Starfield(int screenWidth, int screenHeight, int starCount) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.rng = new Random();
        this.stars = new ArrayList<>(starCount);
        for (int i = 0; i < starCount; i++) {
            stars.add(createStar(rng.nextInt(screenHeight)));
        }
    }

    /**
     * Creates a star assigned to a random parallax layer.
     *
     * PRE:
     * - 0 <= startY <= screenHeight.
     *
     * POST:
     * - Returns a Star whose speed, size and brightness depend on its layer,
     *   producing a sense of depth.
     *
     * @param startY initial vertical position.
     * @return a new Star with layer-based attributes.
     */
    private Star createStar(int startY) {
        int layer = rng.nextInt(LAYERS);
        double speed = BASE_SPEED + layer * LAYER_SPEED_STEP;
        int size = layer + 1;
        int brightness = BASE_BRIGHTNESS + layer * LAYER_BRIGHTNESS_STEP;
        double startX = rng.nextInt(screenWidth);
        return new Star(startX, startY, speed, size, brightness);
    }

    /**
     * Updates every star and recycles the ones that left the screen.
     *
     * PRE:
     * - delta >= 0.
     *
     * POST:
     * - Each star advances.
     * - Any star past the bottom edge is moved back to the top with a new
     *   random X, keeping the star count constant.
     *
     * @param delta Time elapsed since the last update.
     */
    public void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
            if (star.getY() > screenHeight) {
                star.reposition(rng.nextInt(screenWidth), 0);
            }
        }
    }

    /**
     * Draws the whole starfield.
     *
     * PRE:
     * - g != null.
     *
     * POST:
     * - Every star is rendered.
     * - No internal state is modified.
     *
     * @param g Graphics context used for rendering.
     */
    public void draw(Graphics g) {
        for (Star star : stars) {
            star.draw(g);
        }
    }
}