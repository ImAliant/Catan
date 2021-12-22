package Game;

public class Board {

  public static final int DESERT = -1;
  public static final int FORET = 0;
  public static final int MONTAGNE = 1;
  public static final int CHAMPS = 2;
  public static final int PRE = 3;
  public static final int COLLINE = 4;
  public static final int TOUT = 5;

  private Case[] cases;
  private Intersection[] intersections;
  private Road[] roads;
  private int indexRobber;

  public Board() {
    init();
  }

  private void init() {
    initCases();
    initIntersections();
    initRoads();
  }

  private void initCases() {
    // Initialiser le tableau de cases et l'index du voleur.
    // Le plateau a initialiser sera le plateau present sur le sujet. --je m'en
    // charge .IB
    // Pour l'instant pas de plateau initialise aleatoirement.
    // Vu qu'on est sur un tableau simple , se lit de gauche a droite

    /*
     * cases[0] = new Case(FORET,FORET,6);
     * cases[1] = new Case(PRE,PRE,10);
     * cases[2] = new Case(CHAMPS,CHAMPS,11);
     * cases[3] = new Case(PRE,PRE,8);
     * cases[4] = new Case(CHAMPS,CHAMPS,4);
     * cases[5] = new Case(COLLINE,COLLINE,10);
     * cases[6] = new Case(FORET,FORET,5);
     * cases[7] = new Case(MONTAGNE,MONTAGNE,12);
     * cases[8] = new Case(MONTAGNE,MONTAGNE,3);
     * cases[9] = new Case(DESERT,DESERT,DESERT);
     * cases[9].setRobber(true);
     * cases[10] = new Case(CHAMPS,CHAMPS,10);
     * cases[11] = new Case(COLLINE,COLLINE,6);
     * cases[12] = new Case(COLLINE,COLLINE,9);
     * cases[13] = new Case(MONTAGNE,MONTAGNE,8);
     * cases[14] = new Case(PRE,PRE,5);
     * cases[15] = new Case(FORET,FORET,2);
     */

    cases = new Case[] {
        new Case(FORET, new Resource(Resource.BOIS), 6),
        new Case(PRE, new Resource(Resource.MOUTON), 10),
        new Case(CHAMPS, new Resource(Resource.BLE), 11),
        new Case(PRE, new Resource(Resource.MOUTON), 8),
        new Case(CHAMPS, new Resource(Resource.BLE), 4),
        new Case(COLLINE, new Resource(Resource.ARGILE), 10),
        new Case(FORET, new Resource(Resource.BOIS), 5),
        new Case(MONTAGNE, new Resource(Resource.PIERRE), 12),
        new Case(MONTAGNE, new Resource(Resource.PIERRE), 3),
        new Case(DESERT, new Resource(Resource.DESERT), -1),
        new Case(CHAMPS, new Resource(Resource.BLE), 10),
        new Case(COLLINE, new Resource(Resource.ARGILE), 6),
        new Case(COLLINE, new Resource(Resource.ARGILE), 9),
        new Case(MONTAGNE, new Resource(Resource.PIERRE), 8),
        new Case(PRE, new Resource(Resource.MOUTON), 5),
        new Case(FORET, new Resource(Resource.BOIS), 2)
    };

    indexRobber = 9;
    cases[indexRobber].setRobber(true);
  }

  private void initIntersections() {
    // Initialiser les intersections du plateau.
    // Initialiser la position des ports.
    /*
     * this.intersections = new Intersection[25];
     * for(int i = 0; i<this.intersections.length; i++){
     * if(i == 0 || i == 3 || i == 4 ||i == 5 || i == 19 || i == 24){
     * this.intersections[i] = new Port(0);
     * }else if(i == 2 || i == 3){
     * this.intersections[i] = new Port(0,PRE);
     * }else if(i == 9 || i == 14){
     * this.intersections[i] = new Port(0,FORET);
     * }else if(i == 10 || i == 15 ){
     * this.intersections[i] = new Port(0,CHAMPS);
     * }else if(i ==20 || i == 21){
     * this.intersections[i] = new Port(0,MONTAGNE);
     * }else if(i ==22 || i == 23){
     * this.intersections[i] = new Port(0,COLLINE);
     * }else{
     * this.intersections[i] = new Intersection(0);
     * }
     * 
     * if(i == 0 || i == 4 || i == 20 || i == 24){
     * this.intersections[i].setCoin(true);
     * }
     * 
     * }
     */
    intersections = new Intersection[] {
        new Intersection(new Building(0), new Port()),
        new Intersection(new Building(0), new Port(new Resource(Resource.MOUTON))),
        new Intersection(new Building(0), new Port(new Resource(Resource.MOUTON))),
        new Intersection(new Building(0), new Port()),
        new Intersection(new Building(0), new Port()),
        new Intersection(new Building(0), new Port()),
        new Intersection(new Building(0), null),
        new Intersection(new Building(0), null),
        new Intersection(new Building(0), null),
        new Intersection(new Building(0), new Port(new Resource(Resource.BOIS))),
        new Intersection(new Building(0), new Port(new Resource(Resource.BLE))),
        new Intersection(new Building(0), null),
        new Intersection(new Building(0), null),
        new Intersection(new Building(0), null),
        new Intersection(new Building(0), new Port(new Resource(Resource.BOIS))),
        new Intersection(new Building(0), new Port(new Resource(Resource.BLE))),
        new Intersection(new Building(0), null),
        new Intersection(new Building(0), null),
        new Intersection(new Building(0), null),
        new Intersection(new Building(0), new Port()),
        new Intersection(new Building(0), new Port(new Resource(Resource.PIERRE))),
        new Intersection(new Building(0), new Port(new Resource(Resource.PIERRE))),
        new Intersection(new Building(0), new Port(new Resource(Resource.ARGILE))),
        new Intersection(new Building(0), new Port(new Resource(Resource.ARGILE))),
        new Intersection(new Building(0), new Port())
    };
  }

  private void initRoads() {
    // Initialiser les aretes du plateau.
  }

  //Getters et Setters
  public Case[] getCases() {return cases;}
  public void setCases(Case[] cases) {this.cases = cases;}
  public Intersection[] getIntersections() {return intersections;}
  public void setIntersections(Intersection[] intersections) {this.intersections = intersections;}
  public Road[] getRoads() {return roads;}
  public void setRoads(Road[] roads) {this.roads = roads;}
  public int getIndexRobber() {return indexRobber;}
  public void setIndexRobber(int indexRobber) {this.indexRobber = indexRobber;} 
}
