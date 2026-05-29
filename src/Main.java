/*import gui.GamePanel;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Sky Defense");

        Image image = new ImageIcon(Main.class.getResource("/Images/logo.png")).getImage();
        window.setIconImage(image);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();


    }
}*/

import gui.Menu;

public class Main {

    public static void main(String[] args) {

        new Menu();
    }
}