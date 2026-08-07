package main.View;

import java.awt.*;

public class MenuView {

    private final int screenWidth;
    private final int screenHeight;

    public MenuView(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void render(Graphics g, boolean audioMuted) {
        drawBackground(g);
        drawTitle(g);
        drawPlayText(g);
        drawSoundStatus(g, audioMuted);
    }

    private void drawSoundStatus(Graphics g, boolean audioMuted) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));

        String soundText = audioMuted ? "[M] SOUND: OFF" : "[M] SOUND: ON";
        g.drawString(soundText, getCenteredTextX(g, soundText), 500);
    }

    private void drawBackground(Graphics g) {
        g.setColor(new Color(10, 20, 40));
        g.fillRect(0, 0, screenWidth, screenHeight);
    }

    private void drawTitle(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 52));

        String title = "SKY DEFENSE";
        g.drawString(title, getCenteredTextX(g, title), 260);
    }

    private void drawPlayText(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 26));

        String text = "PRESIONE ENTER PARA JUGAR";
        g.drawString(text, getCenteredTextX(g, text), 390);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        String controls = "← → moverse   |   ↑ ↓ altitud";
        g.drawString(controls, getCenteredTextX(g, controls), 440);
    }

    private int getCenteredTextX(Graphics g, String text) {
        FontMetrics metrics = g.getFontMetrics();
        return (screenWidth - metrics.stringWidth(text)) / 2;
    }
}