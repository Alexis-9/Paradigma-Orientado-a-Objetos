package Controller;

import javax.swing.*;
import Model.Plane;
import Model.GameState;



public class GameController extends JPanel {

    GameState gameState = GameState.MENU;

    Timer gameTimer;

    int FPS = 60;

    float delta = 1f / FPS;

    Plane plane;

    KeyHandler keyHandler = new KeyHandler();

    public GameController(){

    }



    public void init(){
        gameState = GameState.RUNNING;
        startGameTimer();
    }

    public void update(float delta) {
        plane.update(delta);

    }

    public void deInit(){
        if (gameTimer != null) gameTimer.stop();
        gameState = GameState.MENU;
    }


    public void startGameTimer(){

        gameTimer = new Timer(1000 / FPS, e -> {update(delta);repaint();}
        );

        gameTimer.start();
    }

    public void pause() {
        if (gameTimer != null) gameTimer.stop();
        gameState = GameState.PAUSED;
    }

    public void resume() {
        gameState = GameState.RUNNING;
        if (gameTimer != null) gameTimer.start();
    }


    public void planeValidateBounds() {

        if (plane.getX() + planeWidth >= screenWidth) plane.setX(screenWidth - planeWidth);

        if (plane.getX() <= 0) plane.setX(0);

        if (plane.getY() + planeHeight >= screenHeight) plane.setY(screenHeight - planeHeight);

        if (plane.getY() <= topBound) plane.setY(topBound);
    }

    public void movementInput(KeyHandler keyHandler){
        int dx = 0;
        int dy = 0;

        if(keyHandler.upPressed){
            dy--;
        }

        if(keyHandler.downPressed){
            dy++;
        }

        if(keyHandler.leftPressed){
            dx--;
        }

        if(keyHandler.rightPressed){
            dx++;
        }

        plane.move(dx, dy, delta);
        planeValidateBounds();
        plane.update(delta);
    }



}
