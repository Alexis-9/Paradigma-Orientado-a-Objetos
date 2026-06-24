package main.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager implements AudioPlayer {

    private Clip backgroundClip;
    private boolean muted = false;
    private Sound currentBackground;

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
        if (muted) return;
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
        this.currentBackground = sound;
        if (muted) return;
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
     * Sets the muted state of the audio system.
     *
     * POST:
     * - If muted, the background music is stopped.
     * - If unmuted, the last background track is resumed (if any).
     * - New sound effects are suppressed while muted.
     *
     * @param muted true to silence audio, false to restore it.
     */
    @Override
    public void setMuted(boolean muted) {
        this.muted = muted;
        if (muted) {
            stopBackground();
        } else if (currentBackground != null) {
            playBackground(currentBackground);
        }
    }

    /**
     * Returns whether the audio system is currently muted.
     *
     * POST:
     * - Returns the current muted state.
     * - No state is modified.
     *
     * @return true if muted; false otherwise.
     */
    @Override
    public boolean isMuted() {
        return muted;
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
        return sound.getPath();
    }
}