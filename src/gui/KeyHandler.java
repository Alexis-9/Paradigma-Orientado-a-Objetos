package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed,downPressed,leftPressed,rightPressed, enterPressed, escPressed;

    private boolean escConsumed = false;
    private boolean enterConsumed = false;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = true;
        }
        else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        } else if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }else if (code == KeyEvent.VK_ESCAPE){
            escPressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
        }
        else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        else if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
            enterConsumed = false;
        }
        else if (code == KeyEvent.VK_ESCAPE) {
            escPressed = false;
            escConsumed = false;
        }

    }

    public boolean consumeEscPress() {
        if (escPressed && !escConsumed) {
            escConsumed = true;
            return true;
        }
        return false;
    }

    public boolean consumeEnterPress() {
        if (enterPressed && !enterConsumed) {
            enterConsumed = true;
            return true;
        }
        return false;
    }
}
