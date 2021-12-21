package Game;

public class Resource {
    public static final int BOIS=0;
    public static final int PIERRE=1;
    public static final int BLE=2;
    public static final int MOUTON=3;
    public static final int ARGILE=4;

    private final int resourceType;
    
    public Resource(int resourceType){
        this.resourceType=resourceType;
    }

    //Getters et Setters
    public int getResourceType() {return resourceType;}
}
