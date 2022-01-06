package Game;

public class Road {
    private static int nextId = 0;

    private final int id;

    private final int id1;
    private final int id2;
    private boolean road;
    private Player player;

    public Road(int id1, int id2){
        id=nextId++;
        this.id1=id1;
        this.id2=id2;
        road=false;
        player=null;
    }

    public boolean canUpgradeRoad(){
        if(!road){
            return true;
        }
        return false;
    }

    public void destroyRoad(){
        if(road){
            road=false;
            player=null;
        }
    }
    
    public boolean upgradeRoad(Player p){
        if(!canUpgradeRoad()) return false;
        
        road=true;
        player=p;
        return true;
    }

    public String toString(){
        return "("+id1+", "+id2+")";
    }

    //Getters et Setters
    public int getId1() {return id1;}
    public int getId2() {return id2;}
    public boolean isRoad() {return road;}
    public void setRoad(boolean road) {this.road = road;}
    public Player getPlayer() {return player;}
    public void setPlayer(Player player) {this.player = player;}
    public int getId() {return id;}
}
