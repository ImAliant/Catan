package Game;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private String name;
    private int color;
    private int[] playerResources; //(index) 0: bois | 1: pierre | 2: ble | 3: mouton | 4: argile
    private int victoryPoint;
    
    public Player(String name, int color){
        this.name=name;
        this.color=color;
        playerResources=new int[]{0,0,0,0,0}; 
        victoryPoint=0;
    }

    public void addResourcesInv(int resourceType){
        playerResources[resourceType]++;
    }
}
