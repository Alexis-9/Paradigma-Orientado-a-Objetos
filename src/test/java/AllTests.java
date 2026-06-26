package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ExplosionTest.class,
        GameControllerTest.class,
        LevelTest.class,
        MissileTest.class,
        PlaneTest.class,
        PlayerTest.class,
 SquadronTest.class
})
public class AllTests {
}