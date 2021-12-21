package Game;

public class Case {
    private final int id;
    private final Resource resource;
    private boolean robber;
    private final int land;
    private final int diceRoll;
    
    public Case(int id, int land, Resource resource, int diceRoll){
        this.id=id;
        this.land=land;
        this.resource=resource;
        this.diceRoll=diceRoll;
    }


    //Getters et Setters
    public int getId() {return id;}
    public Resource getResource() {return resource;}
    public boolean isRobber() {return robber;}
    public void setRobber(boolean robber) {this.robber = robber;}
    public int getLand() {return land;}
    public int getDiceRoll() {return diceRoll;}
}
