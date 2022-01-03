package Game;

public class Case {
    private static int nextId = 0;

    private int id;
    private final Resource resource;
    private boolean robber;
    private final int land;
    private final int diceRoll;
    private Intersection[] caseIntersections;

    public Case(int land, Resource resource, int diceRoll){
        id = nextId++;
        this.land=land;
        this.resource=resource;
        this.diceRoll=diceRoll;
        this.robber = false;
        caseIntersections=null;
    }


  //Getters et Setters
  public int getId() {return id;}
  public Resource getResource() {return resource;}
  public boolean isRobber() {return robber;}
  public void setRobber(boolean robber) {this.robber = robber;}
  public int getLand() {return land;}
  public int getDiceRoll() {return diceRoll;}
  public Intersection[] getCaseIntersections() {return caseIntersections;}
  public void setCaseIntersections(Intersection[] caseIntersections) {this.caseIntersections = caseIntersections;}
  
}
