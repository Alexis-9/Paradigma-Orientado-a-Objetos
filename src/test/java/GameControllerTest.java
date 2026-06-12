package test.java;

import main.Controller.GameController;
import main.Controller.InputSource;
import main.Model.GameState;
import main.audio.AudioPlayer;
import main.audio.Sound;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    // Fakes simples para no depender del teclado ni del audio real.
    static class FakeInput implements InputSource {
        boolean up, down, left, right, esc, enter;

        public boolean isUpPressed() { return up; }
        public boolean isDownPressed() { return down; }
        public boolean isLeftPressed() { return left; }
        public boolean isRightPressed() { return right; }

        public boolean consumeEscPress() {
            boolean pressed = esc;
            esc = false;
            return pressed;
        }

        public boolean consumeEnterPress() {
            boolean pressed = enter;
            enter = false;
            return pressed;
        }
    }

    static class FakeAudio implements AudioPlayer {
        public void play(Sound sound) {}
        public void playBackground(Sound sound) {}
        public void stopBackground() {}
    }

    @Test
    void newGame_startsWithInitialValues() {
        GameController controller = new GameController(new FakeInput(), new FakeAudio());

        assertEquals(GameState.RUNNING, controller.getGameState());
        assertEquals(3, controller.getPlayer().getLives());
        assertEquals(0, controller.getPlayer().getScore());
        assertEquals(1, controller.getCurrentLevel().getLevelNumber());
        assertTrue(controller.getMissiles().isEmpty());
    }

    @Test
    void update_escPress_pausesGame() {
        FakeInput input = new FakeInput();
        GameController controller = new GameController(input, new FakeAudio());

        input.esc = true;
        controller.update(0.016f);

        assertEquals(GameState.PAUSED, controller.getGameState());
    }

    @Test
    void update_escPressWhilePaused_resumesGame() {
        FakeInput input = new FakeInput();
        GameController controller = new GameController(input, new FakeAudio());

        input.esc = true;
        controller.update(0.016f); // pausa

        input.esc = true;
        controller.update(0.016f); // reanuda

        assertEquals(GameState.RUNNING, controller.getGameState());
    }

    @Test
    void update_rightPressed_movesPlaneRight() {
        FakeInput input = new FakeInput();
        GameController controller = new GameController(input, new FakeAudio());
        double startX = controller.getPlane().getX();

        input.right = true;
        controller.update(0.016f);

        assertTrue(controller.getPlane().getX() > startX);
    }

    @Test
    void update_whilePaused_planeDoesNotMove() {
        FakeInput input = new FakeInput();
        GameController controller = new GameController(input, new FakeAudio());

        input.esc = true;
        controller.update(0.016f); // pausa

        double startX = controller.getPlane().getX();
        input.right = true;
        controller.update(0.016f);

        assertEquals(startX, controller.getPlane().getX());
    }
}