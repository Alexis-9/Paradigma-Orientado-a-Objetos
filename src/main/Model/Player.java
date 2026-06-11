package main.Model;

public class Player {

    private int lives;
    private int score;
    private int nextLife = 1000;

    public Player(){
        this.lives = 3;
        this.score = 0;
    }

    /**
     * Adds points to the player score.
     *
     * PRE:
     * - scoreAmount >= 0.
     *
     * POST:
     * - If scoreAmount < 0, score remains unchanged.
     * - Otherwise, score is increased by scoreAmount.
     *
     * @param scoreAmount points to add to the score
     */
    public void addScore(int scoreAmount){
        if (scoreAmount < 0) return;
        score += scoreAmount; }

    /**
     * Decreases the number of lives by one.
     *
     * POST:
     * - If lives > 0, lives is decreased by 1.
     * - If lives == 0, no change occurs.
     */
    public void loseLife(){ if (lives > 0) lives--; }

    /**
     * Increases the number of lives by one.
     *
     * POST:
     * - lives is incremented by 1.
     */
    public void addLife(){ lives++; }

    /**
     * Checks and grants extra lives based on score progression.
     *
     * PRE:
     * - levelNumber >= 0
     *
     * POST:
     * - While score >= nextLife:
     *   - If levelNumber < 5, an extra life is added.
     *   - nextLife threshold is increased by 1000.
     * - score remains unchanged.
     *
     * @param levelNumber current level of the game
     */
    public void checkExtraLife(int levelNumber){
        while (score >= nextLife){
            if (levelNumber < 5) addLife();
            nextLife += 1000;
        }
    }

    /**
     * Getters.
     */
    public int getScore(){ return score; }
    public int getLives() { return lives; }
}