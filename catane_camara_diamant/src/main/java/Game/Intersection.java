package Game;

public class Intersection {
    private int id;
    private Building building;
    private Player player;
    
    public Intersection(int id, Building building){
        this.id=id;
        this.building=building;
        player=null;
    }

    //Getters et Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public Building getBuilding() {return building;}
    public Player getPlayer() {return player;}
    public void setPlayer(Player player) {this.player = player;}
}
