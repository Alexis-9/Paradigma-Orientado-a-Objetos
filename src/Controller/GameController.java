package Controller;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameController extends JPanel {

    private static final int TILE_SIZE = 48;
    private static final int SCREEN_WIDTH = TILE_SIZE * 16;   // 768
    private static final int SCREEN_HEIGHT = TILE_SIZE * 16;  // 768
    private static final int TOP_BOUND = 200;
    private static final int FPS = 60;

    private final float delta = 1f / FPS;

    private GameState gameState = GameState.RUNNING;

    private Timer gameTimer;

    private Player player;
    private Plane plane;
    private Squadron squadron;
    private Level currentLevel;
    private ArrayList<Missile> missiles;

    private final KeyHandler keyHandler = new KeyHandler();

    public GameController() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListener(keyHandler);

        startNewGame();
        startGameTimer();
    }

    private void startNewGame() {
        player = new Player();

        int planeSize = TILE_SIZE * 3;                       // 144
        float startX = SCREEN_WIDTH / 2f - planeSize / 2f;
        float startY = SCREEN_HEIGHT - planeSize - TILE_SIZE;
        plane = new Plane(startX, startY, planeSize, planeSize);

        currentLevel = new Level(1);
        missiles = new ArrayList<>();
        squadron = new Squadron(SCREEN_WIDTH, currentLevel);

        gameState = GameState.RUNNING;
    }

    private void startLevel() {
        squadron = new Squadron(SCREEN_WIDTH, currentLevel);
        missiles.clear();
    }

    private void startGameTimer() {
        gameTimer = new Timer(1000 / FPS, e -> {
            update();
            repaint();
        });
        gameTimer.start();
    }

    private void update() {
        if (gameState == GameState.PAUSED) {
            if (keyHandler.consumeEscPress()) resume();
            return;
        }

        if (gameState == GameState.GAME_OVER) {
            if (keyHandler.consumeEnterPress()) startNewGame();
            return;
        }

        // RUNNING
        if (keyHandler.consumeEscPress()) { pause(); return; }

        movementInput();
        squadron.update(delta);
        dronesShoot();
        updateMissiles();
        checkNextLevel();
    }

    private void movementInput() {
        int dx = 0;
        int dy = 0;

        if (keyHandler.upPressed) dy--;
        if (keyHandler.downPressed) dy++;
        if (keyHandler.leftPressed) dx--;
        if (keyHandler.rightPressed) dx++;

        plane.update(delta);
        plane.move(dx, dy, delta);
        plane.clampToScreen(SCREEN_WIDTH, SCREEN_HEIGHT, TOP_BOUND);
    }

    private void dronesShoot() {
        for (Drone drone : squadron.getDrones()) {
            Missile missile = drone.tryShoot(currentLevel.getMissileSpeed(), SCREEN_HEIGHT);
            if (missile != null) {
                missiles.add(missile);
            }
        }
    }

    private void updateMissiles() {
        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            missile.update(delta);
            resolveExplosion(missile);

            if (missile.isFinished()) {
                missiles.remove(i);
                i--;
            }
        }
    }

    private void resolveExplosion(Missile missile) {
        // impacto directo: el misil toca al avion y todavia no exploto
        if (!missile.isExploding() && missile.collidesWith(plane)) {
            missile.triggerExplosion();
        }

        // dano por explosion: exploto y aun no se aplico el dano
        if (missile.isExploding() && !missile.isDamageApplied()) {
            ExplosionResult result = missile.getExplosion().resolveImpact(plane);

            player.addScore(result.getScore());
            player.checkExtraLife(currentLevel.getLevelNumber());

            if (result.isLethal()) {
                loseLife();
            } else {
                plane.reduceEnergy(result.getDamage());
                if (plane.getCurrentEnergy() <= 0) {
                    loseLife();
                }
            }

            missile.setDamageApplied(true);
        }
    }

    private void loseLife() {
        player.loseLife();
        if (player.getLives() > 0) {
            plane.restoreEnergy();
        } else {
            gameState = GameState.GAME_OVER;
        }
    }

    private void checkNextLevel() {
        if (currentLevel.levelFinished(squadron)) {
            currentLevel.nextLevel();
            player.addScore(300);
            startLevel();
        }
    }

    private void pause() {
        gameState = GameState.PAUSED;
    }

    private void resume() {
        gameState = GameState.RUNNING;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState == GameState.GAME_OVER) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", SCREEN_WIDTH / 2 - 150, SCREEN_HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.WHITE);
            g.drawString("Score: " + player.getScore(), SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT / 2 + 40);
            g.drawString("ENTER para reiniciar", SCREEN_WIDTH / 2 - 90, SCREEN_HEIGHT / 2 + 70);
            return;
        }

        plane.draw(g);
        squadron.draw(g);
        for (Missile missile : missiles) {
            missile.draw(g);
        }

        // HUD minimo de prueba (texto plano, sin barra linda)
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Lives: " + player.getLives(), 20, 30);
        g.drawString("Score: " + player.getScore(), 20, 55);
        g.drawString("Level: " + currentLevel.getLevelNumber(), 20, 80);
        g.drawString("Energy: " + plane.getCurrentEnergy(), 20, 105);

        if (gameState == GameState.PAUSED) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("PAUSA", SCREEN_WIDTH / 2 - 80, SCREEN_HEIGHT / 2);
        }
    }
}