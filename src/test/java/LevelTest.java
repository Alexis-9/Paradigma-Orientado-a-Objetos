package test.java;

import main.Model.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    @Test
    void nextLevel_incrementsLevel(){
        Level level = new Level(1);

        level.nextLevel();

        assertEquals(2, level.getLevelNumber());
    }

    @Test
    void levelIsInfinite_worksCorrectly(){
        Level level = new Level(5);
        level.nextLevel();

        assertTrue(level.levelIsInfinite());
    }

    @Test
    void nextLevel_DoesNotGoAboveLevelFive(){
        Level level = new Level (5);
        level.nextLevel();

        assertEquals(5,level.getLevelNumber());
    }

}
