package main.Model;

public class ExplosionResult {

    private final int score;
    private final int damage;
    private final boolean lethal;

    public ExplosionResult(int score, int damage, boolean lethal){
        this.score = score;
        this.damage = damage;
        this.lethal = lethal;
    }


    /**
     * Getters.
     */
    public int getScore(){ return score; }

    public int getDamage(){ return damage; }

    public boolean isLethal(){ return lethal; }
}