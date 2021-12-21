package Game;

public class Board {

    public static final int DESERT=-1;
    public static final int FORET=0;
    public static final int MONTAGNE=1;
    public static final int CHAMPS=2;
    public static final int PRE=3;
    public static final int COLLINE=4;

    private Case[] cases;
    private Intersection[] intersections;
    private Road[] roads;
    private int indexRobber;
    
    public Board(){
        init();
    }
    
    private void init(){
        initCases();
        initIntersections();
        initRoads();
    }
    
    private void initCases(){
        //Initialiser le tableau de cases et l'index du voleur.
        //Le plateau a initialiser sera le plateau present sur le sujet.
        //Pour l'instant pas de plateau initialise aleatoirement.
    }
    
    private void initIntersections(){
        //Initialiser les intersections du plateau.
        //Initialiser la position des ports.
    }
    
    private void initRoads(){
        //Initialiser les aretes du plateau.
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
