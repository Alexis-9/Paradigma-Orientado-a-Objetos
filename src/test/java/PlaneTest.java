package test.java;

import main.Model.Plane;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class PlaneTest {

    @Test
    void move_changesXandY_movesCorrectly(){
        Plane plane = new Plane(300, 300, 50, 50);
        plane.move(1, -1, 1/60f);


        assertTrue(plane.getX()>300);
        assertTrue(plane.getY()<300);
    }

    @Test
    void clampToScreen_PlaneDoesNotGoAboveTopBound(){
        Plane plane = new Plane(200,200,50,50);
        plane.move(0,-1,1/60f);
        plane.clampToScreen(768,768,200);

        assertEquals(200,plane.getY());
    }

    @Test
    void clampToScreen_PlaneDoesNotGoBelowBottomBound(){
        Plane plane = new Plane(200,715,50,50);
        plane.move(0,1,1/60f);

        plane.clampToScreen(768,768,200);

        assertEquals(718,plane.getY());
    }

    @Test
    void clampToScreen_PlaneDoesNotGoBeyondLeftBound(){
        Plane plane = new Plane(0,300,50,50);
        plane.move(-1,1,1/60f);

        plane.clampToScreen(768,768,200);

        assertEquals(0,plane.getX());
    }

    @Test
    void clampToScreen_PlaneDoesNotGoBeyondRightBound(){
        Plane plane = new Plane(718,300,50,50);
        plane.move(1,1,1/60f);

        plane.clampToScreen(768,768,200);

        assertEquals(718,plane.getX());
    }



    @Test
    void reduceEnergy_NegativeValueDoesNotChangeEnergy(){
        Plane plane = new Plane(300, 300 ,50 ,50);
        plane.reduceEnergy(-20);

        assertEquals(100,plane.getCurrentEnergy());
    }

    @Test
    void reduceEnergy_ReducesCorrectly(){
        Plane plane = new Plane(300, 300 ,50 ,50);
        plane.reduceEnergy(20);

        assertEquals(80,plane.getCurrentEnergy());
    }

    @Test
    void reduceEnergy_DoesNotGoBelowZero(){
        Plane plane = new Plane(300, 300 ,50 ,50);
        plane.reduceEnergy(120);

        assertEquals(0,plane.getCurrentEnergy());
    }

    @Test
    void restoreEnergy_ChangesCurrentEnergyToHundred(){
        Plane plane = new Plane(300, 300, 50, 50);
        plane.reduceEnergy(50);

        plane.restoreEnergy();

        assertEquals(100, plane.getCurrentEnergy());
    }

}
