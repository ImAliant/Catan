package Game;

/**
 * <b>Road est la classe correspondant aux arêtes du plateau.</b>
 * <p>
 * Road est caractérisé par les champs suivants:
 * <ul>
 * <li>Deux entiers correspondants à l'id de l'arête: {@link #nextId}, {@link #id}</li>
 * <li>Deux entiers correspondants aux intersections liés par l'arête: {@link #id1}, {@link #id2}</li>
 * <li>Un booléen correspondant a la présence d'une route sur l'arête: {@link #road}</li>
 * <li>Un objet Player correspondant au propriétaire de la route: {@link #player}</li>
 * </ul>
 * </p>
 * 
 * @see Player
 * 
 * @author CAMARA Ibrahime, DIAMANT Alexandre
 */
public class Road {
    /**
     * S'incrémente est donne sa valeur au champ {@link #id}.
     */
    private static int nextId = 0;
    /**
     * ID de l'arête
     */
    private final int id;
    /**
     * ID de la premiere intersection.
     */
    private final int id1;
    /**
     * ID de la seconde intersection.
     */
    private final int id2;
    /**
     * True si une route est présente, False sinon.
     */
    private boolean road;
    /**
     * Propriétaire de la route.
     */
    private Player player;

    /**
     * <b>Constructeur Road</b>
     * <p>
     * A la construction d'un objet Road, deux entiers sont demandés et correspondent aux id des intersections lié par l'arête.
     * Le premier ID est attribué à {@link #id1} et le second à {@link #id2}.
     * </p>
     * @param id1
     *      ID premiere intersection
     * @param id2
     *      ID seconde intersection
     */
    public Road(int id1, int id2){
        id=nextId++;
        this.id1=id1;
        this.id2=id2;
        road=false;
        player=null;
    }
    /**
     * Teste si l'objet peut avoir une route.
     * @return True si l'objet peut avoir une route. False sinon.
     */
    public boolean canUpgradeRoad(){
        if(!road){
            return true;
        }
        return false;
    }
    /**
     * Pose une route si {@link #canUpgradeRoad()} retourne True.
     * 
     * @return True si l'objet a été amélioré. False sinon.
     * 
     * @param p
     *      Joueur qui pose une route
     */
    public boolean upgradeRoad(Player p){
        if(!canUpgradeRoad()) return false;
        
        road=true;
        player=p;
        return true;
    }

    /**
     * Retourne une chaine de caractéres correspondant aux IDs de l'arête.
     */
    public String toString(){
        return "("+id1+", "+id2+")";
    }

    /**
     * Getters
     * @return Le premier id.
     */
    public int getId1() {return id1;}
    /**
     * Getters
     * @return Le second id.
     */
    public int getId2() {return id2;}
    /**
     * Getters
     * @return Un booléen correspondant a la présence d'une route.
     */
    public boolean isRoad() {return road;}
    /**
     * Setters
     * @param road
     */
    public void setRoad(boolean road) {this.road = road;}
    /**
     * Getters
     * @return Le joueur possèdant la route.
     */
    public Player getPlayer() {return player;}
    /**
     * Setters
     * @param player
     */
    public void setPlayer(Player player) {this.player = player;}
    /**
     * Getters
     * @return L'ID de l'arête.
     */
    public int getId() {return id;}
}
