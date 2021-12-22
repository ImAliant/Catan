
public class Case {
    private static int id = -1;
    private Resource resource;
    private boolean robber;
    private int land;
    private int diceRoll;

    public Case(int land, Resource resource, int diceRoll){
        this.id++;
        this.land=land;
        this.resource=resource;
        this.diceRoll=diceRoll;
        this.robber = false;
    }


  public void setRobber(boolean b){
    this.robber = b;
  }

  
}
