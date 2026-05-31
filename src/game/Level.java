package game;

public class Level {

    int levelNumber;

    double droneSpeed;

    double missileSpeed;

    double shootCooldown;

    public Level(int levelNumber){

        this.levelNumber = levelNumber;

        droneSpeed = 2;

        missileSpeed = 4;

        shootCooldown = 120;
    }

    public void nextLevel() {

        levelNumber++;

        droneSpeed += droneSpeed * 0.15;

        missileSpeed += missileSpeed * 0.15;

        shootCooldown -= shootCooldown * 0.15;
    }

    public boolean levelIsInfinte() { return levelNumber >= 5; }

    public boolean levelFinished(Squadron squadron) {
        return squadron.getDrones().isEmpty() && squadron.getDronesRemaining() == 0 && !levelIsInfinte();
    }

    public double getDroneSpeed() {
        return droneSpeed;
    }

    public double getMissileSpeed() {
        return missileSpeed;
    }

    public double getShootCooldown() {
        return shootCooldown;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}