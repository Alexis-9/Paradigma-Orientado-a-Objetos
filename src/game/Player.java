package game;

public class Player {
    int lives;
    int score;
    int nextLife = 1000;

    public Player(){
        this.lives = 3;
        this.score = 0;
    }

    public void addScore(int scoreAmount, boolean isInfinite){
        score += scoreAmount;
        checkExtraLife(isInfinite);
    }

    public void loseLife(){
        lives--;
    }

    public void addLife(){
        lives++;
    }

    public int getScore(){
        return score;
    }

    public void calculateScore(int distance, boolean isInfinite){
        int scoreAmount = 0;
        if (distance==0){
            scoreAmount = 40;
        }
        else if (distance==20){
            scoreAmount = 20;
        }

        addScore(scoreAmount, isInfinite);
    }

    public void checkExtraLife(boolean isInfinite){
        if (score>=nextLife && !isInfinite){
            addLife();
            nextLife += 1000;
        }
    }

    public int getLives() {
        return lives;
    }
}
