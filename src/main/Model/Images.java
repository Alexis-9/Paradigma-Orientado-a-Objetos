package main.Model;

public enum Images {
    DRONE("/main/Images/Drone/Drone.png"),
    PLANE("/main/Images/Plane/Skins/DefaultSkin.png"),
    MISSILE("/main/Images/Missile/Missile.png"),

    LEVEL_1("/main/Images/Background/level1.png"),
    LEVEL_2("/main/Images/Background/level2.png"),
    LEVEL_3("/main/Images/Background/level3.png"),
    LEVEL_4("/main/Images/Background/level4.png"),
    LEVEL_5("/main/Images/Background/level5.png");

    private final String path;

    Images(String path){
        this.path = path;
    }

    /**
     * Returns the image path.
     * POST:
     * - Returns the resource path associated with the image.
     * @return image resource path
     */
    public String getPath(){
        return path;
    }

    /**
     * Returns the background image associated with a level number.
     * PRE:
     * - levelNumber >= 1.
     * POST:
     * - Returns LEVEL_1 for level 1.
     * - Returns LEVEL_2 for level 2.
     * - Returns LEVEL_3 for level 3.
     * - Returns LEVEL_4 for level 4.
     * - Returns LEVEL_5 for level 5 or higher.
     * @param levelNumber current level number
     * @return background image for the current level*/
    public static Images getBackgroundByLevel(int levelNumber) {
        if (levelNumber <= 1) {
            return LEVEL_1;
        }

        if (levelNumber == 2) {
            return LEVEL_2;
        }

        if (levelNumber == 3) {
            return LEVEL_3;
        }

        if (levelNumber == 4) {
            return LEVEL_4;
        }

        return LEVEL_5;
    }
}