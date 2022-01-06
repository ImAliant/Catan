package Game;

/**
 * <b>Intersection est la classe représentant les intersections du plateau.</b>
 * <p>
 * Intersection est caractérisé par les champs suivants:
 * <ul>
 * <li>Deux correspondants à l'id de l'intersection: {@link #nextId}, {@link #id}</li>
 * <li>Un objet Building correspondant au type de batiment présent sur l'intersection: {@link #building}</li>
 * <li>Un objet Player correspondant au joueur possèdant le batiment sur l'intersection: {@link #player}</li>
 * <li>Un objet Port correspondant au port présent sur l'intersection: {@link #port}</li>
 * <li>Un tableau de Case correspondant aux cases adjacentes à l'intersection: {@link #caseAdj}</li>
 * </ul>
 * </p>
 * 
 * @see Building
 * @see Player
 * @see Port
 * @see Case 
 * 
 * @author CAMARA Ibrahime, DIAMANT Alexandre
 */
public class Intersection {
    /**
     * S'incrémente est donne sa valeur au champ {@link #id}.
     */
    private static int nextId = 0;
    /**
     * ID de l'intersection.
     * 
     * @see Intersection#getId()
     */
    private int id;
    /**
     * Type de batiment présent sur l'intersection.
     * 
     * @see Intersection#getBuilding()
     */
    private Building building;
    /**
     * Joueur possèdant le batiment (si le batiment n'est pas vide).
     * 
     * @see Intersection#getPlayer()
     */
    private Player player;
    /**
     * Port présent sur l'intersection.
     * 
     * @see Intersection#getPort()
     */
    private Port port;
    /**
     * Cases adjacentes à l'intersection.
     * 
     * @see Intersection#getCaseAdj()
     */
    private final Case[] caseAdj;

    /**
     * <b>Constructeur Intersection</b>
     * <p>
     * A la construction d'un objet Intersection, un type de batiment, un port, et un tableau de Case sont demandés.
     * {@link #player} est initialisé a null.
     * </p>
     * 
     * @param building
     *      Type de batiment (par défaut VIDE)
     * @param port
     * @param caseAdj
     *      Tableau de cases adjacentes à l'intersection
     */
    public Intersection(Building building, Port port, Case[] caseAdj){
        id=nextId++;
        this.building=building;
        this.port=port;
        player=null;
        this.caseAdj=caseAdj;
    }

    /**
   * Getters
   * @return L'id de la case.
   */
    public int getId() {return id;}
    /**
   * Getters
   * @return Le batiment de l'intersection.
   */
    public Building getBuilding() {return building;}
    /**
   * Getters
   * @return Le joueur possèdant un batiment sur l'intersection.
   */
    public Player getPlayer() {return player;}
    /**
   * Setters
   * @param player
   */
    public void setPlayer(Player player) {this.player = player;}
    /**
   * Setters
   * @param building
   */
    public void setBuilding(Building building) {this.building = building;}
    /**
   * Getters
   * @return Le port de l'intersection.
   */
    public Port getPort(){return port;}
    /**
   * Setters
   * @param port
   */
    public void setPort(Port port) {this.port = port;}
    /**
   * Getters
   * @return Les cases adjacentes de l'intersection.
   */
    public Case[] getCaseAdj() {return caseAdj;}
}
