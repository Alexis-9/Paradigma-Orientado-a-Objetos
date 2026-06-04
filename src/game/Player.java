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

    public void addScoreByDistance(double distance){
        int scoreAmount;
        if (distance >= 150) {
            scoreAmount = 40;
        } else if (distance >= 80) {
            scoreAmount = 20;
        } else {
            scoreAmount = 0;
        }
        addScore(scoreAmount);
    }

    public void checkExtraLife(int levelNumber){
        while (score>=nextLife){
            if (levelNumber < 5) {
                addLife();
            }
            nextLife += 1000;
        }
    }

    public int getLives() {
        return lives;
    }
}
