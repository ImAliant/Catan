
public class Intersection {
    private static int id = -1;
    private Building building;
    private Player player;
    private boolean coin; // pour ne pas permettre au joueur la construction de building et road

    public Intersection(Building building){
        this.id++;
        this.building=building;
        player=null;
        coin = false;
    }


public void setCoin(boolean b){
  this.coin = b;
}

}
