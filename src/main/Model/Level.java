package main.Model;

public class Level {

    private int levelNumber;
    private double droneSpeed;
    private double missileSpeed;
    private double shootCooldown;
    private double explosionSize;

    public Level(int levelNumber){
        this.levelNumber = levelNumber;
        droneSpeed = 135;
        missileSpeed = 260;
        explosionSize = 75;
        shootCooldown = 90;
    }

    /**
     * Advances the game to the next level and increases difficulty parameters.
     *
     * PRE:
     * - The Level object must be properly initialized.
     *
     * POST:
     * - levelNumber is incremented by 1.
     * - droneSpeed increases by 15%.
     * - missileSpeed increases by 15%.
     * - shootCooldown decreases by 15%.
     */
    public void nextLevel() {
        if (levelIsInfinite()){return;}

        levelNumber++;
        droneSpeed += droneSpeed * 0.15;
        missileSpeed += missileSpeed * 0.15;
        explosionSize += explosionSize * 0.15;
        shootCooldown -= shootCooldown * 0.15;
    }

    /**
     * Determines whether the level system has entered infinite mode.
     *
     * PRE:
     * - levelNumber is initialized.
     *
     * POST:
     * - Returns true if levelNumber >= 5.
     * - Does not modify the state of the object.
     *
     * @return true if the level is considered infinite, false otherwise.
     */
    public boolean levelIsInfinite() { return levelNumber >= 5; } // Level 5 is infinite.

    /**
     * Checks whether the current level has been completed.
     *
     * PRE:
     * - squadron != null.
     * - squadron.getDrones() returns a valid collection.
     *
     * POST:
     * - Returns true if:
     *   - there are no drones alive in the squadron, AND
     *   - no drones remaining to spawn, AND
     *   - the level is not infinite.
     * - Does not modify Level or Squadron state.
     *
     * @return true if the level is finished, false otherwise.
     */
    public boolean levelFinished(boolean isDroneEmpty, boolean notDronesRemaining) {
        return isDroneEmpty && notDronesRemaining && !levelIsInfinite();
    }

    /**
     * Getters.
     */
    public double getDroneSpeed() { return droneSpeed; }
    public double getMissileSpeed() { return missileSpeed; }
    public double getExplosionSize() { return explosionSize; }
    public double getShootCooldown() { return shootCooldown; }
    public int getLevelNumber() { return levelNumber; }
}