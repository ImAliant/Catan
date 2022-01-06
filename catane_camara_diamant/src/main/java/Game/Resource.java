package Game;

/**
 * <b>Resource est la classe représentant les ressources que donne les cases, 
 * les ressources spécifique a l'échange des ports et aux ressources que possèdent 
 * les joueurs</b>
 * 
 * <p>
 * Resource est caractérisé par le champ suivant:
 * <ul>
 * <li>Un entier correspondant au type de ressource: {@link #resourceType}</li>
 * </ul>
 * </p>
 * 
 * @author CAMARA Ibrahime, DIAMANT Alexandre
 */
public class Resource {
    /**
     * Correspond a la ressource bois.
     */
    public static final int BOIS=0;
    /**
     * Correspond a la ressource pierre.
     */
    public static final int PIERRE=1;
    /**
     * Correspond a la ressource ble.
     */
    public static final int BLE=2;
    /**
     * Correspond a la ressource mouton.
     */
    public static final int MOUTON=3;
    /**
     * Correspond a la ressource argile.
     */
    public static final int ARGILE=4;
    /**
     * Correspond a la ressource desert.
     */
    public static final int DESERT=-1;

    /**
     * Type de ressource.
     */
    private final int resourceType;
    
    /**
     * <b>Constructeur Resource</b>
     * <p>
     * A la construction d'un objet Resource, un entier est demandé et attribué a {@link #resourceType}.
     * </p>
     * @param resourceType
     *      Type de ressource.
     */
    public Resource(int resourceType){
        this.resourceType=resourceType;
    }

    /**
     * Retourne une chaine de caractere correspondant au type de ressource de l'objet Resource.
     */
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

    /**
     * Getters
     * @return Le type de ressource.
     */
    public int getResourceType() {return resourceType;}
}
