package Model;

public enum Images {
    DRONE("/model.ImagesDrone.png"),
    PLANE("/model.Imagesrone.png"),
    MISSILE("/model.ImagesDrone.png");

    private final String path;

    Images(String path){
        this.path = path;
    }

    public String getPath(){return path;}
}
