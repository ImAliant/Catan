package Game;

public class Case {
    private int id;
    private Resource resource;
    private boolean robber;
    private int land;
    private int diceRoll;
    
    public Case(int id, int land, Resource resource, int diceRoll){
        this.id=id;
        this.land=land;
        this.resource=resource;
        this.diceRoll=diceRoll;
    }
}
