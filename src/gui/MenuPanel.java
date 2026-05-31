package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel
        implements MouseListener, MouseMotionListener {

    private final Menu menu;

    private final Image logo;

    private final Image[] planeSkins;

    private int currentSkin = 0;

    private boolean hoverPlay = false;

    private final Rectangle playButton =
            new Rectangle(260, 320, 250, 70);

    private final Rectangle leftArrow =
            new Rectangle(220, 510, 60, 60);

    private final Rectangle rightArrow =
            new Rectangle(490, 510, 60, 60);

    private final Timer animationTimer;

    private int glowAlpha = 100;
    private boolean glowIncreasing = true;

    public MenuPanel(Menu menu){

        this.menu = menu;

        setPreferredSize(new Dimension(768,768));
        setBackground(Color.BLACK);
        setFocusable(true);

        logo = loadImage("/Images/logo.png");

        planeSkins = new Image[]{

                loadImage("/Images/Plane/Skins/DefaultSkin.png"),
                loadImage("/Images/Plane/Skins/SkinPortugal.png"),
                loadImage("/Images/Plane/Skins/SkinBrazil.png"),
                loadImage("/Images/Plane/Skins/SkinGermany.png"),
                loadImage("/Images/Plane/Skins/SkinFrance.png"),
                loadImage("/Images/Plane/Skins/SkinArgentina.png")
        };

        addMouseListener(this);
        addMouseMotionListener(this);

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                switch(e.getKeyCode()){

                    case KeyEvent.VK_ENTER:
                        startGame();
                        break;

                    case KeyEvent.VK_LEFT:
                        previousSkin();
                        break;

                    case KeyEvent.VK_RIGHT:
                        nextSkin();
                        break;
                }
            }
        });

        animationTimer = new Timer(16, e -> {

            animateGlow();

            repaint();
        });

        animationTimer.start();
    }

    private Image loadImage(String path){

        return new ImageIcon(
                getClass().getResource(path)
        ).getImage();
    }

    private void animateGlow(){

        if(glowIncreasing){

            glowAlpha += 2;

            if(glowAlpha >= 180){
                glowIncreasing = false;
            }

        } else {

            glowAlpha -= 2;

            if(glowAlpha <= 80){
                glowIncreasing = true;
            }
        }
    }

    private void startGame(){

        animationTimer.stop();

        menu.startGame(
                planeSkins[currentSkin]
        );
    }

    private void nextSkin(){

        currentSkin++;

        if(currentSkin >= planeSkins.length){
            currentSkin = 0;
        }
    }

    private void previousSkin(){

        currentSkin--;

        if(currentSkin < 0){
            currentSkin = planeSkins.length - 1;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        drawBackground(g2);
        drawLogo(g2);
        drawPlayButton(g2);
        drawSkinSelector(g2);
        drawFooter(g2);
    }

    private void drawBackground(Graphics2D g2){

        GradientPaint gradient =
                new GradientPaint(
                        0,
                        0,
                        new Color(8,12,25),
                        0,
                        getHeight(),
                        new Color(0,0,0)
                );

        g2.setPaint(gradient);

        g2.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
        );

        g2.setColor(
                new Color(0,150,255,30)
        );

        g2.fillOval(
                -120,
                -80,
                350,
                350
        );

        g2.fillOval(
                520,
                80,
                300,
                300
        );
    }

    private void drawLogo(Graphics2D g2){

        g2.drawImage(
                logo,
                170,
                90,
                430,
                140,
                null
        );

        g2.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        g2.setColor(
                new Color(170,190,220)
        );

        drawCentered(
                g2,
                "WORLD CUP EDITION",
                250
        );
    }

    private void drawPlayButton(Graphics2D g2){

        g2.setColor(
                new Color(0,0,0,120)
        );

        g2.fillRoundRect(
                265,
                325,
                250,
                70,
                30,
                30
        );

        if(hoverPlay){

            g2.setColor(
                    new Color(0,180,255)
            );

        } else {

            g2.setColor(
                    new Color(30,40,65)
            );
        }

        g2.fillRoundRect(
                260,
                320,
                250,
                70,
                30,
                30
        );

        g2.setColor(
                new Color(0,220,255)
        );

        g2.setStroke(
                new BasicStroke(2)
        );

        g2.drawRoundRect(
                260,
                320,
                250,
                70,
                30,
                30
        );

        g2.setColor(Color.WHITE);

        g2.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        drawCentered(
                g2,
                "PLAY",
                365
        );
    }

    private void drawSkinSelector(Graphics2D g2){

        g2.setColor(
                new Color(180,200,230)
        );

        g2.setFont(
                new Font("Arial", Font.PLAIN, 22)
        );

        drawCentered(
                g2,
                "SELECT AIRCRAFT",
                460
        );

        g2.setColor(
                new Color(0,150,255,glowAlpha)
        );

        g2.fillOval(
                250,
                500,
                270,
                90
        );

        g2.drawImage(
                planeSkins[currentSkin],
                304,
                450,
                160,
                160,
                null
        );

        g2.setFont(
                new Font("Arial", Font.BOLD, 40)
        );

        g2.setColor(
                new Color(200,220,255)
        );

        g2.drawString(
                "‹",
                235,
                550
        );

        g2.drawString(
                "›",
                520,
                550
        );
    }

    private void drawFooter(Graphics2D g2){

        g2.setColor(
                new Color(130,150,180)
        );

        g2.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        drawCentered(
                g2,
                "Press ENTER to Start",
                700
        );
    }

    private void drawCentered(
            Graphics2D g2,
            String text,
            int y){

        FontMetrics metrics =
                g2.getFontMetrics();

        int x =
                (getWidth()
                        - metrics.stringWidth(text))
                        / 2;

        g2.drawString(text,x,y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Point p = e.getPoint();

        if(playButton.contains(p)){
            startGame();
        }

        if(leftArrow.contains(p)){
            previousSkin();
        }

        if(rightArrow.contains(p)){
            nextSkin();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        hoverPlay =
                playButton.contains(
                        e.getPoint()
                );

        repaint();
    }

    @Override public void mousePressed(MouseEvent e){}
    @Override public void mouseReleased(MouseEvent e){}
    @Override public void mouseEntered(MouseEvent e){}
    @Override public void mouseExited(MouseEvent e){}
    @Override public void mouseDragged(MouseEvent e){}
}