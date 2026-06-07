package Model;

import java.awt.*;
import java.util.ArrayList;

public class Squadron {

    private ArrayList<Drone> drones;
    private int dronesRemaining;
    private int spawnCounter;
    private int screenWidth;
    private Level level;

    public Squadron(int screenWidth, Level level){
        this.screenWidth = screenWidth;
        this.level = level;
        drones = new ArrayList<>();
        dronesRemaining = 10;
    }

    public void update(float delta){
        spawnCounter++;

        if(spawnCounter >= 100){
            if(drones.size() < 4 && (dronesRemaining > 0 || level.levelIsInfinite())) {
                spawnDrone();
                if (!level.levelIsInfinite()) dronesRemaining--;
            }
            spawnCounter = 0;
        }

        for(int i = 0; i < drones.size(); i++){
            Drone drone = drones.get(i);
            drone.update(delta);
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
            direction = Direction.RIGHT;
            startX = -48;
        } else {
            direction = Direction.LEFT;
            startX = screenWidth;
        }

        Drone drone = new Drone(startX, randomY, 70, 70, direction, (int) level.getShootCooldown());
        drone.setDefaultValues();
        drones.add(drone);
    }

    public void draw(Graphics g){
        for(Drone drone : drones){
            drone.draw(g);
        }
    }

    public int getDronesRemaining() { return dronesRemaining; }

    public ArrayList<Drone> getDrones(){ return drones; }
}