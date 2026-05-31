package gui;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    CardLayout cardLayout;

    JPanel mainPanel;

    public Menu(){

        setTitle("Sky Defense");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        cardLayout = new CardLayout();

        mainPanel = new JPanel(cardLayout);

        MenuPanel menuPanel = new MenuPanel(this);

        mainPanel.add(menuPanel, "MENU");

        add(mainPanel);

        pack();

        setLocationRelativeTo(null);

        setVisible(true);
    }

    public void startGame(Image planeSkin){

        GamePanel gamePanel =
                new GamePanel(planeSkin);

        mainPanel.add(gamePanel, "GAME");

        cardLayout.show(mainPanel, "GAME");

        gamePanel.requestFocusInWindow();

        gamePanel.startGameTimer();
    }
}