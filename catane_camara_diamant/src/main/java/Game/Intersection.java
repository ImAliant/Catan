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
}
