package Game;

public class Building {
    private int buildingType;
    
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
    public boolean canUpgrade() {
        switch (buildingType) {
            case 0: case 1: return true;
            default: return false;
        }
    }
    public boolean upgrade() {
        if (!canUpgrade()) { return false; }
        else {
            buildingType++;
            return true;
        }
    }

    //Getters et Setters
    public int getBuildingType() {return buildingType;}
    public void setBuildingType(int buildingType) {this.buildingType = buildingType;}
}
