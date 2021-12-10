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
        //Le plateau a initialiser sera le plateau présent sur le sujet.
        //Pour l'instant pas de plateau initialisé aléatoirement.
    }
    
    private void initIntersections(){
        //Initialiser les intersections du plateau.
        //Initialiser la position des ports.
    }
    
    private void initRoads(){
        //Initialiser les arêtes du plateau.
    }
    
}
