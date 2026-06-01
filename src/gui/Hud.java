package gui;

import game.Level;
import game.Player;
import entity.Plane;

import java.awt.*;

public class Hud {

    private Hud(){}

    public static void drawHUD(Graphics g, Player player, Level currentLevel, Plane plane){

        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(new Font("Arial", Font.BOLD, 24));

        g2.setColor(Color.WHITE);

        drawEnergyBar(g2, plane);

        g2.drawString("Lives: " + player.getLives(), 20, 80);

        g2.drawString("Score: " + player.getScore(), 20, 120);

        g2.drawString("Level: " + currentLevel.getLevelNumber(), 20, 160);

        g2.drawString("Altitude: " + (int)plane.getY(), 20, 200);
    }

    public static void drawGameOverScreen(Graphics g, int screenHeight, int screenWidth, Player player){

        Graphics2D g2 = (Graphics2D) g;

        //Background
        g2.setColor(Color.BLACK);

        g2.fillRect(0, 0, screenWidth, screenHeight);

        //Title
        g2.setColor(Color.RED);

        g2.setFont(new Font("Arial", Font.BOLD, 64));

        drawCenteredText(g2, "GAME OVER", 220, screenWidth);

        //Score
        g2.setColor(Color.WHITE);

        g2.setFont(new Font("Arial", Font.BOLD, 32));

        drawCenteredText(g2, "Score: " + player.getScore(), 320, screenWidth);

        //Restart
        g2.setFont(new Font("Arial", Font.PLAIN, 24));

        drawCenteredText(g2, "Press ENTER to restart", 420, screenWidth);}


    public static int getCenteredTextX(Graphics2D g2, String text, int screenWidth){

        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        return screenWidth / 2 - textLength / 2;
    }

    private static void drawCenteredText(Graphics2D g2, String text, int y, int screenWidth){

        int x = getCenteredTextX(g2, text,screenWidth);

        g2.drawString(text, x, y);
    }


    public static void drawEnergyBar(Graphics2D g2, Plane plane){

        int maxEnergy = plane.getMaxEnergy();

        int currentEnergy = plane.getCurrentEnergy();

        //Default Values
        int barX = 20;
        int barY = 30;

        int barWidth = 200;
        int barHeight = 25;

        // Background Bar
        g2.setColor(Color.RED);

        g2.fillRect(barX, barY, barWidth, barHeight);

        //Current Energy Bar
        int currentWidth = (int)(((double) currentEnergy / maxEnergy) * barWidth);

        g2.setColor(Color.GREEN);

        g2.fillRect(barX, barY, currentWidth, barHeight);

        //Border
        g2.setColor(Color.WHITE);

        g2.drawRect(barX, barY, barWidth, barHeight);

        //Energy Text
        g2.setFont(new Font("Arial", Font.BOLD, 18));

        g2.drawString("Energy", barX, barY - 15);
    }
}
