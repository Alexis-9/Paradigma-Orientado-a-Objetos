package main.audio;

public enum Sound {
    SHOOT("/main/Sounds/droneShot.wav"),
    EXPLOSION("/main/Sounds/explosion.wav"),
    LOSE_LIFE("/main/Sounds/loseLife.wav"),
    NEXT_LEVEL("/main/Sounds/levelUp.wav"),
    BACKGROUND("/main/Sounds/background.wav"),
    GAME_OVER("/main/Sounds/gameOver.wav");

    private final String path;

    Sound(String path){
        this.path = path;
    }

    public String getPath(){return path;}
}