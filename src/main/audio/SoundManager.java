package main.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager implements AudioPlayer {

    private Clip backgroundClip;

    /**
     * Plays a short sound effect once.
     *
     * PRE:
     * - sound != null.
     *
     * POST:
     * - The corresponding main.audio clip is loaded and played once.
     * - The clip is automatically closed after finishing.
     * - If the main.audio resource is not found, no action is performed.
     *
     * @param sound sound identifier to play
     */
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

    /**
     * Plays a looping background sound, replacing any previous background main.audio.
     *
     * PRE:
     * - sound != null.
     *
     * POST:
     * - Previous background music is stopped and closed.
     * - New background clip is loaded and started in loop mode.
     * - The clip remains active until explicitly stopped.
     *
     * @param sound background sound identifier
     */
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

    /**
     * Stops and releases the current background music if active.
     *
     * POST:
     * - Background clip is stopped if running.
     * - Audio resources are released.
     * - backgroundClip is left in a non-playing state.
     */
    @Override
    public void stopBackground() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }

    /**
     * Resolves the file path associated with a sound enum value.
     *
     * PRE:
     * - sound != null.
     *
     * POST:
     * - Returns a valid resource path string for the given sound.
     *
     * @param sound sound identifier
     * @return resource path of the main.audio file
     */
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