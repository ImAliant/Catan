

public class Resource {
    public static final int BOIS=0;
    public static final int PIERRE=1;
    public static final int BLE=2;
    public static final int MOUTON=3;
    public static final int ARGILE=4;
    public static final int DESERT=-1;

    private final int resourceType;
    
    public Resource(int resourceType){
        this.resourceType=resourceType;
    }

    public String toString(){
        String resource="";
        switch (resourceType) {
            case BOIS: 
                resource="BOIS";
                break;
            case PIERRE:
                resource="PIERRE";
                break;
            case BLE:
                resource="BLE";
                break;
            case MOUTON:
                resource="MOUTON";
                break;
            case ARGILE:
                resource="ARGILE";
                break;
            case DESERT:
                resource="DESERT";
                break;
        }
        return resource;
    }

    //Getters et Setters
    public int getResourceType() {return resourceType;}
}
