package main.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager implements AudioPlayer {

    private Clip backgroundClip;
    private boolean muted = false;

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
     * - If audio is muted, no sound is played.
     *
     * @param sound sound identifier to play
     */
    @Override
    public void play(Sound sound) {
        if (muted) {
            return;
        }

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
     * - If audio is muted, no background music is played.
     *
     * @param sound background sound identifier
     */
    @Override
    public void playBackground(Sound sound) {
        stopBackground();

        if (muted) {
            return;
        }

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
     * Toggles the mute state of the audio system.
     *
     * PRE:
     * - Audio manager is initialized.
     *
     * POST:
     * - If audio was muted, it becomes unmuted.
     * - If audio was unmuted, it becomes muted.
     * - If audio becomes muted, the current background music is stopped.
     * - No sound effect is played by this method.
     */
    @Override
    public void toggleMute() {
        muted = !muted;

        if (muted) {
            stopBackground();
        }
    }

    /**
     * Returns whether the audio system is currently muted.
     *
     * PRE:
     * - Audio manager is initialized.
     *
     * POST:
     * - Returns true if audio is muted.
     * - Returns false if audio is enabled.
     * - No audio state is modified.
     *
     * @return true if the audio system is muted; false otherwise
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