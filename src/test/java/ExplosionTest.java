package test.java;

import main.Model.Explosion;
import main.Model.ExplosionResult;
import main.Model.Plane;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExplosionTest {

    @Test
    void finished_initiallyFalse() {
        Explosion explosion = new Explosion(0, 0);

        assertFalse(explosion.finished());
    }

    @Test
    void finished_afterDuration_returnsTrue() {
        Explosion explosion = new Explosion(0, 0);

        for (int i = 0; i < 30; i++) {
            explosion.update(1);
        }

        assertTrue(explosion.finished());
    }

    @Test
    void calculateDistance_samePosition_returnsZero() {
        Explosion explosion = new Explosion(0, 0);
        Plane plane = new Plane(0, 0, 50, 50);

        double distance = explosion.calculateDistance(plane);

        assertEquals(0.0, distance, 0.0001);
    }

    @Test
    void calculateDistance_correctValue() {
        Explosion explosion = new Explosion(0, 0);
        Plane plane = new Plane(3, 4, 50, 50);

        double distance = explosion.calculateDistance(plane);

        assertEquals(5.0, distance, 0.0001);
    }

    @Test
    void resolveImpact_farDistance_lowDamage() {
        Explosion explosion = new Explosion(0, 0);
        Plane plane = new Plane(300, 300, 50, 50);

        ExplosionResult result = explosion.resolveImpact(plane);

        assertFalse(result.isLethal());
        assertEquals(40, result.getDamage());
    }

    @Test
    void resolveImpact_mediumDistance_mediumDamage() {
        Explosion explosion = new Explosion(0, 0);
        Plane plane = new Plane(60, 0, 50, 50);

        ExplosionResult result = explosion.resolveImpact(plane);

        assertEquals(20, result.getDamage());
    }

    @Test
    void resolveImpact_closeDistance_highDamage() {
        Explosion explosion = new Explosion(0, 0);
        Plane plane = new Plane(10, 0, 50, 50);

        ExplosionResult result = explosion.resolveImpact(plane);

        assertEquals(40, result.getScore());
    }

    @Test
    void resolveImpact_veryClose_isLethal() {
        Explosion explosion = new Explosion(0, 0);
        Plane plane = new Plane(0, 0, 50, 50);

        ExplosionResult result = explosion.resolveImpact(plane);

        assertTrue(result.isLethal());
    }
}