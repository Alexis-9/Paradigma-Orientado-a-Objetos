package game;

import entity.Drone;

import java.awt.*;
import java.util.ArrayList;

public class Squadron {

    private ArrayList<Drone> drones;

    private int dronesRemaining;

    private int spawnCounter;

    private int screenWidth;

    private Level level;

    private boolean isInfinite;

    public Squadron(int screenWidth, Level level){

        this.screenWidth = screenWidth;

        this.level = level;

        drones = new ArrayList<>();

        dronesRemaining = 10;

        isInfinite = (level.getLevelNumber() < 5) ? false : true;

    }

    public void update(){

        spawnCounter++;

        // SPAWN

        if(spawnCounter >= 100){

            if(drones.size() < 4 && (dronesRemaining > 0 || isInfinite)) {

                spawnDrone();

                if (!isInfinite) {
                    dronesRemaining--;
                }
            }

            spawnCounter = 0;
        }

        // UPDATE DRONES

        for(int i = 0; i < drones.size(); i++){

            Drone drone = drones.get(i);

            drone.update();

            if(drone.outOfScreen(screenWidth)){

                drones.remove(i);

                i--;
            }
        }
    }

    public void spawnDrone(){

        int randomY = (int)(Math.random() * 150);

        Direction direction;

        int startX;

        if(Math.random() < 0.5){

            direction = Direction.Right;

            startX = -48;

        } else {

            direction = Direction.Left;

            startX = screenWidth;
        }

        drones.add(new Drone(startX, randomY, direction, level.getDroneSpeed(), level.getShootCooldown()));
    }

    public void draw(Graphics g){

        for(Drone drone : drones){

            drone.draw(g);
        }
    }

    public ArrayList<Drone> getDrones(){
        return drones;
    }

    public boolean levelFinished(){
        return (dronesRemaining == 0 && drones.isEmpty() && !isInfinite);
    }
}