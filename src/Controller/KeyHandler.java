package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean upPressed,downPressed,leftPressed,rightPressed, enterPressed, escPressed;

    private boolean escConsumed = false;
    private boolean enterConsumed = false;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP     -> upPressed    = true;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN   -> downPressed  = true;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT   -> leftPressed  = true;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT  -> rightPressed = true;
            case KeyEvent.VK_ENTER                 -> enterPressed = true;
            case KeyEvent.VK_ESCAPE                -> escPressed   = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP     -> upPressed    = false;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN   -> downPressed  = false;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT   -> leftPressed  = false;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT  -> rightPressed = false;
            case KeyEvent.VK_ENTER                 -> { enterPressed = false; enterConsumed = false; }
            case KeyEvent.VK_ESCAPE                -> { escPressed   = false; escConsumed   = false; }
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

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public boolean isEscPressed() {
        return escPressed;
    }

    public boolean isEscConsumed() {
        return escConsumed;
    }

    public boolean isEnterConsumed() {
        return enterConsumed;
    }
}
