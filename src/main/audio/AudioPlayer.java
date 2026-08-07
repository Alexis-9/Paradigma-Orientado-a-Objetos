package main.audio;

public interface AudioPlayer {
    void play(Sound sound);
    void playBackground(Sound sound);
    void stopBackground();
    void toggleMute();
    boolean isMuted();
}