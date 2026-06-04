package gui;

import audio.SoundManager;
import game.GameState;
import game.Session;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 16;

    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    int FPS = 40;

    Image planeSkin;

    KeyHandler keyH = new KeyHandler();

    Timer gameTimer;

    Session session;

    SoundManager soundManager;

    public GamePanel(Image planeSkin){

        this.planeSkin = planeSkin;

        this.setPreferredSize(
                new Dimension(screenWidth, screenHeight)
        );

        this.setBackground(Color.black);

        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);

        this.setFocusable(true);

        this.soundManager = new SoundManager();

        session = new Session(
                screenWidth,
                screenHeight,
                tileSize,
                planeSkin,
                soundManager
        );
    }

    public void startGameTimer(){

        gameTimer = new Timer(1000 / FPS, e -> {update();repaint();}
        );

        gameTimer.start();
    }

    public void update(){

        if(session.getGameState() == GameState.Game_Over){

            if(keyH.consumeEnterPress()){

                session = new Session(
                        screenWidth,
                        screenHeight,
                        tileSize,
                        planeSkin,
                        soundManager
                );
            }

            return;
        }

        session.update(keyH);
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        session.draw(g);
    }
}