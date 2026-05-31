package gui;

import game.GameState;
import game.Session;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 16;

    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;

    Image planeSkin;

    KeyHandler keyH = new KeyHandler();

    Thread gameThread;

    Session session;

    public GamePanel(Image planeSkin){

        this.planeSkin = planeSkin;

        this.setPreferredSize(
                new Dimension(screenWidth, screenHeight)
        );

        this.setBackground(Color.black);

        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);

        this.setFocusable(true);

        session = new Session(
                screenWidth,
                screenHeight,
                tileSize,
                planeSkin
        );
    }

    public void startGameThread(){

        gameThread = new Thread(this);

        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;

        double delta = 0;

        long lastTime = System.nanoTime();

        long currentTime;

        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1){

                update();

                repaint();

                delta--;
            }
        }
    }

    public void update(){

        if(session.getGameState() == GameState.Game_Over){

            if(keyH.enterPressed){

                session.startNewGame();
            }

            return;
        }

        session.update(keyH);
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        session.draw(g);
    }
}