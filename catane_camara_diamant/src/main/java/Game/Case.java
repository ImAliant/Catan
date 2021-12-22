package Game;

public class Case {
    private static int id = -1;
    private final Resource resource;
    private boolean robber;
    private final int land;
    private final int diceRoll;

    public Case(int land, Resource resource, int diceRoll){
        id++;
        this.land=land;
        this.resource=resource;
        this.diceRoll=diceRoll;
        this.robber = false;
    }


  //Getters et Setters
  public int getId() {return id;}
  public Resource getResource() {return resource;}
  public boolean isRobber() {return robber;}
  public void setRobber(boolean robber) {this.robber = robber;}
  public int getLand() {return land;}
  public int getDiceRoll() {return diceRoll;}
  
}
