package test.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.Model.Player;

class PlayerTest {

    @Test
    void addScore_increasesScoreCorrectly() {
        Player player = new Player();

        player.addScore(500);

        assertEquals(500, player.getScore());
    }

    @Test
    void addScore_negativeValue_doesNotChangeScore() {
        Player player = new Player();

        player.addScore(-200);

        assertEquals(0, player.getScore());
    }

    @Test
    void loseLife_decreasesLifeByOne() {
        Player player = new Player();

        player.loseLife();

        assertEquals(2, player.getLives());
    }

    @Test
    void loseLife_doesNotGoBelowZero() {
        Player player = new Player();

        player.loseLife();
        player.loseLife();
        player.loseLife();
        player.loseLife(); // extra call

        assertEquals(0, player.getLives());
    }

    @Test
    void addLife_increasesLives() {
        Player player = new Player();

        player.addLife();

        assertEquals(4, player.getLives());
    }

    @Test
    void checkExtraLife_givesExtraLifeWhenScoreThresholdReached() {
        Player player = new Player();

        // subir score suficiente para activar extra life
        player.addScore(1000);

        player.checkExtraLife(1);

        assertTrue(player.getLives() >= 4);
    }

    @Test
    void checkExtraLife_doesNotGiveLifeIfBelowThreshold() {
        Player player = new Player();

        player.addScore(500);
        player.checkExtraLife(1);

        assertEquals(3, player.getLives());
    }
}