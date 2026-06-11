package Model;

public enum Images {
    DRONE("/Images/Drone/Drone.png"),
    PLANE("/Images/Plane/Skins/DefaultSkin.png"),
    MISSILE("/Images/Missile/Missile.png");

    private final String path;

    Images(String path){
        this.path = path;
    }

    /**
     * Getter.
     */
    public String getPath(){return path;}
}
