package main.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener, InputSource {

    private boolean upPressed,downPressed,leftPressed,rightPressed, enterPressed, escPressed;

    private boolean escConsumed = false;
    private boolean enterConsumed = false;

    /**
     * Handles typed key events.
     *
     * POST:
     * - No action is performed.
     *
     * @param e key event triggered by user input
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Handles key press events and updates input state.
     *
     * PRE:
     * - e != null.
     *
     * POST:
     * - Corresponding input flags are set to true based on key pressed.
     * - Movement keys (WASD / arrows) update directional state.
     * - Enter and Escape update action state.
     *
     * @param e key event triggered by user input
     */
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

    /**
     * Handles key release events and updates input state.
     *
     * PRE:
     * - e != null.
     *
     * POST:
     * - Corresponding input flags are set to false based on key released.
     * - Enter and Escape consumption flags are reset.
     *
     * @param e key event triggered by user input
     */
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

    /**
     * Consumes an Escape key press event if available.
     *
     * POST:
     * - Returns true only once per key press.
     * - Once consumed, repeated calls return false until key is released again.
     *
     * @return true if Escape press was consumed; false otherwise
     */
    public boolean consumeEscPress() {
        // consumed input avoids multiple triggers per key press
        if (escPressed && !escConsumed) {
            escConsumed = true;
            return true;
        }
        return false;
    }

    /**
     * Consumes an Enter key press event if available.
     *
     * POST:
     * - Returns true only once per key press.
     * - Once consumed, repeated calls return false until key is released again.
     *
     * @return true if Enter press was consumed; false otherwise
     */
    public boolean consumeEnterPress() {
        // consumed input avoids multiple triggers per key press
        if (enterPressed && !enterConsumed) {
            enterConsumed = true;
            return true;
        }
        return false;
    }

    /**
     * Getters.
     */
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
