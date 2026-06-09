package Controller;

import Model.*;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    public static final int TILE_SIZE = 48;
    public static final int SCREEN_WIDTH = TILE_SIZE * 16;   // 768
    public static final int SCREEN_HEIGHT = TILE_SIZE * 16;  // 768
    private static final int TOP_BOUND = 200;

    private GameState gameState = GameState.RUNNING;

    private Player player;
    private Plane plane;
    private Squadron squadron;
    private Level currentLevel;
    private ArrayList<Missile> missiles;

    private final InputSource input;

    public GameController(InputSource input) {
        this.input = input;
        startNewGame();
    }

    private void startNewGame() {
        player = new Player();

        int planeSize = TILE_SIZE * 3;
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

    public void update(float delta) {
        if (gameState == GameState.PAUSED) {
            if (input.consumeEscPress()) resume();
            return;
        }

        if (gameState == GameState.GAME_OVER) {
            if (input.consumeEnterPress()) startNewGame();
            return;
        }

        if (input.consumeEscPress()) { pause(); return; }

        movementInput(delta);
        squadron.update(delta);
        dronesShoot();
        updateMissiles(delta);
        checkNextLevel();
    }

    private void movementInput(float delta) {
        int dx = 0;
        int dy = 0;

        if (input.isUpPressed()) dy--;
        if (input.isDownPressed()) dy++;
        if (input.isLeftPressed()) dx--;
        if (input.isRightPressed()) dx++;

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

    private void updateMissiles(float delta) {
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
        if (!missile.isExploding() && missile.collidesWith(plane)) {
            missile.triggerExplosion();
        }

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

    private void pause() { gameState = GameState.PAUSED; }
    private void resume() { gameState = GameState.RUNNING; }

    public GameState getGameState() { return gameState; }
    public Player getPlayer() { return player; }
    public Plane getPlane() { return plane; }
    public Squadron getSquadron() { return squadron; }
    public Level getCurrentLevel() { return currentLevel; }
    public List<Missile> getMissiles() { return missiles; }
}