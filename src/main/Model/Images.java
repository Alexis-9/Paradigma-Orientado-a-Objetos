package main.Model;

public enum Images {
    DRONE("/main/Images/Drone/Drone.png"),
    PLANE("/main/Images/Plane/Skins/DefaultSkin.png"),
    MISSILE("/main/Images/Missile/Missile.png");

    private final String path;

    Images(String path){
        this.path = path;
    }

    /**
     * Getter.
     */
    public String getPath(){return path;}
}
