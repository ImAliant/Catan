package Game;

/**
 * <b>DevCard est la classe correspondant aux cartes de développement.</b>
 * <p>
 * DevCard est caractérisé par le champ suivant:
 * <ul>
 * <li>Un entier correspondant au type de carte qu'est l'objet DevCard</li>
 * </ul>
 * 
 * @author CAMARA Ibrahime, DIAMANT Alexandre
 */
public class DevCard {
    /**
     * Correspond a la carte "point de victoire".
     */
    public static final int VICTORY_POINT=0;
    /**
     * Correspond a la carte "progrès : contruction".
     */
    public static final int PROGRESS_BUILD=1;
    /**
     * Correspond a la carte "progrès : découverte".
     */
    public static final int PROGRESS_DISCOVERY=2;
    /**
     * Correspond a la carte "progrès : monopole".
     */
    public static final int PROGRESS_MONOPOLY=3;
    /**
     * Correspond a la carte "chevalier".
     */
    public static final int KNIGHT=4;

    /**
     * Correspond au type de la carte.
     * 
     * @see DevCard#getCard()
     */
    private int card;
    
    /**
     * <b>Constructeur DevCard</b>
     * <p>
     * A la construction d'un objet DevCard, un entier correspondant au type de la carte est démandé.
     * </p>
     * @param card
     *      Type de carte
     * @throws Exception
     *      Si l'entier ne correspond pas a une des constantes, une exception est lancé.
     * 
     * @see #VICTORY_POINT
     * @see #PROGRESS_BUILD
     * @see #PROGRESS_DISCOVERY
     * @see #PROGRESS_MONOPOLY
     * @see #KNIGHT
     */
    public DevCard(int card) throws Exception{
        switch (card) {
            case VICTORY_POINT: case PROGRESS_BUILD: case PROGRESS_DISCOVERY: case PROGRESS_MONOPOLY: case KNIGHT:
                this.card=card;
                break;
            default:
                throw new Exception("Cette carte n'existe pas !");
        }
    }

    /**
     * Retourne une chaine de caractère correspondant au type de la carte de développement.
     * 
     * @see DevCard#getCard()
     */
    @Override
    public String toString(){
        String s="";
        switch (card) {
            case VICTORY_POINT:
                s="Carte Victoire";
                break;
            case PROGRESS_BUILD:
                s="Carte Progrès : Construction";
                break;
            case PROGRESS_DISCOVERY:
                s="Carte Progrès : Découverte";
                break;
            case PROGRESS_MONOPOLY:
                s="Carte Progrès : Monopole";
                break;
            case KNIGHT:
                s="Carte Chevalier";
                break;
        }
        return s;
    }

    /**
     * Getters
     * @return Le type de carte de l'objet DevCard.
     */
    public int getCard() {return card;}
    /**
     * Setters
     * @param card
     */
    public void setCard(int card) {this.card = card;}
}
