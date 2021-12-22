package Game;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private String name;
    private int color;


    private int[] playerResources; //(index) 0: bois | 1: pierre | 2: ble | 3: mouton | 4: argile
    private ArrayList<Road> roads;
    private ArrayList<Intersection> settlements;
    private ArrayList<Intersection> cities;
    private ArrayList<DevCard> cards;
    private boolean longestRoad;
    private boolean strongestKnight;

    private int victoryPoint;
    
    public Player(String name, int color){
        this.name=name;
        this.color=color;
        playerResources=new int[]{0,0,0,0,0}; 

        roads =new ArrayList<Road>();
        settlements =new ArrayList<Intersection>();
        cities =new ArrayList<Intersection>();
        cards =new ArrayList<DevCard>();

        longestRoad=false;
        strongestKnight=false;
        victoryPoint=0;
    }

    public void buildSettlement(){}

    public void buildCity(){}

    public void buildRoad(){}

    public void collectResources(int resourceType){
        playerResources[resourceType]++;
    }

    //Getters et Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getColor() {return color;}
    public void setColor(int color) {this.color = color;}
    public int[] getPlayerResources() {return playerResources;}
    public void setPlayerResources(int[] playerResources) {this.playerResources = playerResources;}
    public ArrayList<DevCard> getCards() {return cards;}
    public void setCards(ArrayList<DevCard> cards) {this.cards = cards;}
    public int getVictoryPoint() {return victoryPoint;}
    public void setVictoryPoint(int victoryPoint) {this.victoryPoint = victoryPoint;}
}
