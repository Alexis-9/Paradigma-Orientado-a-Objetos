package game;

import audio.SoundManager;
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

    private final SoundManager soundManager;

    int screenWidth;
    int screenHeight;

    public Session(
            int screenWidth,
            int screenHeight,
            int tileSize,
            Image planeSkin,
            SoundManager soundManager
    ){

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.soundManager = soundManager;

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

        soundManager.playBackground(SoundManager.Sound.BACKGROUND);
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

            Hud.drawGameOverScreen(g, screenHeight, screenWidth, player);

            return;
        }

        if(gameState == GameState.Paused){

            plane.draw(g);

            squadron.draw(g);

            for(Missile missile : missiles){
                missile.draw(g);
            }

            Hud.drawHUD(g, player, currentLevel, plane);

            Hud.drawPauseScreen(g, screenWidth, screenHeight);

            return;
        }

        plane.draw(g);

        squadron.draw(g);

        for(Missile missile : missiles){

            missile.draw(g);
        }

        Hud.drawHUD(g, player, currentLevel, plane);
    }

    public void update(KeyHandler keyH){

        handlePauseInput(keyH);

        if(gameState == GameState.Paused) return;

        movementInput(keyH);

        squadron.update();

        updateDroneShots();

        updateMissiles();

        checkNextLevel();
    }

    private void handlePauseInput(KeyHandler keyH){
        if(keyH.consumeEscPress()){
            togglePause();
        }
    }

    public void updateDroneShots(){
        for(Drone drone : squadron.getDrones()){

            Missile missile = drone.tryShoot(
                    currentLevel.getMissileSpeed(),
                    screenHeight
            );

            if (missile != null){
                soundManager.play(SoundManager.Sound.SHOOT);
                missiles.add(missile);
            }
        }
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

            soundManager.play(SoundManager.Sound.EXPLOSION);

            missile.triggerExplosion();

            missile.setDamageApplied(true);

            player.loseLife();

            playerNextLife();
        }

        // =========================
        // EXPLOSION DAMAGE
        // =========================

        if(missile.isExploding() && !missile.isDamageApplied()){

            Explosion explosion = missile.getExplosion();
            double distance = explosion.calculateDistance(plane);
            int damage = explosion.calculateDamage(plane);

            soundManager.play(SoundManager.Sound.EXPLOSION);

            plane.reduceEnergy(damage);

            player.addScoreByDistance(distance);
            player.checkExtraLife(currentLevel.getLevelNumber());

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

            soundManager.play(SoundManager.Sound.LOSE_LIFE);
            plane.restoreEnergy();

        } else {

            soundManager.stopBackground();
            soundManager.play(SoundManager.Sound.GAME_OVER);
            gameState = GameState.Game_Over;
        }
    }

    public void checkNextLevel(){

        if(currentLevel.levelFinished(squadron) && missiles.isEmpty()){
            soundManager.play(SoundManager.Sound.NEXT_LEVEL);
            currentLevel.nextLevel();
            player.addScore(300);
            player.checkExtraLife(currentLevel.getLevelNumber());
            startLevel();
        }
    }

    public GameState getGameState(){return gameState;}

    public void togglePause(){

        if(gameState == GameState.Running){
            gameState = GameState.Paused;
        } else if (gameState == GameState.Paused) {
            gameState = GameState.Running;
        }
    }

}