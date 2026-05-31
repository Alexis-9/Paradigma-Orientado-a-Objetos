package game;

import entity.Drone;
import entity.Missile;
import entity.Plane;
import gui.KeyHandler;
import gui.Hud;

import java.awt.*;
import java.util.ArrayList;

public class Session {

    GameState gameState;

    Player player;

    Plane plane;

    Squadron squadron;


    ArrayList<Missile> missiles;

    Level currentLevel;

    Hud hud;

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
        hud = new Hud();

        gameState = GameState.Running;
    }

    public void startLevel(){

        squadron = new Squadron(
                screenWidth,
                currentLevel
        );

        missiles.clear();
    }

    public void draw(Graphics g){

        if(gameState == GameState.Game_Over){

            hud.drawGameOverScreen(g, screenHeight, screenWidth, player);

            return;
        }

        plane.draw(g);

        squadron.draw(g);

        for(Missile missile : missiles){

            missile.draw(g);
        }

        hud.drawHUD(g, player, currentLevel, plane);
    }

    public void update(KeyHandler keyH){

        movementInput(keyH);

        squadron.update();

        // =========================
        // DRONES SHOOT deberia ir dentro de drone
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

    public boolean removeMissile(Missile missile, int index){
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

        if(currentLevel.levelFinished(squadron) && missiles.isEmpty()){

            currentLevel.nextLevel();
            player.addScore(300);
            startLevel();

        }
    }

    public GameState getGameState(){return gameState;}


}