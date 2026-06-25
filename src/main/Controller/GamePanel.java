package main.Controller;

import main.Model.*;
import main.audio.SoundManager;
import main.View.MenuView;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {

    private static final int FPS = 60;
    private static final float DELTA = 1f / FPS;

    private final GameController controller;
    private final Timer gameTimer;
    private final MenuView menuView;
    private final Map<Images, Image> backgrounds;

    public GamePanel() {
        KeyHandler keyHandler = new KeyHandler();
        SoundManager soundManager = new SoundManager();

        this.controller = new GameController(keyHandler, soundManager);
        this.menuView = new MenuView(
                GameController.SCREEN_WIDTH,
                GameController.SCREEN_HEIGHT
        );

        this.backgrounds = loadBackgrounds();

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

        drawBackground(g);
        drawGame(g);
        drawHud(g);

        if (controller.getGameState() == GameState.PAUSED) {
            drawPause(g, w, h);
        }
    }

    private Map<Images, Image> loadBackgrounds() {
        Map<Images, Image> loadedBackgrounds = new HashMap<>();

        Images[] levelBackgrounds = {
                Images.LEVEL_1,
                Images.LEVEL_2,
                Images.LEVEL_3,
                Images.LEVEL_4,
                Images.LEVEL_5
        };

        for (Images image : levelBackgrounds) {
            java.net.URL imageUrl = getClass().getResource(image.getPath());

            if (imageUrl != null) {
                loadedBackgrounds.put(image, new ImageIcon(imageUrl).getImage());
            } else {
                System.out.println("Background not found: " + image.getPath());
            }
        }

        return loadedBackgrounds;
    }

    private void drawBackground(Graphics g) {
        int levelNumber = controller.getCurrentLevel().getLevelNumber();

        Images backgroundImage = Images.getBackgroundByLevel(levelNumber);
        Image background = backgrounds.get(backgroundImage);

        if (background != null) {
            g.drawImage(
                    background,
                    0,
                    0,
                    GameController.SCREEN_WIDTH,
                    GameController.SCREEN_HEIGHT,
                    null
            );
            return;
        }

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, GameController.SCREEN_WIDTH, GameController.SCREEN_HEIGHT);
    }

    private void drawGame(Graphics g) {
        controller.getPlane().draw(g);
        controller.getSquadron().draw(g);

        for (Missile missile : controller.getMissiles()) {
            missile.draw(g);
        }
    }

    private void drawHud(Graphics g) {
        Player player = controller.getPlayer();

        Graphics2D g2 = (Graphics2D) g;

        // HUD background
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(10, 10, GameController.SCREEN_WIDTH - 20, 90, 18, 18);

        // Lives
        drawLives(g, player.getLives(), 25, 45);

        // Main HUD texts
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));

        g.drawString("Score: " + player.getScore(), 230, 42);
        g.drawString("Level: " + controller.getCurrentLevel().getLevelNumber(), 455, 42);

        String soundText = controller.isAudioMuted() ? "[M] SOUND: OFF" : "[M] SOUND: ON";
        g.drawString(soundText, 620, 42);

        // Energy bar
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

    private void drawLives(Graphics g, int lives, int x, int y) {
        g.setFont(new Font("Segoe UI Symbol", Font.BOLD, 26));
        g.setColor(Color.RED);

        if (lives <= 5) {
            for (int i = 0; i < lives; i++) {
                g.drawString("\u2665", x + (i * 26), y);
            }
        } else {
            g.drawString("\u2665 x" + lives, x, y);
        }
    }

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

        g.setColor(Color.RED);
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