package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel
        implements Runnable, MouseListener, MouseMotionListener {

    Menu menu;

    Thread menuThread;

    Image logo;

    Image[] planeSkins;

    int currentSkin = 0;

    Rectangle playButtonBounds;

    Rectangle leftArrowBounds;

    Rectangle rightArrowBounds;

    boolean hoverPlay;

    int glowAlpha = 120;

    boolean increasing = true;

    public MenuPanel(Menu menu){

        this.menu = menu;

        setPreferredSize(new Dimension(768,768));

        setBackground(new Color(5,8,15));

        setFocusable(true);

        // =========================
        // LOAD IMAGES
        // =========================

        logo = new ImageIcon(
                getClass().getResource("/Images/logo.png")
        ).getImage();

        planeSkins = new Image[]{

                new ImageIcon(
                        getClass().getResource("/Images/Plane/Skins/DefaultSkin.png")
                ).getImage(),

                new ImageIcon(
                        getClass().getResource("/Images/Plane/Skins/SkinPortugal.png")
                ).getImage(),

                new ImageIcon(
                        getClass().getResource("/Images/Plane/Skins/SkinBrazil.png")
                ).getImage(),

                new ImageIcon(
                        getClass().getResource("/Images/Plane/Skins/SkinGermany.png")
                ).getImage(),

                new ImageIcon(
                        getClass().getResource("/Images/Plane/Skins/SkinFrance.png")
                ).getImage(),

                new ImageIcon(
                        getClass().getResource("/Images/Plane/Skins/SkinArgentina.png")
                ).getImage()
        };

        // =========================
        // BUTTONS
        // =========================

        playButtonBounds = new Rectangle(
                260,
                310,
                250,
                65
        );

        leftArrowBounds = new Rectangle(
                220,
                520,
                50,
                50
        );

        rightArrowBounds = new Rectangle(
                500,
                520,
                50,
                50
        );

        // =========================
        // INPUT
        // =========================

        addMouseListener(this);

        addMouseMotionListener(this);

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if(e.getKeyCode() == KeyEvent.VK_ENTER){

                    menu.startGame(
                            planeSkins[currentSkin]
                    );
                }

                if(e.getKeyCode() == KeyEvent.VK_LEFT){

                    previousSkin();
                }

                if(e.getKeyCode() == KeyEvent.VK_RIGHT){

                    nextSkin();
                }
            }
        });

        startMenuThread();
    }

    public void startMenuThread(){

        menuThread = new Thread(this);

        menuThread.start();
    }

    @Override
    public void run() {

        while(menuThread != null){

            update();

            repaint();

            try{

                Thread.sleep(16);

            } catch(Exception e){

                e.printStackTrace();
            }
        }
    }

    public void update(){

        // =========================
        // GLOW
        // =========================

        if(increasing){

            glowAlpha += 2;

            if(glowAlpha >= 180){

                increasing = false;
            }

        } else {

            glowAlpha -= 2;

            if(glowAlpha <= 90){

                increasing = true;
            }
        }
    }

    public void previousSkin(){

        currentSkin--;

        if(currentSkin < 0){

            currentSkin = planeSkins.length - 1;
        }
    }

    public void nextSkin(){

        currentSkin++;

        if(currentSkin >= planeSkins.length){

            currentSkin = 0;
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

        // =========================
        // BACKGROUND
        // =========================

        GradientPaint gradient = new GradientPaint(
                0,
                0,
                new Color(5,8,15),
                0,
                getHeight(),
                new Color(0,0,0)
        );

        g2.setPaint(gradient);

        g2.fillRect(0,0,getWidth(),getHeight());

        // =========================
        // SOFT LIGHTS
        // =========================

        g2.setColor(new Color(0,120,255,30));

        g2.fillOval(
                -100,
                -50,
                350,
                350
        );

        g2.fillOval(
                500,
                100,
                300,
                300
        );

        // =========================
        // TITLE
        // =========================

        g2.setFont(
                new Font("Arial", Font.BOLD, 54)
        );

        g2.setColor(Color.WHITE);

        drawCenteredString(
                g2,
                "SKY DEFENSE",
                90
        );

        // =========================
        // LOGO
        // =========================

        g2.drawImage(
                logo,
                180,
                120,
                400,
                120,
                null
        );

        // =========================
        // PLAY BUTTON SHADOW
        // =========================

        g2.setColor(new Color(0,0,0,120));

        g2.fillRoundRect(
                265,
                315,
                250,
                65,
                30,
                30
        );

        // =========================
        // PLAY BUTTON
        // =========================

        if(hoverPlay){

            g2.setColor(new Color(0,200,255));

        } else {

            g2.setColor(new Color(25,35,55));
        }

        g2.fillRoundRect(
                260,
                310,
                250,
                65,
                30,
                30
        );

        // BORDER

        g2.setStroke(new BasicStroke(2));

        g2.setColor(new Color(0,220,255));

        g2.drawRoundRect(
                260,
                310,
                250,
                65,
                30,
                30
        );

        // PLAY TEXT

        g2.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        g2.setColor(Color.WHITE);

        drawCenteredString(
                g2,
                "PLAY",
                352
        );

        // =========================
        // SELECT SHIP
        // =========================

        g2.setFont(
                new Font("Arial", Font.PLAIN, 20)
        );

        g2.setColor(new Color(170,190,220));

        drawCenteredString(
                g2,
                "Select Aircraft",
                450
        );

        // =========================
        // PLANE GLOW
        // =========================

        g2.setColor(new Color(0,140,255,glowAlpha));

        g2.fillOval(
                260,
                500,
                250,
                80
        );

        // =========================
        // LEFT ARROW
        // =========================

        g2.setFont(
                new Font("Arial", Font.BOLD, 40)
        );

        g2.setColor(new Color(180,220,255));

        g2.drawString(
                "‹",
                230,
                555
        );

        // =========================
        // RIGHT ARROW
        // =========================

        g2.drawString(
                "›",
                515,
                555
        );

        // =========================
        // PLANE
        // =========================

        g2.drawImage(
                planeSkins[currentSkin],
                305,
                450,
                160,
                160,
                null
        );

        // =========================
        // BOTTOM TEXT
        // =========================

        g2.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        g2.setColor(new Color(120,140,170));

        drawCenteredString(
                g2,
                "Press ENTER to start",
                690
        );
    }

    public void drawCenteredString(
            Graphics2D g2,
            String text,
            int y
    ){

        FontMetrics metrics = g2.getFontMetrics();

        int x =
                (getWidth() - metrics.stringWidth(text)) / 2;

        g2.drawString(text, x, y);
    }

    // =========================
    // MOUSE
    // =========================

    @Override
    public void mouseClicked(MouseEvent e) {

        Point p = e.getPoint();

        if(playButtonBounds.contains(p)){

            menu.startGame(
                    planeSkins[currentSkin]
            );
        }

        if(leftArrowBounds.contains(p)){

            previousSkin();

            repaint();
        }

        if(rightArrowBounds.contains(p)){

            nextSkin();

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        hoverPlay =
                playButtonBounds.contains(e.getPoint());

        repaint();
    }

    // =========================
    // UNUSED
    // =========================

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}
}