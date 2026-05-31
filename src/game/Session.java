package game;

import entity.Drone;
import entity.Missile;
import entity.Plane;
import gui.KeyHandler;

import java.awt.*;
import java.util.ArrayList;

public class Session {

    GameState gameState;

    Player player;

    Plane plane;

    Squadron squadron;

    ArrayList<Missile> missiles;

    Level currentLevel;

    int screenWidth;
    int screenHeight;

    public Session(
            int screenWidth,
            int screenHeight,
            int tileSize,
            Image planeSkin
    ){

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.player = new Player();

        this.plane = new Plane(
                screenWidth,
                screenHeight,
                tileSize,
                planeSkin
        );

        missiles = new ArrayList<>();

        currentLevel = new Level(1);

        startLevel();

        gameState = GameState.Running;
    }

    public void startLevel(){

        squadron = new Squadron(
                screenWidth,
                currentLevel
        );

        missiles.clear();
    }

    public void update(KeyHandler keyH){

        movementInput(keyH);

        squadron.update();

        // =========================
        // DRONES SHOOT
        // =========================

        for(Drone drone : squadron.getDrones()){

            if(drone.canShoot()){

                missiles.add(
                        new Missile(
                                drone.getCenterX(),
                                drone.getY(),
                                currentLevel.getMissileSpeed(),
                                screenHeight
                        )
                );

                drone.resetShootCounter();
            }
        }

        updateMissiles();

        checkNextLevel();
    }

    public void draw(Graphics g){

        if(gameState == GameState.Game_Over){

            drawGameOverScreen(g);

            return;
        }

        plane.draw(g);

        squadron.draw(g);

        for(Missile missile : missiles){

            missile.draw(g);
        }

        drawHUD(g);
    }

    public void drawHUD(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        g2.setColor(Color.WHITE);

        drawEnergyBar(g2);

        g2.drawString(
                "Lives: " + player.getLives(),
                20,
                80
        );

        g2.drawString(
                "Score: " + player.getScore(),
                20,
                120
        );

        g2.drawString(
                "Level: " + currentLevel.getLevelNumber(),
                20,
                160
        );
    }

    public void drawGameOverScreen(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        // =========================
        // BACKGROUND
        // =========================

        g2.setColor(Color.BLACK);

        g2.fillRect(
                0,
                0,
                screenWidth,
                screenHeight
        );

        // =========================
        // TITLE
        // =========================

        g2.setColor(Color.RED);

        g2.setFont(
                new Font("Arial", Font.BOLD, 64)
        );

        String title = "GAME OVER";

        int titleX =
                getCenteredTextX(g2, title);

        g2.drawString(
                title,
                titleX,
                220
        );

        // =========================
        // SCORE
        // =========================

        g2.setColor(Color.WHITE);

        g2.setFont(
                new Font("Arial", Font.BOLD, 32)
        );

        String scoreText =
                "Score: " + player.getScore();

        int scoreX =
                getCenteredTextX(g2, scoreText);

        g2.drawString(
                scoreText,
                scoreX,
                320
        );

        // =========================
        // RESTART
        // =========================

        g2.setFont(
                new Font("Arial", Font.PLAIN, 24)
        );

        String restartText =
                "Press ENTER to restart";

        int restartX =
                getCenteredTextX(g2, restartText);

        g2.drawString(
                restartText,
                restartX,
                420
        );
    }

    public int getCenteredTextX(
            Graphics2D g2,
            String text
    ){

        int textLength =
                (int) g2.getFontMetrics()
                        .getStringBounds(text, g2)
                        .getWidth();

        return screenWidth / 2 - textLength / 2;
    }

    public void drawEnergyBar(Graphics2D g2){

        int maxEnergy =
                plane.getMaxEnergy();

        int currentEnergy =
                plane.getCurrentEnergy();

        // =========================
        // BAR SETTINGS
        // =========================

        int barX = 20;
        int barY = 30;

        int barWidth = 200;
        int barHeight = 25;

        // =========================
        // BACKGROUND
        // =========================

        g2.setColor(Color.RED);

        g2.fillRect(
                barX,
                barY,
                barWidth,
                barHeight
        );

        // =========================
        // CURRENT ENERGY
        // =========================

        int currentWidth =
                (int)(
                        ((double) currentEnergy
                                / maxEnergy)
                                * barWidth
                );

        g2.setColor(Color.GREEN);

        g2.fillRect(
                barX,
                barY,
                currentWidth,
                barHeight
        );

        // =========================
        // BORDER
        // =========================

        g2.setColor(Color.WHITE);

        g2.drawRect(
                barX,
                barY,
                barWidth,
                barHeight
        );

        // =========================
        // TEXT
        // =========================

        g2.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        g2.drawString(
                "Energy",
                barX,
                barY - 10
        );
    }

    public void movementInput(KeyHandler keyH){

        int dx = 0;
        int dy = 0;

        if(keyH.upPressed){
            dy--;
        }

        if(keyH.downPressed){
            dy++;
        }

        if(keyH.leftPressed){
            dx--;
        }

        if(keyH.rightPressed){
            dx++;
        }

        plane.move(dx, dy);

        plane.update();
    }

    public void updateMissiles(){

        for(int i = 0; i < missiles.size(); i++){

            Missile missile = missiles.get(i);

            missile.update();

            explosionDamage(missile);

            if(removeMissile(missile, i)){

                i--;
            }
        }
    }

    public void explosionDamage(Missile missile){

        // =========================
        // DIRECT HIT
        // =========================

        if(!missile.isExploding()
                && missile.collidesWith(plane)){

            missile.triggerExplosion();

            missile.setDamageApplied(true);

            player.loseLife();

            playerNextLife();
        }

        // =========================
        // EXPLOSION DAMAGE
        // =========================

        if(missile.isExploding()
                && !missile.isDamageApplied()){

            int damage =
                    missile.getExplosion()
                            .calculateDamage(plane);

            plane.reduceEnergy(damage);

            player.calculateScore(damage);

            missile.setDamageApplied(true);

            if(plane.getCurrentEnergy() <= 0){

                player.loseLife();

                playerNextLife();
            }
        }
    }

    public boolean removeMissile(
            Missile missile,
            int index
    ){

        if(missile.isFinished()){

            missiles.remove(index);

            return true;
        }

        return false;
    }

    public void playerNextLife(){

        if(player.getLives() > 0){

            plane.restoreEnergy();

        } else {

            gameState = GameState.Game_Over;
        }
    }

    public void checkNextLevel(){

        if(squadron.levelFinished()
                && currentLevel.getLevelNumber() <= 5){

            currentLevel.nextLevel();

            player.addScore(300);

            startLevel();
        }
    }

    public GameState getGameState(){

        return gameState;
    }

    public void startNewGame(){

        player = new Player();

        currentLevel = new Level(1);

        plane.setDefaultValues();

        startLevel();

        gameState = GameState.Running;
    }
}