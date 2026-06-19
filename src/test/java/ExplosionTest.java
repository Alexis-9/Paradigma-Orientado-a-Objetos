package test.java;

import main.Model.Explosion;
import main.Model.ExplosionResult;
import main.Model.Plane;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExplosionTest {

    @Test
    void finished_initiallyFalse() {
        Explosion explosion = new Explosion(0, 0, 150);

        assertFalse(explosion.finished());
    }

    @Test
    void finished_afterDuration_returnsTrue() {
        Explosion explosion = new Explosion(0, 0, 150);

        for (int i = 0; i < 30; i++) {
            explosion.update(1);
        }

        assertTrue(explosion.finished());
    }

    @Test
    void finished_beforeDuration_returnsFalse() {
        Explosion explosion = new Explosion(0, 0, 150);

        for (int i = 0; i < 29; i++) {
            explosion.update(1);
        }

        assertFalse(explosion.finished());
    }

    @Test
    void calculateDistance_zeroDistance() {
        Plane plane = new Plane(0, 0, 50, 50);
        Explosion explosion = new Explosion(25, 25, 150); // Starts at plane center X and Y

        double distance = explosion.calculateDistance(plane);

        assertEquals(0, distance);
    }

    @Test
    void resolveImpact_explosionTouchingHitbox_isLethal() {
        Plane plane = new Plane(0, 0, 50, 50);
        Explosion explosion = new Explosion(10, 25, 150); // exactamente sobre el borde de la hitbox

        ExplosionResult result = explosion.resolveImpact(plane);

        assertTrue(result.isLethal());
    }

    @Test
    void resolveImpact_farDistance_givePointsNoDamage() {
        Plane plane = new Plane(175, 175, 50, 50); // Distance around 282
        Explosion explosion = new Explosion(0, 0, 150);

        ExplosionResult result = explosion.resolveImpact(plane);

        assertEquals(40, result.getScore());
        assertEquals(0,  result.getDamage());
        assertFalse(result.isLethal());
    }

    @Test
    void resolveImpact_mediumDistance_lowDamage() {
        Plane plane = new Plane(75, -25, 50, 50); // Distance around 100
        Explosion explosion = new Explosion(0, 0, 150);

        ExplosionResult result = explosion.resolveImpact(plane);

        assertEquals(20, result.getScore());
        assertEquals(20, result.getDamage());
        assertFalse(result.isLethal());
    }

    @Test
    void resolveImpact_closeDistance_highDamage() {
        Plane plane = new Plane(25, -25, 50, 50); // Distance around 50
        Explosion explosion = new Explosion(0, 0, 150);

        ExplosionResult result = explosion.resolveImpact(plane);

        assertEquals(0,  result.getScore());
        assertEquals(40, result.getDamage());
        assertFalse(result.isLethal());
    }

    @Test
    void resolveImpact_veryCloseDistance_isLethal() {
        Plane plane = new Plane(-15, -25, 50, 50); // Distance around 10
        Explosion explosion = new Explosion(0, 0, 150);

        ExplosionResult result = explosion.resolveImpact(plane);

        assertEquals(0, result.getScore());
        assertEquals(0, result.getDamage());
        assertTrue(result.isLethal());
    }
}