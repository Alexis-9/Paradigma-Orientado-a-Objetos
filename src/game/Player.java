package game;

public class Player {
    int lives;
    int score;
    int nextLife = 1000;

    public Player(){
        this.lives = 3;
        this.score = 0;
    }

    public void addScore(int scoreAmount){
        score += scoreAmount;
        checkExtraLife();
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

    public void calculateScore(int distance){
        int scoreAmount = 0;
        if (distance==0){
            scoreAmount = 40;
        }
        else if (distance==20){
            scoreAmount = 20;
        }

        addScore(scoreAmount);
    }

    public void checkExtraLife(){
        if (score>=nextLife){
            addLife();
            nextLife += 1000;
        }
    }

    public int getLives() {
        return lives;
    }
}
