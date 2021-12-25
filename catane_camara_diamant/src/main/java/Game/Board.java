package Game;

import java.util.ArrayList;

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
    intersections = new Intersection[] {
        new Intersection(new Building(0), new Port(), new Case[]{cases[0]}),
        new Intersection(new Building(0), new Port(new Resource(Resource.MOUTON)), new Case[]{cases[0], cases[1]}),
        new Intersection(new Building(0), new Port(new Resource(Resource.MOUTON)), new Case[]{cases[1], cases[2]}),
        new Intersection(new Building(0), new Port(), new Case[]{cases[2], cases[3]}),
        new Intersection(new Building(0), new Port(), new Case[]{cases[3]}),
        new Intersection(new Building(0), new Port(), new Case[]{cases[0], cases[4]}),
        new Intersection(new Building(0), null, new Case[]{cases[0], cases[1], cases[4], cases[5]}),
        new Intersection(new Building(0), null, new Case[]{cases[1], cases[2], cases[5], cases[6]}),
        new Intersection(new Building(0), null, new Case[]{cases[2], cases[3], cases[6], cases[7]}),
        new Intersection(new Building(0), new Port(new Resource(Resource.BOIS)), new Case[]{cases[3], cases[7]}),
        new Intersection(new Building(0), new Port(new Resource(Resource.BLE)), new Case[]{cases[4], cases[8]}),
        new Intersection(new Building(0), null, new Case[]{cases[4], cases[5], cases[8], cases[9]}),
        new Intersection(new Building(0), null, new Case[]{cases[5], cases[6], cases[9], cases[10]}),
        new Intersection(new Building(0), null, new Case[]{cases[6], cases[7], cases[10], cases[11]}),
        new Intersection(new Building(0), new Port(new Resource(Resource.BOIS)), new Case[]{cases[7], cases[11]}),
        new Intersection(new Building(0), new Port(new Resource(Resource.BLE)), new Case[]{cases[8], cases[12]}),
        new Intersection(new Building(0), null, new Case[]{cases[8], cases[9], cases[12], cases[13]}),
        new Intersection(new Building(0), null, new Case[]{cases[9], cases[10], cases[13], cases[14]}),
        new Intersection(new Building(0), null, new Case[]{cases[10], cases[11], cases[14], cases[15]}),
        new Intersection(new Building(0), new Port(), new Case[]{cases[11], cases[15]}),
        new Intersection(new Building(0), new Port(new Resource(Resource.PIERRE)), new Case[]{cases[12]}),
        new Intersection(new Building(0), new Port(new Resource(Resource.PIERRE)), new Case[]{cases[12], cases[13]}),
        new Intersection(new Building(0), new Port(new Resource(Resource.ARGILE)), new Case[]{cases[13], cases[14]}),
        new Intersection(new Building(0), new Port(new Resource(Resource.ARGILE)), new Case[]{cases[14], cases[15]}),
        new Intersection(new Building(0), new Port(), new Case[]{cases[15]})
    };
  }

  private void initRoads() {
    // Initialiser les aretes du plateau.
    roads =new Road[]{
      new Road(0,1), new Road(0,5),
      new Road(1,2), new Road(1,6),
      new Road(2,3), new Road(2,7),
      new Road(3,4), new Road(3,8),
      new Road(4,9),
      new Road(5,6), new Road(5,10),
      new Road(6,7), new Road(6,11),
      new Road(7,8), new Road(7,12),
      new Road(8,9), new Road(8,13),
      new Road(9,14),
      new Road(10,11), new Road(10,15),
      new Road(11,12), new Road(11,16),
      new Road(12,13), new Road(12,17),
      new Road(13,14), new Road(13,18),
      new Road(14,19),
      new Road(15,16), new Road(15,20),
      new Road(16,17), new Road(16,21),
      new Road(17,18), new Road(17,22),
      new Road(18,19), new Road(18,23),
      new Road(19,24),
      new Road(20,21),
      new Road(21,22),
      new Road(22,23),
      new Road(23,24)
    };
  }

  public Road getSpecifiedRoad(int id1, int id2){
    Road rep=null;
    for(Road r : roads){
      if(r.getId1()==id1 && r.getId2()==id2 || r.getId1()==id2 && r.getId2()==id1){
        rep=r;
        break;
      }
    }
    return rep;
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
