package Game;

public class Board {
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
        //Le plateau a initialiser sera le plateau pr�sent sur le sujet.
        //Pour l'instant pas de plateau initialis� al�atoirement.
    }
    
    private void initIntersections(){
        //Initialiser les intersections du plateau.
        //Initialiser la position des ports.
    }
    
    private void initRoads(){
        //Initialiser les ar�tes du plateau.
    }
    
}
