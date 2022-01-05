package Game;

import java.util.ArrayList;

public class Board {

    //Correspond aux types de terrain disponibles pour les cases.
    public static final int DESERT = -1;
    public static final int FORET = 0;
    public static final int MONTAGNE = 1;
    public static final int CHAMPS = 2;
    public static final int PRE = 3;
    public static final int COLLINE = 4;
    public static final int TOUT = 5;


    private Case[] cases;
    private Intersection[] intersections;
    private Port[] ports;
    private Road[] roads;
    private int indexRobber;

    public Board() {
        init();
    }

    private void init() {
        initCases();
        initPortsLocation();
        initIntersections();
        initCasesIntersectionAdj();
        initRoads();
    }

    // Initialise le tableau de cases et l'index du voleur.
    // Le plateau a initialisé sera le plateau present sur le sujet.
    private void initCases() {
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

    private void initPortsLocation(){
        ports =new Port[] {
            new Port(new Resource(Resource.MOUTON)),
            new Port(),
            new Port(),
            new Port(new Resource(Resource.BOIS)),
            new Port(new Resource(Resource.BLE)),
            new Port(),
            new Port(new Resource(Resource.PIERRE)),
            new Port(new Resource(Resource.ARGILE))
        };
    }

    // Initialise les intersections du plateau.
    private void initIntersections() {
        intersections = new Intersection[] {
            new Intersection(new Building(0), ports[2], new Case[]{cases[0]}),
            new Intersection(new Building(0), ports[0], new Case[]{cases[0], cases[1]}),
            new Intersection(new Building(0), ports[0], new Case[]{cases[1], cases[2]}),
            new Intersection(new Building(0), ports[1], new Case[]{cases[2], cases[3]}),
            new Intersection(new Building(0), ports[1], new Case[]{cases[3]}),
            new Intersection(new Building(0), ports[2], new Case[]{cases[0], cases[4]}),
            new Intersection(new Building(0), null, new Case[]{cases[0], cases[1], cases[4], cases[5]}),
            new Intersection(new Building(0), null, new Case[]{cases[1], cases[2], cases[5], cases[6]}),
            new Intersection(new Building(0), null, new Case[]{cases[2], cases[3], cases[6], cases[7]}),
            new Intersection(new Building(0), ports[3], new Case[]{cases[3], cases[7]}),
            new Intersection(new Building(0), ports[4], new Case[]{cases[4], cases[8]}),
            new Intersection(new Building(0), null, new Case[]{cases[4], cases[5], cases[8], cases[9]}),
            new Intersection(new Building(0), null, new Case[]{cases[5], cases[6], cases[9], cases[10]}),
            new Intersection(new Building(0), null, new Case[]{cases[6], cases[7], cases[10], cases[11]}),
            new Intersection(new Building(0), ports[3], new Case[]{cases[7], cases[11]}),
            new Intersection(new Building(0), ports[4], new Case[]{cases[8], cases[12]}),
            new Intersection(new Building(0), null, new Case[]{cases[8], cases[9], cases[12], cases[13]}),
            new Intersection(new Building(0), null, new Case[]{cases[9], cases[10], cases[13], cases[14]}),
            new Intersection(new Building(0), null, new Case[]{cases[10], cases[11], cases[14], cases[15]}),
            new Intersection(new Building(0), ports[5], new Case[]{cases[11], cases[15]}),
            new Intersection(new Building(0), ports[6], new Case[]{cases[12]}),
            new Intersection(new Building(0), ports[6], new Case[]{cases[12], cases[13]}),
            new Intersection(new Building(0), ports[7], new Case[]{cases[13], cases[14]}),
            new Intersection(new Building(0), ports[7], new Case[]{cases[14], cases[15]}),
            new Intersection(new Building(0), ports[5], new Case[]{cases[15]})
        };
    }

    //Initialise les cases adjacentes aux intersections.
    private void initCasesIntersectionAdj(){
        cases[0].setCaseIntersections(new Intersection[]{intersections[0],intersections[1],intersections[5],intersections[6]});
        cases[1].setCaseIntersections(new Intersection[]{intersections[1],intersections[2],intersections[6],intersections[7]});
        cases[2].setCaseIntersections(new Intersection[]{intersections[2],intersections[3],intersections[7],intersections[8]});
        cases[3].setCaseIntersections(new Intersection[]{intersections[3],intersections[4],intersections[8],intersections[9]});
        cases[4].setCaseIntersections(new Intersection[]{intersections[5],intersections[6],intersections[10],intersections[11]});
        cases[5].setCaseIntersections(new Intersection[]{intersections[6],intersections[7],intersections[11],intersections[12]});
        cases[6].setCaseIntersections(new Intersection[]{intersections[7],intersections[8],intersections[12],intersections[13]});
        cases[7].setCaseIntersections(new Intersection[]{intersections[8],intersections[9],intersections[13],intersections[14]});
        cases[8].setCaseIntersections(new Intersection[]{intersections[10],intersections[11],intersections[15],intersections[16]});
        cases[9].setCaseIntersections(new Intersection[]{intersections[11],intersections[12],intersections[16],intersections[17]});
        cases[10].setCaseIntersections(new Intersection[]{intersections[12],intersections[13],intersections[17],intersections[18]});
        cases[11].setCaseIntersections(new Intersection[]{intersections[13],intersections[14],intersections[18],intersections[19]});
        cases[12].setCaseIntersections(new Intersection[]{intersections[15],intersections[16],intersections[20],intersections[21]});
        cases[13].setCaseIntersections(new Intersection[]{intersections[16],intersections[17],intersections[21],intersections[22]});
        cases[14].setCaseIntersections(new Intersection[]{intersections[17],intersections[18],intersections[22],intersections[23]});
        cases[15].setCaseIntersections(new Intersection[]{intersections[18],intersections[19],intersections[23],intersections[24]});
    }

    // Initialise les aretes du plateau.
    private void initRoads() {
        roads =new Road[]{
            new Road(0,1), new Road(1,2), new Road(2,3), new Road(3,4),
            new Road(5,6), new Road(6,7), new Road(7,8), new Road(8,9),
            new Road(10,11), new Road(11,12), new Road(12,13), new Road(13,14),
            new Road(15,16), new Road(16,17), new Road(17,18), new Road(18,19),
            new Road(20,21), new Road(21,22), new Road(22,23), new Road(23,24),
            new Road(0,5),
            new Road(1,6),
            new Road(2,7),
            new Road(3,8),
            new Road(4,9),
            new Road(5,10),
            new Road(6,11),
            new Road(7,12),
            new Road(8,13),
            new Road(9,14),
            new Road(10,15),
            new Road(11,16),
            new Road(12,17),
            new Road(13,18),
            new Road(14,19),
            new Road(15,20),
            new Road(16,21),
            new Road(17,22),
            new Road(18,23),
            new Road(19,24),
        };
    }

    //Retourne l'objet Road qui a comme IDs id1 et id2 (si elle existe sinon null).
    public Road getSpecificRoad(int id1, int id2){
        Road rep=null;
        for(Road r : roads){
            if(r.getId1()==id1 && r.getId2()==id2 || r.getId1()==id2 && r.getId2()==id1){
                rep=r;
                break;
            }
        }
        return rep;
    }

    //Retourne une arraylist qui contient toutes les intersections qui sont vides.
    public ArrayList<Intersection> getEmptyIntersection(){
        ArrayList<Intersection> emptyIntersection=new ArrayList<Intersection>();

        for(Intersection inter : getIntersections()){
            if(inter.getPlayer()==null){
                emptyIntersection.add(inter);
            }
        }
        return emptyIntersection;
    }

    //Retourne une arraylist qui contient toutes les arêtes qui sont vides.
    public ArrayList<Road> getEmptyRoad(){
        ArrayList<Road> emptyRoadID=new ArrayList<Road>();

        for(Road road : getRoads()){
            if(road.getPlayer()==null){
                emptyRoadID.add(road);
            }
        }
        return emptyRoadID;
    }

    //Retourne une arraylist de case où ne se trouve pas le voleur.
    public ArrayList<Case> caseWithoutRobber(){
        ArrayList<Case> isNotRobber =new ArrayList<Case>();

        for(Case c : getCases()){
            if(!c.isRobber()){
                isNotRobber.add(c);
            }
        }

        return isNotRobber;
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
    public Port[] getPorts() {return ports;}
    public void setPorts(Port[] ports) {this.ports = ports;} 
}
