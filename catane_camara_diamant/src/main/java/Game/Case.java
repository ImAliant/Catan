package Game;

/**
 * <b>Case est la classe représentant les cases du plateau</b>
 * <p>
 * Case est caractérisé par les champs suivants:
 * <ul>
 * <li>Deux entier correspondant a l'id de la case: {@link #nextId}, {@link #id}</li>
 * <li>Un champ resource correspondant a la ressource que donne la case: {@link #resource}</li>
 * <li>Un booléen correspondant a la présence du voleur sur la case: {@link #robber}</li>
 * <li>Un entier correspondant au type de terrain: {@link #land}</li>
 * <li>Un entier correspondant au résultat du jet de deux dés qui permettra aux Joueur de collecter des ressources: {@link #diceRoll}</li>  
 * <li>Un tableau d'intersections correspondant aux intersections adjacentes a la case: {@link #caseIntersections}</li>
 * </ul>
 * </p>
 * 
 * @see Resource
 * @see Intersection
 * 
 * @author CAMARA Ibrahime, DIAMANT Alexandre
 */
public class Case {
    /**
     * S'incrémente est donne sa valeur au champ {@link #id}.
     */
    private static int nextId = 0;

    /**
     * ID de la case.
     * 
     * @see Case#getId()
     */
    private int id;
    /**
     * Ressource de la case.
     * 
     * @see Case#getResource()
     * @see Resource
     */
    private final Resource resource;
    /**
     * Présence du voleur ou non.
     * 
     * @see Case#isRobber()
     */
    private boolean robber;
    /**
     * Type de terrain de la case
     * 
     * @see Case#getLand()
     */
    private final int land;
    /**
     * Résultat du jet de dés nécessaires a collecte des ressources.
     * 
     * @see Case#getDiceRoll()
     */
    private final int diceRoll;
    /**
     * Intersections adjacentes a la case.
     * 
     * @see Case#getCaseIntersections()
     * @see Intersection
     */
    private Intersection[] caseIntersections;

    /**
     * <b>Constructeur Case</b>
     * <p>
     * A la construction d'un objet Case, un type de terrain est attribué à {@link #land}, 
     * une ressource à {@link #resource}, et un entier en 2 et 12 à {@link #diceRoll}. {@link #robber} est initialisé a false
     * par défaut et {@link #caseIntersections} est vide.</p>
     * @param land
     *    Type de terrain.
     * @param resource
     *    Type de ressource.
     * @param diceRoll
     *    Résultat jet de dés.
     * 
     * @see Case#getId()
     * @see Case#getLand()
     * @see Case#getResource()
     * @see Resource
     * @see Intersection
     */
    public Case(int land, Resource resource, int diceRoll){
        id = nextId++;
        this.land=land;
        this.resource=resource;
        this.diceRoll=diceRoll;
        this.robber = false;
        caseIntersections=null;
    }


  /**
   * Getters
   * @return L'id de la case.
   */
  public int getId() {return id;}
  /**
   * Getters
   * @return La ressource de la case.
   */
  public Resource getResource() {return resource;}
  /**
   * Getters
   * @return La présence du voleur sur la case.
   */
  public boolean isRobber() {return robber;}
  /**
   * Setters
   * @param robber
   */
  public void setRobber(boolean robber) {this.robber = robber;}
  /**
   * Getters
   * @return Le type de terrain de la case.
   */
  public int getLand() {return land;}
  /**
   * Getters
   * @return Le jet de dés de la case.
   */
  public int getDiceRoll() {return diceRoll;}
  /**
   * Getters
   * @return Les intersections adjacentes de la case.
   */
  public Intersection[] getCaseIntersections() {return caseIntersections;}
  /**
   * Setters
   * @param caseIntersections
   */
  public void setCaseIntersections(Intersection[] caseIntersections) {this.caseIntersections = caseIntersections;}
  
}
