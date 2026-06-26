package main;

import main.Controller.GamePanel;

import javax.swing.*;
import java.awt.*;

public class Main {


    /**
     * Starts the game and graphics.
     * @param args
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame("Sky Defense");
        frame.add(new GamePanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("main/Images/Plane/Skins/DefaultSkin.png").getImage());
        frame.setVisible(true);

    }

}
