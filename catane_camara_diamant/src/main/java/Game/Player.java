

import java.util.ArrayList;
import java.util.Scanner;

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

    private Scanner scan=new Scanner(System.in);
    
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

    public void buildSettlement(int id, Board board){
        if(id >= 0 && id <=24){
            if(board.getIntersections()[id].getBuilding().upgradeToSettlements()){
                settlements.add(board.getIntersections()[id]);
                victoryPoint+=1;
            }
            else{
                System.out.println("Cette intersection ne peut pas être amélioré en colonie !");
                System.out.println("Saississez un nouvel id (0 à 24) : ");
                int rep=scan.nextInt();
                buildSettlement(rep, board);
            }
        }
        else{
            System.out.println("Cette intersection n'existe pas !");
            System.out.println("Saississez un nouvel id (0 à 24) : ");
            int rep=scan.nextInt();
            buildSettlement(rep, board);
        }
    }

    public void buildCity(int id, Board board){
        if(id >= 0 && id <=24){
            if(board.getIntersections()[id].getBuilding().upgradeToCity()){
                cities.add(board.getIntersections()[id]);
                settlements.remove(board.getIntersections()[id]);
                victoryPoint+=2;
            }
            else{
                System.out.println("Cette colonie ne peut pas être amélioré en ville !");
                System.out.println("Saississez un nouvel id (0 à 24) : ");
                int rep=scan.nextInt();
                buildCity(rep, board);
            }
        }
        else{
            System.out.println("Cette intersection n'existe pas !");
            System.out.println("Saississez un nouvel id (0 à 24) : ");
            int rep=scan.nextInt();
            buildCity(rep, board);
        }
    }

    public void buildRoad(int id1, int id2, Board board){
        if((id1 >= 0 && id1 <= 24) && (id2 == id1+1 || id2 == id1-1)){
            if(board.getSpecifiedRoad(id1, id2).upgradeRoad(this))
                roads.add(board.getSpecifiedRoad(id1, id2));
            else{
                System.out.println("Cette arête possède déjà une route !");
                System.out.println("Saississez deux nouveaux id adjacents (0 à 24) : ");
                int rep1=scan.nextInt();
                int rep2=scan.nextInt();
                buildRoad(rep1, rep2, board);
            }
        }
        else{
            System.out.println("Cette route n'existe pas !");
            System.out.println("Saississez deux nouveaux id adjacents (0 à 24) : ");
            int rep1=scan.nextInt();
            int rep2=scan.nextInt();
            buildRoad(rep1, rep2, board);
        }
    }

    public void collectResources(int resourceType){
        playerResources[resourceType]++;
    }

    public Intersection lastSettlements(){
        return settlements.get(settlements.size()-1);
    }

    @Override
    public String toString(){
        return name;
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
    public ArrayList<Road> getRoads() {return roads;}
    public void setRoads(ArrayList<Road> roads) {this.roads = roads;}
    public ArrayList<Intersection> getSettlements() {return settlements;}
    public void setSettlements(ArrayList<Intersection> settlements) {this.settlements = settlements;}
    public ArrayList<Intersection> getCities() {return cities;}
    public void setCities(ArrayList<Intersection> cities) {this.cities = cities;}
    public boolean isLongestRoad() {return longestRoad;}
    public void setLongestRoad(boolean longestRoad) {this.longestRoad = longestRoad;}
    public boolean isStrongestKnight() {return strongestKnight;}
    public void setStrongestKnight(boolean strongestKnight) {this.strongestKnight = strongestKnight;}
}
