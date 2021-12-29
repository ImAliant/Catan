package Game;

public class Building {
    private int buildingType;  // 0 : Vide, 1 : Colonie, 2 : Ville 

    public Building(int buildingType){
        this.buildingType=buildingType;
    }

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
    
    public boolean canUpgradeToSettlements() {
        switch (buildingType) {
            case 0: return true;
            default: return false;
        }
    }
    
    public boolean upgradeToSettlements() {
        if (!canUpgradeToSettlements()) { return false; }
        else {
            buildingType++;
            return true;
        }
    }

    public boolean canUpgradeToCity(){
        switch (buildingType) {
            case 1: return true;
            default: return false;
        }
    }
    
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
