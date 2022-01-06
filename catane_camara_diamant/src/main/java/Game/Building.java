package Game;

/**
 * <b>Building est la classe représentant les batiments construits sur les intersections</b>
 * <p>
 * Building est caractérisé par le champ suivant:
 * <ul>
 * <li>Un entier qui correspond au type de batiment construit sur l'intersection: {@link #buildingType}</li>
 * </ul>
 * </p>
 * 
 * @see Intersection
 * 
 * @author CAMARA Ibrahime, DIAMANT Alexandre
 */
public class Building {
    /**
     * Correspond au type de batîment présent sur l'intersection. 
     * <ul>
     * <li>0 = Vide</li>
     * <li>1 = Colonie</li>
     * <li>2 = Ville</li> 
     * </ul>
     */
    private int buildingType;

    /**
     * <b>Constructeur Building</b>
     * <p>A la construction d'un objet Building, un entier est demandé. 
     * Cette entier correspond au type de batiment.
     * </p>
     * 
     * @param buildingType
     *      Type de batîment.
     * @throws Exception
     *      On jete une exception si l'entier {@link Building#buildingType} n'est pas entre 0 et 2.
     * 
     * @see Building#getBuildingType()
     */
    public Building(int buildingType) throws Exception{
        if(buildingType<0 || buildingType>2)
            throw new Exception("Ce type de batiment n'existe pas !");

        this.buildingType=buildingType;
    }

    /**
     * @return Une chaine de caractère qui correspond au type de batiment.
     */
    @Override
    public String toString() {
        switch (buildingType) {
            case 0: 
                return "Vide";
            case 1: 
                return "Colonie";
            case 2: 
                return "Ville";
            default: return "Invalid building";
        }
    }
    
    /**
     * Teste si l'objet peut être amélioré en colonie.
     * @return True si l'objet peut être amélioré en colonie. False sinon.
     */
    public boolean canUpgradeToSettlements() {
        switch (buildingType) {
            case 0: return true;
            default: return false;
        }
    }
    
    /**
     * Améliore le batiment si {@link Building#buildingType} est vide.
     * 
     * @return True si l'objet a été amélioré. False sinon.
     */
    public boolean upgradeToSettlements() {
        if (!canUpgradeToSettlements()) { return false; }
        else {
            buildingType++;
            return true;
        }
    }

    /**
     * Teste si l'objet peut être amélioré en ville.
     * @return True si l'objet peut être amélioré en ville. False sinon.
     */
    public boolean canUpgradeToCity(){
        switch (buildingType) {
            case 1: return true;
            default: return false;
        }
    }
    
     /**
     * Améliore le batiment si {@link Building#buildingType} est une colonie.
     * 
     * @return True si l'objet a été amélioré. False sinon.
     */
    public boolean upgradeToCity(){
        if(!canUpgradeToCity()) return false;
        else{
            buildingType++;
            return true;
        }
    }

    /**
     * Getters
     * @return Le type de batiment.
     */
    public int getBuildingType() {return buildingType;}
    /**
     * Setters
     * @param buildingType
     */
    public void setBuildingType(int buildingType) {this.buildingType = buildingType;}
}
