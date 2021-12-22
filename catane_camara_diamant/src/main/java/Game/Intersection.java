package Game;

public class Intersection {
    private static int id = -1;
    private Building building;
    private Player player;
    private Port port;

    public Intersection(Building building, Port port){
        id++;
        this.building=building;
        this.port=port;
        player=null;
    }

    //Getters et Setters
    public int getId() {return id;}
    public Building getBuilding() {return building;}
    public Player getPlayer() {return player;}
    public void setPlayer(Player player) {this.player = player;}
    public void setBuilding(Building building) {this.building = building;}
    public Port getPort(){return port;}
    public void setPort(Port port) {this.port = port;}
}
