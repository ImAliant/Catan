package Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * <b>Player est la classe correspondant aux joueurs présent dans la partie.</b>
 * <p>
 * Player est une classe abstraite et est caractérisé par les champs suivants:
 * <ul>
 * <li>Une chaine de caractère correspondant au nom du joueur: {@link #name}</li>
 * <li>Un entier correspondant a la couleur du joueur: {@link #color}</li>
 * <li>Un booléen qui correspond a la victoire du joueur: {@link #winner}</li>
 * <li>Un tableau d'entiers correspondants aux ressources du joueur: {@link #playerResources}</li>
 * <li>Plusieurs arraylist correspondants aux possessions du joueur, c'est les routes, les colonies, les villes et
 * les cartes de développements: {@link #roads}, {@link #settlements}, {@link #cities}, {@link #cards}</li>
 * <li>Un booléen correspondant a la possession de "la route la plus longue": {@link #longestRoad}</li>
 * <li>Un booléen correspondant a la possession du "chevalier le plus fort": {@link #strongestKnight}</li>
 * <li>Un entier correspondant au point de victoire du joueur: {@link #victoryPoint}</li>
 * <li>Un entier correspondant au nombre de chevalier joué par le joueur: {@link #knightPlayed}</li>
 * <li>Un scanner et un random: {@link #scan}, {@link #rand}</li>
 * </ul>
 * </p>
 * 
 * @see Road
 * @see Intersection
 * @see DevCard
 * 
 * @author CAMARA Ibrahime, DIAMANT Alexandre
 */
public abstract class Player {
    /**
     * Nom du joueur.
     */
    private String name;
    /**
     * Couleur du joueur.
     */
    private int color;
    /**
     * False, sauf si le joueur a gagné la partie.
     */
    private boolean winner;
    /**
     * Ressources du joueur:
     * <p>
     * Index:
     * <ul>
     * <li>0: BOIS</li>
     * <li>1: PIERRE</li>
     * <li>2: BLE</li>
     * <li>3: MOUTON</li>
     * <li>4: ARGILE</li>
     * </ul>
     * </p>
     */
    private int[] playerResources;
    /**
     * Routes que possède le joueur.
     */
    private ArrayList<Road> roads;
    /**
     * Colonies que possède le joueur.
     */
    private ArrayList<Intersection> settlements;
    /**
     * Villes que possède le joueur.
     */
    private ArrayList<Intersection> cities;
    /**
     * Carte de développement que possède le joueur.
     */
    private ArrayList<DevCard> cards;
    /**
     * False, sauf si le joueur possède la plus longue route.
     */
    private boolean longestRoad;
    /**
     * False, sauf si le joueur possède le chevalier le plus fort.
     */
    private boolean strongestKnight;
    /**
     * Point du joueur.
     */
    private int victoryPoint;
    /**
     * Nombre de carte "chevalier" joué par le joueur.
     */
    private int knightPlayed;

    private Scanner scan=new Scanner(System.in);
    private Random rand =new Random();
    
    /**
     * <b>Constructeur Player</b>
     * <p>
     * A la construction d'un objet Player, un nom et une couleur est attribué au joueur.
     * {@link #winner} est initialisé a false, toutes les ressources du joueurs dans 
     * {@link #playerResources} sont égales a 0. Les arraylists sont initialisé vide.
     * Le joueur ne possède pas la plus longue route ni le chevalier le plus fort, ses points 
     * et le nombre de chevalier joué sont initialisés a 0.
     * </p>
     * @param name
     *      Nom du joueur.
     * @param color
     *      Couleur du joueur.
     */
    public Player(String name, int color){
        this.name=name;
        this.color=color;

        winner=false;

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
    /**
     * Méthode abstraite qui doit réalisé le tour du joueur.
     * 
     * @param board
     *      Plateau de la partie.
     * @param game
     *      Partie.
     * @throws Exception
     */
    public abstract void turn(Board board, Game game) throws Exception;
    /**
     * Méthode abstraite qui doit réalisé le mouvement du voleur.
     * @param board
     *      Plateau de la partie.
     * @param game
     *      Partie.
     */
    public abstract void moveRobber(Board board, Game game);
    /**
     * Méthode abstraite qui doit construire une colonie sur l'id d'une intersection donné.
     * 
     * @param id
     *      ID de l'intersection.
     * @param board
     *      Plateau de la partie.
     * @param turn
     *      Tour de la partie.
     */
    public abstract void buildSettlement(int id, Board board, int turn);
    /**
     * Méthode abstraite qui doit construire une ville sur l'id d'une intersection où est déjà présente une colonie.
     * @param id
     *      ID de l'intersection.
     * @param board
     *      Plateau de la partie.
     */
    public abstract void buildCity(int id, Board board);
    /**
     * Méthode abstraite qui doit construire une route sur une arête correspondant aux IDs donnés en paramètre.
     * @param id1
     *      ID de la première intersection lié à l'arête.
     * @param id2
     *      ID de la seconde intersection lié à l'arête.
     * @param board
     *      Plateau de la partie.
     * @param turn
     *      Tour de la partie.
     * @param game
     *      Partie.
     */
    public abstract void buildRoad(int id1, int id2, Board board, int turn,  Game game);

    /**
     * Retourne vrai si le joueur a assez de ressources pour construire une colonie.
     * @return True si il peut construire une colonie, False sinon.
     */
    public boolean resourceForSettlement(){
        if(playerResources[Resource.BOIS]>=1 && playerResources[Resource.ARGILE]>=1 && playerResources[Resource.BLE]>=1 && playerResources[Resource.MOUTON]>=1){
            return true;
        }
        return false;
    }
    /**
     * Retourne vrai si le joueur a assez de ressources pour construire une ville.
     * @return True si il peut construire une ville, False sinon.
     */
    public boolean resourceForCity(){
        if(playerResources[Resource.BLE]>=2 && playerResources[Resource.PIERRE]>=3){
            return true;
        }
        return false;
    }
    /**
     * Retourne vrai si le joueur a assez de ressources pour construire une route.
     * @return True si il peut construire une route, False sinon.
     */
    public boolean resourceForRoad(){
        if(playerResources[Resource.BOIS]>=1 && playerResources[Resource.ARGILE]>=1){
            return true;
        }
        return false;
    }
    /**
     * Retourne vrai si le joueur a assez de ressources pour acheter une carte de développement.
     * @return True si il peut cacheter une carte de développement, False sinon.
     */
    public boolean resourceForDevCard(){
        if(playerResources[Resource.BLE]>=1 && playerResources[Resource.MOUTON]>=1 && playerResources[Resource.PIERRE]>=1){
            return true;
        }
        return false;
    }
    /**
     * Retourne les ports du joueur. S'il n'en a pas retourne une arraylist vide.
     * @return Une arraylist contenant les ports du joueur.
     */
    public ArrayList<Port> getPortsOfPlayer(){
        ArrayList<Port> portOfPlayer=new ArrayList<Port>();
        if(!settlements.isEmpty()){
            for(int i=0; i<settlements.size(); i++){
                if(settlements.get(i).getPort()!=null){
                    portOfPlayer.add(settlements.get(i).getPort());
                }
            }
        }
        if(!cities.isEmpty()){
            for(int i=0; i<cities.size(); i++){
                if(cities.get(i).getPort()!=null){
                    portOfPlayer.add(cities.get(i).getPort());
                }
            }
        }
        return portOfPlayer;
    }
    /**
     * Retourne vrai si le joueur possède au moins un port
     * @return True si le joueur a une colonie/ville sur un port, False sinon.
     */
    public boolean hasPort(){
        if(getPortsOfPlayer().isEmpty())
            return false;
        return true;
    }
    /**
     * Retourne les ports type 2:1 du joueur.
     * @return Une arraylist contenant les ports 2:1 du joueur.
     */
    public ArrayList<Port> getPortsType2OfPlayer(){
        ArrayList<Port> portType2OfPlayer =new ArrayList<Port>();
        if(!getPortsOfPlayer().isEmpty()){
            for(Port port : getPortsOfPlayer()){
                if(port.getPortType()==0)
                    portType2OfPlayer.add(port);
            }
        }
        return portType2OfPlayer;
    }
    /**
     * Retourne vrai si le joueur possède une colonie/ville avec un port 2:1.
     * @return True si il possède une colonie/ville avec port 2:1, False sinon.
     */
    public boolean hasType2Port(){
        if(getPortsType2OfPlayer().isEmpty())
            return false;
        return true;
    }
    /**
     * Retourne les ports 3:1 du joueur.
     * @return Une arraylist contenant tout les ports type 3:1 du joueur.
     */
    public ArrayList<Port> getPortsType3OfPlayer(){
        ArrayList<Port> portType3OfPlayer =new ArrayList<Port>();
        for(int i=0; i<getPortsOfPlayer().size(); i++){
            if(getPortsOfPlayer().get(i).getPortType()==1)
                portType3OfPlayer.add(getPortsOfPlayer().get(i));
        }
        return portType3OfPlayer;
    }
    /**
     * Retourne vrai si le joueur possède une colonie/ville avec un port 3:1
     * @return True si le joueur possède une colonie/ville avec un port 3:1, False sinon.
     */
    public boolean hasType3Port(){
        if(getPortsType3OfPlayer().isEmpty())
            return false;
        return true;
    }
    /**
     * Retourne toutes les possessions du joueur (Colonie / Ville)
     * @return Une arraylist contenant toutes les colonies et villes du joueur.
     */
    public ArrayList<Intersection> allBuildingOfPlayer(){
        ArrayList<Intersection> allBuilding =new ArrayList<Intersection>();
        if(!settlements.isEmpty()){
            for(Intersection inter : settlements){
                allBuilding.add(inter);
            }
        }
        if(!cities.isEmpty()){
            for(Intersection inter : cities){
                allBuilding.add(inter);
            }
        }
        return allBuilding;
    }
    /**
     * Méthode qui permet d'ajouter un certain type de ressource un certain nombre de fois dans les ressources du joueurs.
     * @param resourceType
     *      Type de ressource que le joueur va recevoir.
     * @param nb
     *      Nombre de ressource que le joueur va recevoir.
     */
    public void collectResources(int resourceType, int nb){
        if(resourceType!=-1)
            playerResources[resourceType]++;
    }
    /**
     * Méthode qui permet d'enlever un certain type de ressource un certain nombre de fois dans les ressources du joueurs.
     * @param resourceType
     *      Type de ressource que le joueur va perdre.
     * @param nb
     *      Nombre de ressource que le joueur va perdre.
     */
    public void removeResource(int resourceType, int nb){
        playerResources[resourceType] -= nb;
    }
    /**
     * Retire les ressources nécessaires a l'achat d'une carte de développement.
     */
    public void removeResourceForDevCard(){
        removeResource(Resource.BLE, 1);
        removeResource(Resource.MOUTON, 1);
        removeResource(Resource.PIERRE, 1);
    }
    /**
     * Retire les ressources nécessaires a la construction d'une colonie.
     */
    public void removeResourceForSettlements(){
        removeResource(Resource.BOIS, 1);
        removeResource(Resource.ARGILE, 1);
        removeResource(Resource.BLE, 1);
        removeResource(Resource.MOUTON, 1);
    }
    /**
     * Retire les ressources nécessaires a la construction d'une ville.
     */
    public void removeResourceForCities(){
        removeResource(Resource.BLE, 2);
        removeResource(Resource.PIERRE, 3);
    }
    /**
     * Retire les ressources nécessaires a la construction d'une route.
     */
    public void removeResourceForRoad(){
        removeResource(Resource.BOIS, 1);
        removeResource(Resource.ARGILE, 1);
    }
    /**
     * Retourne une chaine de caractère correspondant aux ressources que possède le joueur. 
     * @return Un chaine de caractère.
     */
    public String resourceOfPlayerToString(){
        String s="";
        s+="Ressource du " + name + " : [";
        int index=-1;
        for(int resource : playerResources){
            index++;
            if(index!=playerResources.length-1)
                s+=(resource+" ");
            else
                s+=(resource);
        }
        s+="] (bois/pierre/ble/mouton/argile)";
        return s;
    }
    /**
     * Cette méthode fait comme {@link #resourceOfPlayerToString()} mais sans les types de ressources. 
     * @return Un chaine de caractère.
     */
    public String resourceOfPlayerToStringWithoutResourceType(){
        String s="";
        s+="[";
        int index=-1;
        for(int resource : playerResources){
            index++;
            if(index!=playerResources.length-1)
                s+=(resource+" ");
            else
                s+=(resource);
        }
        s+="]";
        return s;
    }
    /**
     * Retourne vrai si le joueur possède au moins 1 ressource d'un type spécifique.
     * @param resource
     *      Ressource que l'on cherche dans les ressources du joueur.
     * @return Un booléen.
     */
    public boolean hasOneSpecificResources(int resource){
        if(playerResources[resource]>=1)
            return true;
        return false;
    }
    /**
     * Retourne vrai si le joueur possède au moins 2 ressources d'un type spécifique.
     * @param resource
     *      Ressource que l'on cherche dans les ressources du joueur.
     * @return Un booléen.
     */
    public boolean hasTwoSpecificResources(int resource){
        if(playerResources[resource]>=2)
            return true;
        return false;
    }
    /**
     * Retourne vrai si le joueur possède au moins 3 ressources d'un type spécifique.
     * @param resource
     *      Ressource que l'on cherche dans les ressources du joueur.
     * @return Un booléen.
     */
    public boolean hasThreeSpecificResources(int resource){
        if(playerResources[resource]>=3)
            return true;
        return false;
    }
    /**
     * Retourne vrai si le joueur possède au moins 4 ressources d'un type spécifique.
     * @param resource
     *      Ressource que l'on cherche dans les ressources du joueur.
     * @return Un booléen.
     */
    public boolean hasFourSpecificResources(int resource){
        if(playerResources[resource]>=4)
            return true;
        return false;
    }
    /**
     * Retourne vrai si le joueur possède au moins 1 ressource de n'importe quel type.
     * @param resource
     *      Ressource que l'on cherche dans les ressources du joueur.
     * @return Un booléen.
     */
    public boolean hasOneResources(){
        for(int resource : playerResources){
            if(resource>=1)
                return true;
        }
        return false;
    }
    /**
     * Retourne vrai si le joueur possède au moins 2 ressources de n'importe quel type.
     * @param resource
     *      Ressource que l'on cherche dans les ressources du joueur.
     * @return Un booléen.
     */
    public boolean hasTwoResources(){
        for(int resource : playerResources){
            if(resource>=2)
                return true;
        }
        return false;
    }
    /**
     * Retourne vrai si le joueur possède au moins 3 ressource de n'importe quel type.
     * @param resource
     *      Ressource que l'on cherche dans les ressources du joueur.
     * @return Un booléen.
     */
    public boolean hasThreeResources(){
        for(int resource : playerResources){
            if(resource>=3)
                return true;
        }
        return false;
    }
    /**
     * Retourne vrai si le joueur possède au moins 4 ressource de n'importe quel type.
     * @param resource
     *      Ressource que l'on cherche dans les ressources du joueur.
     * @return Un booléen.
     */
    public boolean hasFourResources(){
        for(int resource : playerResources){
            if(resource>=4)
                return true;
        }
        return false;
    }
    /**
     * Retourne vrai si le joueur possède au moins une carte de développement.
     * @return Un booléen.
     */
    public boolean hasDevCard(){
        if(cards.isEmpty())
            return false;
        return true;
    }
    /**
     * Retourne le total des ressources du joueur.
     * @return Un entier.
     */
    public int totalResource(){
        int total=0;
        for(int resource : playerResources){
            total+=resource;
        }
        return total;
    }
    /**
     * Ajoute une carte de développement dans l'arraylist {@link #cards}.
     * @param devCard
     *      Carte qu'on ajoute dans les cartes du joueur.
     */
    public void addDevCard(DevCard devCard){
        cards.add(devCard);
    }
    /**
     * Retire une carte de développement de l'arraylist {@link #cards}.
     * @param devCard
     *      Carte qu'on retire des cartes du joueur.
     */
    public void removeDevCard(DevCard devCard){
        cards.remove(devCard);
    }
    /**
     * Retourne la dernière colonie du joueur.
     */
    public Intersection lastSettlements(){
        return settlements.get(settlements.size()-1);
    }
    /**
     * Ajoute un certain nombre de point au joueur.
     * @param nb
     *      Nombre de point qu'on ajoute au joueur.
     */
    public void addVictoryPoint(int nb){
        victoryPoint += nb;
    }
    /**
     * Retire un certain nombre de point au joueur.
     * @param nb
     *      Nombre de point qu'on retire au joueur.
     */
    public void removeVictoryPoint(int nb){
        victoryPoint -= nb;
    }
    /**
     * Retourne une chaine de caractère correspondant au nom du joueur.
     */
    @Override
    public String toString(){
        return name;
    }
    /**
     * Getters
     * @return Le nom du joueur.
     */
    public String getName() {return name;}
    /**
     * Setters
     * @param name
     */
    public void setName(String name) {this.name = name;}
    /**
     * Getters
     * @return La couleur du joueur.
     */
    public int getColor() {return color;}
    /**
     * Setters
     * @param color
     */
    public void setColor(int color) {this.color = color;}
    /**
     * Getters
     * @return Les ressources du joueur.
     */
    public int[] getPlayerResources() {return playerResources;}
    /**
     * Setters
     * @param playerResources
     */
    public void setPlayerResources(int[] playerResources) {this.playerResources = playerResources;}
    /**
     * Getters
     * @return Les cartes du joueur.
     */
    public ArrayList<DevCard> getCards() {return cards;}
    /**
     * Setters
     * @param cards
     */
    public void setCards(ArrayList<DevCard> cards) {this.cards = cards;}
    /**
     * Getters
     * @return Les points du joueur.
     */
    public int getVictoryPoint() {return victoryPoint;}
    /**
     * Setters
     * @param victoryPoint
     */
    public void setVictoryPoint(int victoryPoint) {this.victoryPoint = victoryPoint;}
    /**
     * Getters
     * @return Les routes du joueur.
     */
    public ArrayList<Road> getRoads() {return roads;}
    /**
     * Setters
     * @param roads
     */
    public void setRoads(ArrayList<Road> roads) {this.roads = roads;}
    /**
     * Getters
     * @return Les colonies du joueur.
     */
    public ArrayList<Intersection> getSettlements() {return settlements;}
    /**
     * Setters
     * @param settlements
     */
    public void setSettlements(ArrayList<Intersection> settlements) {this.settlements = settlements;}
    /**
     * Getters
     * @return Les villes du joueur.
     */
    public ArrayList<Intersection> getCities() {return cities;}
    /**
     * Setters
     * @param cities
     */
    public void setCities(ArrayList<Intersection> cities) {this.cities = cities;}
    /**
     * Getters
     * @return True si le joueur possède la route la plus longue.
     */
    public boolean isLongestRoad() {return longestRoad;}
    /**
     * Setters
     * @param longestRoad
     */
    public void setLongestRoad(boolean longestRoad) {this.longestRoad = longestRoad;}
    /**
     * Getters
     * @return True si le joueur possède le chevalier le plus fort.
     */
    public boolean isStrongestKnight() {return strongestKnight;}
    /**
     * Setters
     * @param strongestKnight
     */
    public void setStrongestKnight(boolean strongestKnight) {this.strongestKnight = strongestKnight;}
    /**
     * Getters
     * @return Le nombre de carte chevalier joué par le joueur.
     */
    public int getKnightPlayed() {return knightPlayed;}
    /**
     * Setters
     * @param knightPlayed
     */
    public void setKnightPlayed(int knightPlayed) {this.knightPlayed = knightPlayed;}
    /**
     * Getters
     * @return True si le joueur a gagné la partie.
     */
    public boolean isWinner(){return winner;}
    /**
     * Setters
     * @param winner
     */
    public void setWinner(boolean winner){this.winner=winner;}
}
