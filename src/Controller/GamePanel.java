package Controller;

import Model.*;
import audio.SoundManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static final int FPS = 60;
    private static final float DELTA = 1f / FPS;

    private final GameController controller;
    private final Timer gameTimer;

    public GamePanel() {
        KeyHandler keyHandler = new KeyHandler();
        SoundManager soundManager = new SoundManager();
        this.controller = new GameController(keyHandler, soundManager);

        setPreferredSize(new Dimension(GameController.SCREEN_WIDTH, GameController.SCREEN_HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListener(keyHandler);

        gameTimer = new Timer(1000 / FPS, e -> {
            controller.update(DELTA);
            repaint();
        });
        gameTimer.start();
    }

    /**
     * Renders the entire game frame.
     *
     * PRE:
     * - g != null.
     *
     * POST:
     * - If game state is GAME_OVER, only game over screen is drawn.
     * - Otherwise, game entities (plane, drones, missiles) are rendered.
     * - HUD is always rendered unless game is over.
     * - Pause overlay is rendered if game is paused.
     *
     * @param g graphics context used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = GameController.SCREEN_WIDTH;
        int h = GameController.SCREEN_HEIGHT;

        if (controller.getGameState() == GameState.GAME_OVER) {
            drawGameOver(g, w, h);
            return;
        }

        controller.getPlane().draw(g);
        controller.getSquadron().draw(g);
        for (Missile missile : controller.getMissiles()) {
            missile.draw(g);
        }

        drawHud(g);

        if (controller.getGameState() == GameState.PAUSED) {
            drawPause(g, w, h);
        }
    }

    /**
     * Draws the heads-up display (HUD) with player and game information.
     *
     * PRE:
     * - g != null.
     * - controller != null.
     *
     * POST:
     * - Displays lives, score, level and energy.
     * - No game state is modified.
     *
     * @param g graphics context used for rendering
     */
    private void drawHud(Graphics g) {
        Player player = controller.getPlayer();
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Lives: " + player.getLives(), 20, 30);
        g.drawString("Score: " + player.getScore(), 20, 55);
        g.drawString("Level: " + controller.getCurrentLevel().getLevelNumber(), 20, 80);
        g.drawString("Energy: " + controller.getPlane().getCurrentEnergy(), 20, 105);
    }

    /**
     * Draws the game over screen.
     *
     * PRE:
     * - g != null.
     *
     * POST:
     * - Displays GAME OVER text and final score.
     * - Shows restart instruction.
     * - No game state is modified.
     *
     * @param g graphics context used for rendering
     * @param w screen width
     * @param h screen height
     */
    private void drawGameOver(Graphics g, int w, int h) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("GAME OVER", w / 2 - 150, h / 2);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + controller.getPlayer().getScore(), w / 2 - 50, h / 2 + 40);
        g.drawString("ENTER para reiniciar", w / 2 - 90, h / 2 + 70);
    }

    /**
     * Draws the pause overlay.
     *
     * PRE:
     * - g != null.
     *
     * POST:
     * - Displays pause message on screen.
     * - No game state is modified.
     *
     * @param g graphics context used for rendering
     * @param w screen width
     * @param h screen height
     */
    private void drawPause(Graphics g, int w, int h) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("PAUSA", w / 2 - 80, h / 2);
    }
}