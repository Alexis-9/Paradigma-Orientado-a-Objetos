package game;

public class Level {

    private int levelNumber;
    private int droneSpeed;
    private int missileSpeed;
    private int shootCooldown;

    public Level(int levelNumber){

        this.levelNumber = levelNumber;

        droneSpeed = 2;

        missileSpeed = 4;

        shootCooldown = 120;
    }

    public void nextLevel() {

        levelNumber++;

        droneSpeed = (int)(droneSpeed * 1.15);

        missileSpeed = (int)(missileSpeed * 1.15);

        shootCooldown = (int)(shootCooldown * 0.85);
    }

    public int getDroneSpeed() {
        return droneSpeed;
    }

    public int getMissileSpeed() {
        return missileSpeed;
    }

    public int getShootCooldown() {
        return shootCooldown;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}