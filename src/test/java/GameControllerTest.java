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
        boolean up, down, left, right, esc, enter, mute;

        @Override
        public boolean isUpPressed() {
            return up;
        }

        @Override
        public boolean isDownPressed() {
            return down;
        }

        @Override
        public boolean isLeftPressed() {
            return left;
        }

        @Override
        public boolean isRightPressed() {
            return right;
        }

        @Override
        public boolean consumeEscPress() {
            boolean pressed = esc;
            esc = false;
            return pressed;
        }

        @Override
        public boolean consumeEnterPress() {
            boolean pressed = enter;
            enter = false;
            return pressed;
        }

        @Override
        public Boolean consumeMutePress() {
            boolean pressed = mute;
            mute = false;
            return pressed;
        }
    }

    static class FakeAudio implements AudioPlayer {
        boolean muted = false;

        @Override
        public void play(Sound sound) {
        }

        @Override
        public void playBackground(Sound sound) {
        }

        @Override
        public void stopBackground() {
        }

        @Override
        public void toggleMute() {
            muted = !muted;
        }

        @Override
        public boolean isMuted() {
            return muted;
        }
    }

    private GameController createRunningController(FakeInput input) {
        GameController controller = new GameController(input, new FakeAudio());

        input.enter = true;
        controller.update(0.016f);

        return controller;
    }

    @Test
    void newGame_startsInMenuWithInitialValues() {
        GameController controller = new GameController(new FakeInput(), new FakeAudio());

        assertEquals(GameState.MENU, controller.getGameState());
        assertEquals(3, controller.getPlayer().getLives());
        assertEquals(0, controller.getPlayer().getScore());
        assertEquals(1, controller.getCurrentLevel().getLevelNumber());
        assertTrue(controller.getMissiles().isEmpty());
    }

    @Test
    void update_enterPressFromMenu_startsGame() {
        FakeInput input = new FakeInput();
        GameController controller = new GameController(input, new FakeAudio());

        input.enter = true;
        controller.update(0.016f);

        assertEquals(GameState.RUNNING, controller.getGameState());
    }

    @Test
    void update_escPress_pausesGame() {
        FakeInput input = new FakeInput();
        GameController controller = createRunningController(input);

        input.esc = true;
        controller.update(0.016f);

        assertEquals(GameState.PAUSED, controller.getGameState());
    }

    @Test
    void update_escPressWhilePaused_resumesGame() {
        FakeInput input = new FakeInput();
        GameController controller = createRunningController(input);

        input.esc = true;
        controller.update(0.016f); // pausa

        input.esc = true;
        controller.update(0.016f); // reanuda

        assertEquals(GameState.RUNNING, controller.getGameState());
    }

    @Test
    void update_rightPressed_movesPlaneRight() {
        FakeInput input = new FakeInput();
        GameController controller = createRunningController(input);

        double startX = controller.getPlane().getX();

        input.right = true;
        controller.update(0.016f);

        assertTrue(controller.getPlane().getX() > startX);
    }

    @Test
    void update_whilePaused_planeDoesNotMove() {
        FakeInput input = new FakeInput();
        GameController controller = createRunningController(input);

        input.esc = true;
        controller.update(0.016f); // pausa

        double startX = controller.getPlane().getX();

        input.right = true;
        controller.update(0.016f);

        assertEquals(startX, controller.getPlane().getX());
    }

    @Test
    void update_mutePress_togglesAudioMute() {
        FakeInput input = new FakeInput();
        FakeAudio audio = new FakeAudio();
        GameController controller = new GameController(input, audio);

        input.mute = true;
        controller.update(0.016f);

        assertTrue(audio.isMuted());
    }
}