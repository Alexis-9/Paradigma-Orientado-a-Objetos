package main.Model;

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

    /**
     * Updates the squadron state each frame.
     *
     * PRE:
     * - delta >= 0.
     * - level != null.
     *
     * POST:
     * - spawnCounter is incremented.
     * - When spawnCounter reaches threshold, drones may be spawned.
     * - Drones are updated.
     * - Drones that leave the screen are removed.
     *
     * @param delta time elapsed since last update
     */
    public void update(float delta){
        spawnCounter++;

        if(spawnCounter >= 100){
            // spawn limit prevents screen overload
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

    /**
     * Spawns a new drone in a random position and direction.
     *
     * PRE:
     * - level != null.
     * - screenWidth > 0.
     *
     * POST:
     * - A new Drone is created and added to the list.
     * - Drone direction is randomly LEFT or RIGHT.
     * - Drone starts outside screen boundaries.
     * - Level shootCooldown is used as drone parameter.
     */
    public void spawnDrone(){
        Direction direction = calculateDirection();

        int randomY = (int)(Math.random() * 150);

        int startX = direction == Direction.RIGHT ? -48 : screenWidth;

        Drone drone = new Drone(startX, randomY, 70, 70, direction, (int) level.getShootCooldown());
        drone.setDefaultValues();
        drones.add(drone);
    }

    /**
     * Draws all drones in the squadron.
     *
     * PRE:
     * - g != null.
     *
     * POST:
     * - All drones are rendered on the screen.
     * - No game state is modified.
     *
     * @param g graphics context used for rendering
     */
    public void draw(Graphics g){
        for(Drone drone : drones){
            drone.draw(g);
        }
    }

    /**
     * Randomly selects a movement direction.
     *
     * POST:
     * - Returns either Direction.RIGHT or Direction.LEFT.
     * - Both directions have the same probability of being selected.
     * - The object state is not modified.
     *
     * @return the selected direction
     */
    public Direction calculateDirection(){
        Direction direction;

        if(Math.random() < 0.5){
            return Direction.RIGHT;

        } else {
            return Direction.LEFT;
        }
    }

    /**
     * Getters.
     */
    public int getDronesRemaining() { return dronesRemaining; }
    public ArrayList<Drone> getDrones(){ return drones; }
}