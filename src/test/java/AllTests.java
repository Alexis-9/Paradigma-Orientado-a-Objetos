package test.java;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
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