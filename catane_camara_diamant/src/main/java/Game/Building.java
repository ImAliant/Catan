

public class Building {
    private int buildingType;  // 0 : Vide, 1 : Colonie, 2 : Ville 

    public Building(int buildingType){
        this.buildingType=buildingType;
    }

    //Retourne une chaine de caractere correspondant au type de batiment se trouvant sur l'intersection.
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
    
    //Retourne un booléen. True si le batiment peut être amélioré en colonie, false sinon.
    public boolean canUpgradeToSettlements() {
        switch (buildingType) {
            case 0: return true;
            default: return false;
        }
    }
    
    //Retourne un booléen. True si le batiment a été amélioré en colonie, false sinon.
    public boolean upgradeToSettlements() {
        if (!canUpgradeToSettlements()) { return false; }
        else {
            buildingType++;
            return true;
        }
    }

    //Retourne un booléen. True si le batiment peut être amélioré en ville, false sinon.
    public boolean canUpgradeToCity(){
        switch (buildingType) {
            case 1: return true;
            default: return false;
        }
    }
    
    //Retourne un booléen. True si le batiment a été amélioré ville, false sinon.
    public boolean upgradeToCity(){
        if(!canUpgradeToCity()) return false;
        else{
            buildingType++;
            return true;
        }
    }

    //Getters et Setters
    public int getBuildingType() {return buildingType;}
    public void setBuildingType(int buildingType) {this.buildingType = buildingType;}
}
