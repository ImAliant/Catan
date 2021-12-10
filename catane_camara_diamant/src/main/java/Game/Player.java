package Game;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private String name;
    private int color;
    private ArrayList<Integer> playerResources;
    private int victoryPoint;
    
    public Player(String name, int color){
        this.name=name;
        this.color=color;
        playerResources=new ArrayList<>(Arrays.asList(0,0,0,0,0));
        victoryPoint=0;
    }
}
