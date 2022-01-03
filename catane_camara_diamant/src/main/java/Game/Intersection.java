

public class Intersection {
    private static int nextId = -1;

    private int id;
    private Building building;
    private Player player;
    private Port port;

    private final Case[] caseAdj;

    public Intersection(Building building, Port port, Case[] caseAdj){
        id=nextId++;
        this.building=building;
        this.port=port;
        player=null;
        this.caseAdj=caseAdj;
    }

    //Getters et Setters
    public int getId() {return id;}
    public Building getBuilding() {return building;}
    public Player getPlayer() {return player;}
    public void setPlayer(Player player) {this.player = player;}
    public void setBuilding(Building building) {this.building = building;}
    public Port getPort(){return port;}
    public void setPort(Port port) {this.port = port;}
    public Case[] getCaseAdj() {return caseAdj;}
}
