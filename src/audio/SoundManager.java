package audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager implements AudioPlayer {

    private Clip backgroundClip;

    @Override
    public void play(Sound sound) {
        String path = resolvePath(sound);
        try {
            URL url = getClass().getResource(path);
            if (url == null) return;

            AudioInputStream stream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.open(stream);
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playBackground(Sound sound) {
        stopBackground();
        String path = resolvePath(sound);
        try {
            URL url = getClass().getResource(path);
            if (url == null) return;

            AudioInputStream stream = AudioSystem.getAudioInputStream(url);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(stream);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopBackground() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }

    private String resolvePath(Sound sound) {
        return switch (sound) {
            case SHOOT      -> "/Sounds/droneShot.wav";
            case EXPLOSION  -> "/Sounds/explosion.wav";
            case LOSE_LIFE  -> "/Sounds/loseLife.wav";
            case NEXT_LEVEL -> "/Sounds/levelUp.wav";
            case BACKGROUND -> "/Sounds/background.wav";
            case GAME_OVER  -> "/Sounds/gameOver.wav";
        };
    }
}