package main.Controller;

import main.Model.*;
import main.audio.AudioPlayer;
import main.audio.Sound;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    public static final int TILE_SIZE = 48;
    public static final int SCREEN_WIDTH = TILE_SIZE * 16;   // 768
    public static final int SCREEN_HEIGHT = TILE_SIZE * 16;  // 768
    private static final int TOP_BOUND = 250;
    private static final float LEVEL_UP_DURATION = 2.0f; // segundos que dura el cartel
    private float levelUpTimer = 0f;

    private GameState gameState = GameState.MENU;

    private Player player;
    private Plane plane;
    private Squadron squadron;
    private Level currentLevel;
    private ArrayList<Missile> missiles;

    private final InputSource input;
    private final AudioPlayer audio;

    public GameController(InputSource input, AudioPlayer audio) {
        this.input = input;
        this.audio = audio;
        startNewGame();
        gameState = GameState.MENU;
    }
    /**
     * Initializes a new game session.
     *
     * POST:
     * - Player is reset.
     * - Plane is created at starting position.
     * - Level is reset to 1.
     * - Missiles and squadron are initialized.
     * - Game state is set to RUNNING.
     */
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

    /**
     * Initializes a new level without resetting the entire game.
     *
     * POST:
     * - Squadron is reset for the new level.
     * - All missiles are cleared.
     */
    private void startLevel() {
        squadron = new Squadron(SCREEN_WIDTH, currentLevel);
        missiles.clear();
    }

    /**
     * Updates the game state each frame.
     *
     * PRE:
     * - delta >= 0.
     *
     * POST:
     * - Mute input is processed before any game state logic.
     * - Audio may be muted or unmuted if the mute key is pressed.
     * - Game state is updated according to current GameState.
     * - Player input is processed when the game is running.
     * - Plane, squadron and missiles are updated when the game is running.
     * - Game progression (levels, lives, game over) is evaluated when the game is running.
     *
     * @param delta time elapsed since last update
     */
    public void update(float delta) {
        if (input.consumeMutePress()) {
            audio.toggleMute();
            if (!audio.isMuted() && gameState == GameState.RUNNING) {
                audio.playBackground(Sound.BACKGROUND);
            }
        }

        if (gameState == GameState.MENU) {
            if (input.consumeEnterPress()) {
                startNewGame();
                audio.playBackground(Sound.BACKGROUND);
            }
            return;
        }

        if (gameState == GameState.PAUSED) {
            if (input.consumeEscPress()) {
                resume();
            }
            return;
        }

        if (gameState == GameState.GAME_OVER) {
            if (input.consumeEnterPress()) {
                startNewGame();
                audio.playBackground(Sound.BACKGROUND);
            }
            return;
        }

        if (input.consumeEscPress()) {
            pause();
            return;
        }

        movementInput(delta);
        squadron.update(delta);
        dronesShoot();
        if (levelUpTimer > 0f) {
            levelUpTimer -= delta;
        }
        updateMissiles(delta);
        checkNextLevel();
    }
    /**
     * Processes player movement input and updates plane position.
     *
     * PRE:
     * - delta >= 0.
     *
     * POST:
     * - dx and dy are computed from input state.
     * - Plane is updated and moved accordingly.
     * - Plane position is clamped inside screen bounds.
     *
     * @param delta time elapsed since last update
     */
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

    /**
     * Handles shooting logic for all drones in the squadron.
     *
     * PRE:
     * - squadron != null.
     * - currentLevel != null.
     *
     * POST:
     * - Each drone may generate a missile based on cooldown.
     * - New missiles are added to the missiles list.
     * - Shooting sound is played when a missile is created.
     */
    private void dronesShoot() {
        for (Drone drone : squadron.getDrones()) {
            int explosionY = TOP_BOUND + (int)(Math.random() * (SCREEN_HEIGHT - TOP_BOUND));

            Missile missile = drone.tryShoot(currentLevel.getMissileSpeed(), currentLevel.getExplosionSize(), explosionY, SCREEN_WIDTH);
            if (missile != null) {
                missiles.add(missile);
                audio.play(Sound.SHOOT);
            }
        }
    }

    /**
     * Updates all active missiles and removes finished ones.
     *
     * PRE:
     * - missiles != null.
     *
     * POST:
     * - Each missile is updated.
     * - Explosion logic is resolved.
     * - Finished missiles are removed from the list.
     *
     * @param delta time elapsed since last update
     */
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

    /**
     * Resolves missile explosion effects on the plane.
     *
     * PRE:
     * - missile != null.
     * - plane != null.
     *
     * POST:
     * - If missile collides with plane, explosion is triggered.
     * - If missile is exploding and damage not applied:
     *   - ExplosionResult is computed.
     *   - Player score is updated.
     *   - Extra life is checked.
     *   - Plane energy or lives are reduced accordingly.
     *   - Missile is marked as processed.
     *
     * @param missile missile to evaluate
     */
    private void resolveExplosion(Missile missile) {
        if (!missile.isExploding() && missile.collidesWith(plane)) {
            missile.triggerExplosion();
        }

        if (missile.isExploding() && !missile.isDamageApplied()) {
            ExplosionResult result = missile.getExplosion().resolveImpact(plane);
            audio.play(Sound.EXPLOSION);

            player.addScore(result.getScore());
            player.checkExtraLife(currentLevel.getLevelNumber());

            if (result.isLethal()) {
                loseLife();
                audio.play(Sound.LOSE_LIFE);
            } else {
                plane.reduceEnergy(result.getDamage());
                if (plane.getCurrentEnergy() <= 0) {
                    loseLife();
                    audio.play(Sound.LOSE_LIFE);
                }
            }

            missile.setDamageApplied(true);
        }
    }

    /**
     * Handles player life loss and game over conditions.
     *
     * POST:
     * - If player still has lives:
     *   - Plane energy is restored.
     *   - Lose life sound is played.
     * - If no lives remain:
     *   - Game state is set to GAME_OVER.
     *   - Background music is stopped.
     *   - Game over sound is played.
     */
    private void loseLife() {
        player.loseLife();
        if (player.getLives() > 0) {
            plane.restoreEnergy();
            audio.play(Sound.LOSE_LIFE);
        } else {
            gameState = GameState.GAME_OVER;
            audio.stopBackground();
            audio.play(Sound.GAME_OVER);
        }
    }

    /**
     * Checks if current level is completed and advances if necessary.
     *
     * POST:
     * - If the level is finished:
     *   - The level counter is advanced and the next level starts immediately.
     *   - The player receives bonus score and the extra-life check runs.
     *   - The level-up banner is shown for a few seconds while play continues.
     *   - The next-level sound is played.
     */
    private void checkNextLevel() {

        if (currentLevel.levelFinished(squadron.getDrones().isEmpty(),
                squadron.getDronesRemaining() == 0) && missiles.isEmpty()) {
            currentLevel.nextLevel();
            player.addScore(300);
            player.checkExtraLife(currentLevel.getLevelNumber());
            startLevel();
            audio.play(Sound.NEXT_LEVEL);
            levelUpTimer = LEVEL_UP_DURATION;
        }
    }

    /**
     * Pauses the game.
     *
     * POST:
     * - Game state is set to PAUSED.
     */
    private void pause() { gameState = GameState.PAUSED; }

    /**
     * Resumes the game.
     *
     * POST:
     * - Game state is set to RUNNING.
     */
    private void resume() { gameState = GameState.RUNNING; }

    /**
     * Returns whether the audio system is currently muted.
     *
     * POST:
     * - Returns true if audio is muted.
     * - Returns false if audio is enabled.
     * - No game state is modified.
     *
     * @return true if audio is muted; false otherwise
     */
    public boolean isAudioMuted() { return audio.isMuted(); }

    /**
     * Getters.
     */
    public GameState getGameState() { return gameState; }
    public Player getPlayer() { return player; }
    public Plane getPlane() { return plane; }
    public Squadron getSquadron() { return squadron; }
    public Level getCurrentLevel() { return currentLevel; }
    public List<Missile> getMissiles() { return missiles; }
    public boolean isLevelUpVisible() { return levelUpTimer > 0f; }
}