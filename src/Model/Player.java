package Model;

public class Player {

    private int lives;
    private int score;
    private int nextLife = 1000;

    public Player(){
        this.lives = 3;
        this.score = 0;
    }

    public void addScore(int scoreAmount){
        if (scoreAmount < 0) return;
        score += scoreAmount; }

    public void loseLife(){ if (lives > 0) lives--; }

    public void addLife(){ lives++; }

    public int getScore(){ return score; }


    public void checkExtraLife(int levelNumber){
        while (score >= nextLife){
            if (levelNumber < 5) addLife();
            nextLife += 1000;
        }
    }

    public int getLives() { return lives; }
}