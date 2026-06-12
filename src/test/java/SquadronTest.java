package test.java;

import main.Model.Direction;
import main.Model.Drone;
import main.Model.Level;
import main.Model.Squadron;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SquadronTest {

    @Test
    void spawnDrone_addsDroneToList() {
        Squadron squadron = new Squadron(768, new Level(1));

        squadron.spawnDrone();

        assertEquals(1, squadron.getDrones().size());
    }

    @Test
    void spawnDrone_droneStartsOutsideScreen() {
        Squadron squadron = new Squadron(768, new Level(1));

        squadron.spawnDrone();
        Drone drone = squadron.getDrones().get(0);

        assertTrue(drone.getX() == -48 || drone.getX() == 768);
    }

    @Test
    void setDirection_returnsLeftOrRight() {
        Squadron squadron = new Squadron(768, new Level(1));

        Direction direction = squadron.setDirection();

        assertTrue(direction == Direction.LEFT || direction == Direction.RIGHT);
    }

    @Test
    void update_spawnsDroneAfterCounterReachesThreshold() {
        Squadron squadron = new Squadron(768, new Level(1));

        for (int i = 0; i < 100; i++) {
            squadron.update(0f); // delta 0: los drones no se mueven
        }

        assertEquals(1, squadron.getDrones().size());
        assertEquals(9, squadron.getDronesRemaining());
    }

    @Test
    void update_neverSpawnsMoreThanFourDrones() {
        Squadron squadron = new Squadron(768, new Level(1));

        for (int i = 0; i < 1000; i++) {
            squadron.update(0f);
        }

        assertEquals(4, squadron.getDrones().size());
    }

    @Test
    void update_removesDronesThatLeaveTheScreen() {
        Squadron squadron = new Squadron(768, new Level(1));
        squadron.spawnDrone();

        // con delta grande el drone cruza y sale de la pantalla
        for (int i = 0; i < 10; i++) {
            squadron.update(1f);
        }

        assertEquals(0, squadron.getDrones().size());
    }
}