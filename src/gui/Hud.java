package gui;

import game.Level;
import game.Player;
import entity.Plane;

import java.awt.*;

public class Hud {

    public Hud(){

    }

    public void drawHUD(Graphics g, Player player, Level currentLevel, Plane plane){

        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        g2.setColor(Color.WHITE);

        drawEnergyBar(g2, plane);

        g2.drawString(
                "Lives: " + player.getLives(),
                20,
                80
        );

        g2.drawString(
                "Score: " + player.getScore(),
                20,
                120
        );

        g2.drawString(
                "Level: " + currentLevel.getLevelNumber(),
                20,
                160
        );
    }

    public void drawGameOverScreen(Graphics g, int screenHeight, int screenWidth, Player player){

        Graphics2D g2 = (Graphics2D) g;

        // =========================
        // BACKGROUND
        // =========================

        g2.setColor(Color.BLACK);

        g2.fillRect(
                0,
                0,
                screenWidth,
                screenHeight
        );

        // =========================
        // TITLE
        // =========================

        g2.setColor(Color.RED);

        g2.setFont(
                new Font("Arial", Font.BOLD, 64)
        );

        drawCenteredText(g2, "GAME OVER", 220, screenWidth);

        // =========================
        // SCORE
        // =========================

        g2.setColor(Color.WHITE);

        g2.setFont(
                new Font("Arial", Font.BOLD, 32)
        );

        drawCenteredText(g2, "Score: " + player.getScore(), 320, screenWidth);

        // =========================
        // RESTART
        // =========================

        g2.setFont(
                new Font("Arial", Font.PLAIN, 24)
        );

        drawCenteredText(g2, "Press ENTER to restart", 420, screenWidth);}

    public int getCenteredTextX(Graphics2D g2, String text, int screenWidth){

        int textLength =
                (int) g2.getFontMetrics()
                        .getStringBounds(text, g2)
                        .getWidth();

        return screenWidth / 2 - textLength / 2;
    }

    private void drawCenteredText(Graphics2D g2, String text, int y, int screenWidth){

        int x = getCenteredTextX(g2, text,screenWidth);

        g2.drawString(text, x, y);
    }

    public void drawEnergyBar(Graphics2D g2, Plane plane){

        int maxEnergy =
                plane.getMaxEnergy();

        int currentEnergy =
                plane.getCurrentEnergy();

        // =========================
        // BAR SETTINGS
        // =========================

        int barX = 20;
        int barY = 30;

        int barWidth = 200;
        int barHeight = 25;

        // =========================
        // BACKGROUND
        // =========================

        g2.setColor(Color.RED);

        g2.fillRect(
                barX,
                barY,
                barWidth,
                barHeight
        );

        // =========================
        // CURRENT ENERGY
        // =========================

        int currentWidth =
                (int)(
                        ((double) currentEnergy
                                / maxEnergy)
                                * barWidth
                );

        g2.setColor(Color.GREEN);

        g2.fillRect(
                barX,
                barY,
                currentWidth,
                barHeight
        );

        // =========================
        // BORDER
        // =========================

        g2.setColor(Color.WHITE);

        g2.drawRect(
                barX,
                barY,
                barWidth,
                barHeight
        );

        // =========================
        // TEXT
        // =========================

        g2.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        g2.drawString(
                "Energy",
                barX,
                barY - 10
        );
    }
}
