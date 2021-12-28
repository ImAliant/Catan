package Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Player {
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
    private int knightPlayed;

    private Scanner scan=new Scanner(System.in);
    private Random rand =new Random();
    
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
        knightPlayed=0;
    }

    public abstract void turn(Board board, Game game);

    public abstract void moveRobber(Board board, Game game);

    public void buildSettlement(int id, Board board, int turn){ //HUMAIN ET IA
        if(id >= 0 && id <=24){
            if(board.getIntersections()[id].getBuilding().upgradeToSettlements()){
                settlements.add(board.getIntersections()[id]);
                board.getIntersections()[id].setPlayer(this);
                if(turn!=0){
                    removeResource(Resource.BOIS, 1);
                    removeResource(Resource.ARGILE, 1);
                    removeResource(Resource.BLE, 1);
                    removeResource(Resource.MOUTON, 1);
                }
                System.out.println("Colonie construite en ("+id+").");
                victoryPoint+=1;
            }
            else{
                int rep=0;
                if(this instanceof Human){
                    System.out.println("Cette intersection ne peut pas être amélioré en colonie !");
                    System.out.println("Saississez un nouvel id (0 à 24) : ");
                    rep=scan.nextInt();
                }
                else{
                    rep=0+rand.nextInt(24-0);
                }
                buildSettlement(rep, board, turn);
            }
        }
        else{
            int rep=0;
            if(this instanceof Human){
                System.out.println("Cette intersection n'existe pas !");
                System.out.println("Saississez un nouvel id (0 à 24) : ");
                rep=scan.nextInt();
            }
            else{
                rep=0+rand.nextInt(24-0);
            }
            buildSettlement(rep, board, turn);
        }
    }

    public void buildCity(int id, Board board){
        if(id >= 0 && id <=24){
            if(board.getIntersections()[id].getBuilding().upgradeToCity()){
                cities.add(board.getIntersections()[id]);
                settlements.remove(board.getIntersections()[id]);
                removeResource(Resource.BLE, 2);
                removeResource(Resource.PIERRE, 3);
                victoryPoint+=2;
                System.out.println("Ville construite en ("+id+").\n");
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

    public void buildRoad(int id1, int id2, Board board, int turn, Game game){ //HUMAIN ET IA
        if((id1 >= 0 && id1 <= 24) && (id2 >= 0 && id2 <= 24)){
            if(board.getSpecificRoad(id1, id2)!=null){
                if(board.getSpecificRoad(id1, id2).upgradeRoad(this)){
                    if(turn!=0){
                        removeResource(Resource.BOIS, 1);
                        removeResource(Resource.ARGILE, 1);
                    }
                    roads.add(board.getSpecificRoad(id1, id2));
                    System.out.println("Route construite en ("+id1+" "+id2+").\n");
                }
                else{
                    int rep1=0;
                    int rep2=0;
                    if(this instanceof Human){
                        System.out.println("Cette arête possède déjà une route !");
                        System.out.println("Saississez deux nouveaux id adjacents (0 à 24) : ");
                        rep1=scan.nextInt();
                        rep2=scan.nextInt();
                    }
                    else{
                        rep1=settlements.get(rand.nextInt(settlements.size())).getId();
                        
                        ArrayList<Integer> canBuilRoad = game.intersectionsRoadIA(rep1);

                        rep2=canBuilRoad.get(ThreadLocalRandom.current().nextInt(0, canBuilRoad.size()));
                    }
                    buildRoad(rep1, rep2, board, turn, game);
                }
            }
            else{
                int rep1=0;
                int rep2=0;
                if(this instanceof Human){
                    System.out.println("Cette route n'existe pas !");
                    System.out.println("Saississez deux nouveaux id adjacents (0 à 24) : ");
                    rep1=scan.nextInt();
                    rep2=scan.nextInt();
                }
                else{
                    rep1=0+rand.nextInt(24-0);
                    rep2=0+rand.nextInt(24-0);
                }
                buildRoad(rep1, rep2, board, turn, game);
            }
        }
        else{
            int rep1=0;
            int rep2=0;
            if(this instanceof Human){
                System.out.println("Cette route n'existe pas !");
                System.out.println("Saississez deux nouveaux id adjacents (0 à 24) : ");
                rep1=scan.nextInt();
                rep2=scan.nextInt();
            }
            else{
                rep1=0+rand.nextInt(24-0);
                rep2=0+rand.nextInt(24-0);
            }
            buildRoad(rep1, rep2, board, turn, game);
        }
    }

    public boolean resourceForSettlement(){
        if(playerResources[Resource.BOIS]>=1 && playerResources[Resource.ARGILE]>=1 && playerResources[Resource.BLE]>=1 && playerResources[Resource.MOUTON]>=1){
            return true;
        }
        return false;
    }

    public boolean resourceForCity(){
        if(playerResources[Resource.BLE]>=2 && playerResources[Resource.PIERRE]>=3){
            return true;
        }
        return false;
    }

    public boolean resourceForRoad(){
        if(playerResources[Resource.BOIS]>=1 && playerResources[Resource.ARGILE]>=1){
            return true;
        }
        return false;
    }

    public boolean resourceForDevCard(){
        if(playerResources[Resource.BLE]>=1 && playerResources[Resource.MOUTON]>=1 && playerResources[Resource.PIERRE]>=1){
            return true;
        }
        return false;
    }

    public ArrayList<Port> getPortsOfPlayer(){
        ArrayList<Port> portOfPlayer=new ArrayList<Port>();
        for(int i=0; i<settlements.size(); i++){
            if(settlements.get(i).getPort()!=null){
                portOfPlayer.add(settlements.get(i).getPort());
            }
        }
        for(int i=0; i<cities.size(); i++){
            if(cities.get(i).getPort()!=null){
                portOfPlayer.add(settlements.get(i).getPort());
            }
        }
        return portOfPlayer;
    }

    public boolean hasPort(){
        if(getPortsOfPlayer().isEmpty())
            return false;
        return true;
    }

    public ArrayList<Port> getPortsType2OfPlayer(){
        ArrayList<Port> portType2OfPlayer =new ArrayList<Port>();
        for(int i=0; i<getPortsOfPlayer().size(); i++){
            if(getPortsOfPlayer().get(i).getPortType()==0)
                portType2OfPlayer.add(getPortsOfPlayer().get(i));
        }
        return portType2OfPlayer;
    }

    public boolean hasType2Port(){
        if(getPortsType2OfPlayer().isEmpty())
            return false;
        return true;
    }

    public ArrayList<Port> getPortsType3OfPlayer(){
        ArrayList<Port> portType3OfPlayer =new ArrayList<Port>();
        for(int i=0; i<getPortsOfPlayer().size(); i++){
            if(getPortsOfPlayer().get(i).getPortType()==1)
                portType3OfPlayer.add(getPortsOfPlayer().get(i));
        }
        return portType3OfPlayer;
    }

    public boolean hasType3Port(){
        if(getPortsType3OfPlayer().isEmpty())
            return false;
        return true;
    }

    public void collectResources(int resourceType, int nb){
        if(resourceType!=-1)
            playerResources[resourceType]++;
    }

    public void removeResource(int resourceType, int nb){
        playerResources[resourceType] -= nb;
    }

    public void removeResourceForDevCard(){
        removeResource(Resource.BLE, 1);
        removeResource(Resource.MOUTON, 1);
        removeResource(Resource.PIERRE, 1);
    }

    public void removeResourceForSettlements(){
        removeResource(Resource.BOIS, 1);
        removeResource(Resource.ARGILE, 1);
        removeResource(Resource.BLE, 1);
        removeResource(Resource.MOUTON, 1);
    }

    public void removeResourceForCities(){
        removeResource(Resource.BLE, 2);
        removeResource(Resource.PIERRE, 3);
    }

    public void removeResourceForRoad(){
        removeResource(Resource.BOIS, 1);
        removeResource(Resource.ARGILE, 1);
    }

    public String resourceOfPlayerToString(){
        String s="";
        s+="Ressource du " + name + " : [";
        for(int resource : playerResources){
            s+=(resource+" ");
        }
        s+="] (bois/pierre/ble/mouton/argile)";
        return s;
    }

    public boolean hasOneSpecificResources(int resource){
        if(playerResources[resource]>=1)
            return true;
        return false;
    }

    public boolean hasTwoSpecificResources(int resource){
        if(playerResources[resource]>=2)
            return true;
        return false;
    }

    public boolean hasThreeSpecificResources(int resource){
        if(playerResources[resource]>=3)
            return true;
        return false;
    }

    public boolean hasFourSpecificResources(int resource){
        if(playerResources[resource]>=4)
            return true;
        return false;
    }

    public boolean hasTwoResources(){
        for(int resource : playerResources){
            if(resource==2)
                return true;
        }
        return false;
    }

    public boolean hasThreeResources(){
        for(int resource : playerResources){
            if(resource==3)
                return true;
        }
        return false;
    }

    public boolean hasFourResources(){
        for(int resource : playerResources){
            if(resource==4)
                return true;
        }
        return false;
    }

    public boolean hasDevCard(){
        if(cards.isEmpty())
            return false;
        return true;
    }

    public int totalResource(){
        int total=0;
        for(int resource : playerResources){
            total+=resource;
        }
        return total;
    }

    public void addDevCard(DevCard devCard){
        cards.add(devCard);
    }

    public void removeDevCard(DevCard devCard){
        cards.remove(devCard);
    }

    public Intersection lastSettlements(){
        return settlements.get(settlements.size()-1);
    }

    public void addVictoryPoint(int nb){
        victoryPoint += nb;
    }

    public void removeVictoryPoint(int nb){
        victoryPoint -= nb;
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
    public int getKnightPlayed() {return knightPlayed;}
    public void setKnightPlayed(int knightPlayed) {this.knightPlayed = knightPlayed;}
    
}
