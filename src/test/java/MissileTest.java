package test.java;

import main.Model.Missile;
import main.Model.Plane;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MissileTest {

    @Test
    void move_increasesYOnly() {
        Missile missile = new Missile(100, 100, 260, 600);

        missile.move(1f);

        assertEquals(360, missile.getY(), 0.0001);
        assertEquals(100, missile.getX(), 0.0001);
    }

    @Test
    void shouldExplode_aboveExplosionY_returnsFalse() {
        Missile missile = new Missile(100, 100, 260, 600);

        assertFalse(missile.shouldExplode());
    }

    @Test
    void shouldExplode_atExplosionY_returnsTrue() {
        Missile missile = new Missile(100, 600, 260, 600);

        assertTrue(missile.shouldExplode());
    }

    @Test
    void update_reachesExplosionY_startsExploding() {
        Missile missile = new Missile(100, 500, 260, 600);

        missile.update(1f); // baja 260px -> pasa explosionY

        assertTrue(missile.isExploding());
        assertNotNull(missile.getExplosion());
    }

    @Test
    void triggerExplosion_setsExplodingAndCreatesExplosion() {
        Missile missile = new Missile(100, 100, 260, 600);

        missile.triggerExplosion();

        assertTrue(missile.isExploding());
        assertNotNull(missile.getExplosion());
    }

    @Test
    void triggerExplosion_calledTwice_doesNotReplaceExplosion() {
        Missile missile = new Missile(100, 100, 260, 600);

        missile.triggerExplosion();
        var firstExplosion = missile.getExplosion();
        missile.triggerExplosion();

        assertSame(firstExplosion, missile.getExplosion());
    }

    @Test
    void update_explosionFinishes_missileIsFinished() {
        Missile missile = new Missile(100, 100, 260, 600);
        missile.triggerExplosion();

        for (int i = 0; i < 30; i++) {
            missile.update(0f);
        }

        assertTrue(missile.isFinished());
    }

    @Test
    void collidesWith_overlappingPlane_returnsTrue() {
        Missile missile = new Missile(125, 125, 260, 600);
        Plane plane = new Plane(100, 100, 50, 50);

        assertTrue(missile.collidesWith(plane));
    }

    @Test
    void collidesWith_farPlane_returnsFalse() {
        Missile missile = new Missile(100, 100, 260, 600);
        Plane plane = new Plane(500, 500, 50, 50);

        assertFalse(missile.collidesWith(plane));
    }
}