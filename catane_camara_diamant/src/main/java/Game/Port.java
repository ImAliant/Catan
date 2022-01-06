package Game;

/**
 * <b>Port est la classe correspondant au port présent sur le plateau</b>
 * <p>
 * Port est caractérise par les champs suivant:
 * <ul>
 * <li>Un entier correspondant au type de port: {@link #portType}</li>
 * <li>Un objet Resource correspondant a la resource du port si le port est de type 2:1: {@link #resource}</li>
 * </ul>
 * </p>
 * 
 * @see Resource
 * 
 * @author CAMARA Ibrahime, DIAMANT Alexandre
 */
public class Port {
    /**
     * Type de port : 0 = 2:1; 1 = 3:1
     */
    private final int portType;
    /**
     * Resource du port 2:1
     */
    private final Resource resource; //Resource spécifié si type "2:1"

    /**
     * <b>Premier onstructeur Port</b>
     * <p>
     * A la construction d'un objet Port 2:1, une resource spécifique est demandé et attribué au champ {@link #resource}.
     * </p> 
     * @param resource
     *      Type de ressource
     * 
     * @see Resource
     */
    public Port(Resource resource){
          this.portType = 0;
          this.resource = resource;
    }

    /**
     * <b>Second constructeur Port</b>
     * <p>
     * A la construction d'un objet Port 3:1, {@link #portType} est initialisé a 1 et {@link #resource} à null.
     * </p>
     */
    public Port(){ // port type "3:1"
        this.portType=1;
        this.resource=null;
    }

    /**
     * Retourne une chaine de caractere correspondant au type du port.
     */
    @Override
    public String toString(){
        if(portType==1){
            return "3:1";
        }
        else return "2:1 ("+resource.toString()+")";
    }

    /**
     * Getters
     * @return Le type du port.
     */
    public int getPortType() {return portType;}
    /**
     * Getters
     * @return La ressource du port.
     */
    public Resource getResource() {return resource;}
}
