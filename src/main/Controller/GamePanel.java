package main.Controller;

import main.Model.*;
import main.audio.SoundManager;
import main.View.MenuView;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static final int FPS = 60;
    private static final float DELTA = 1f / FPS;

    private final GameController controller;
    private final Timer gameTimer;
    private final MenuView menuView;

    public GamePanel() {
        KeyHandler keyHandler = new KeyHandler();
        SoundManager soundManager = new SoundManager();

        this.controller = new GameController(keyHandler, soundManager);
        this.menuView = new MenuView(
                GameController.SCREEN_WIDTH,
                GameController.SCREEN_HEIGHT
        );

        setPreferredSize(new Dimension(GameController.SCREEN_WIDTH, GameController.SCREEN_HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListener(keyHandler);

        SwingUtilities.invokeLater(this::requestFocusInWindow);

        gameTimer = new Timer(1000 / FPS, e -> {
            controller.update(DELTA);
            repaint();
        });

        gameTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = GameController.SCREEN_WIDTH;
        int h = GameController.SCREEN_HEIGHT;

        if (controller.getGameState() == GameState.MENU) {
            menuView.render(g, controller.isAudioMuted());
            return;
        }

        if (controller.getGameState() == GameState.GAME_OVER) {
            drawGameOver(g, w, h);
            return;
        }

        drawGame(g);
        drawHud(g);

        if (controller.getGameState() == GameState.PAUSED) {
            drawPause(g, w, h);
        }
    }

    private void drawGame(Graphics g) {
        controller.getPlane().draw(g);
        controller.getSquadron().draw(g);

        for (Missile missile : controller.getMissiles()) {
            missile.draw(g);
        }
    }

    /**
     * Draws the heads-up display (HUD) with player and game information.
     * PRE:
     * - g != null.
     * - controller != null.
     * POST:
     * - Displays player lives as hearts.
     * - Displays score, level, energy bar and sound status.
     * - No game state is modified.
     *
     * @param g graphics context used for rendering
     */
    private void drawHud(Graphics g) {
        Player player = controller.getPlayer();

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(10, 10, GameController.SCREEN_WIDTH - 20, 90, 18, 18);

        drawLives(g, player.getLives(), 25, 45);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));

        g.drawString("Score: " + player.getScore(), 180, 42);
        g.drawString("Level: " + controller.getCurrentLevel().getLevelNumber(), 360, 42);

        String soundText = controller.isAudioMuted() ? "[M] SOUND: OFF" : "[M] SOUND: ON";
        g.drawString(soundText, 560, 42);

        drawEnergyBar(
                g,
                25,
                75,
                250,
                16,
                controller.getPlane().getCurrentEnergy(),
                100
        );
    }

    /**
     * Draws player lives as heart symbols.
     * PRE:
     * - g != null.
     * - lives >= 0.
     * POST:
     * - Draws one heart for each current player life.
     * - No game state is modified.
     *
     * @param g graphics context used for rendering
     * @param lives current player lives
     * @param x initial x position
     * @param y y position
     */
    private void drawLives(Graphics g, int lives, int x, int y) {
        g.setFont(new Font("Segoe UI Symbol", Font.BOLD, 28));
        g.setColor(Color.RED);

        for (int i = 0; i < lives; i++) {
            g.drawString("\u2665", x + (i * 30), y);
        }
    }

    /**
     * Draws the plane energy as a horizontal bar.
     * PRE:
     * - g != null.
     * - max > 0.
     * - width > 0.
     * - height > 0.
     * POST:
     * - Draws a filled energy bar proportional to current energy.
     * - Energy percentage is clamped between 0 and 1.
     * - No game state is modified.
     * @param g graphics context used for rendering
     * @param x bar x position
     * @param y bar y position
     * @param width bar width
     * @param height bar height
     * @param current current energy value
     * @param max maximum energy value
     */
    private void drawEnergyBar(Graphics g, int x, int y, int width, int height, double current, double max) {
        double percentage = current / max;

        if (percentage < 0) {
            percentage = 0;
        }

        if (percentage > 1) {
            percentage = 1;
        }

        int filledWidth = (int) (width * percentage);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("ENERGY", x, y - 6);

        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(x + 1, y + 1, width - 1, height - 1);

        g.setColor(Color.GREEN);
        g.fillRect(x + 1, y + 1, Math.max(0, filledWidth - 1), height - 1);
    }

    private void drawGameOver(Graphics g, int w, int h) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("GAME OVER", w / 2 - 150, h / 2);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + controller.getPlayer().getScore(), w / 2 - 50, h / 2 + 40);
        g.drawString("ENTER para reiniciar", w / 2 - 90, h / 2 + 70);
    }

    private void drawPause(Graphics g, int w, int h) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("PAUSA", w / 2 - 80, h / 2);
    }
}