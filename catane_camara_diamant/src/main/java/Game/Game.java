package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player[] players;
    private Board board;
    private ArrayList<DevCard> devCard;
    private GameDisplay display;

    private int winner;
    private int playerTurn;
    private int turn;

    private int strongestKnightSize;
    private Player strongestKnightOwner;
    private int longestRoadSize;
    private Player longestRoadOwner;

    private Scanner scan =new Scanner(System.in);
    private Random rand =new Random();
 
    public Game(Player[] players) throws Exception{
        this.players=players;
        board =new Board();
        initDevCardGame(); 
    }

    public void play(){
        turn=0;
        foundation();

        strongestKnightSize=0;
        strongestKnightOwner=null;
        longestRoadSize=0;
        longestRoadOwner=null;

        winner=-1;
        int playerTurn=0;

        while(winner==-1){
            turn++;
            players[playerTurn].turn(board, this);
            playerTurn++;

            if(players.length==4){
                if(playerTurn%4==0)
                    playerTurn=0;
            }
            if(players.length==3){
                if(playerTurn%3==0)
                    playerTurn=0;
            }
        }

        scan.close();
    }

    public void foundation(){  //HUMAIN ET IA
        int tour=0;
        while(tour!=2){
            for(Player player : players){
                System.out.println("Tour : " + player.toString());
                if(player instanceof Human){ //HUMAIN
                    boolean reponseValide1=false;
                    while(!reponseValide1){
                        if(tour==0)
                            System.out.println("\nChoisissez la position de votre première colonie : (0 à 24)");
                        else 
                            System.out.println("\nChoisissez la position de votre deuxième colonie : (0 à 24)");
                    
                        int rep = scan.nextInt();
                        scan.nextLine();
    
                        if(distanceRules(rep)){
                            player.buildSettlement(rep, board, turn);
                            reponseValide1=true;
                        }
                        else{
                            if(player instanceof Human)
                                System.out.println("Vous ne pouvez pas construire sur cette intersection. La règle de distance des colonies n'est pas respecté !");
                        }
                    }

                    boolean reponseValide2=false;
                    while(!reponseValide2){
                        if(tour==0)
                            System.out.println("\nChoisissez la position de votre première route (Saisir les deux id des intersections où est présente l'arête) : ");
                        else 
                            System.out.println("\nChoisissez la position de votre deuxième route (Saisir les deux id des intersections où est présente l'arête) : ");
                        int rep1 = scan.nextInt();
                        scan.nextLine();
                        int rep2 = scan.nextInt();
                        scan.nextLine();

                        if(idForRoadHasSettlementsOrCity(rep1, rep2, player)){
                            player.buildRoad(rep1, rep2, board, turn, this);
                            reponseValide2=true;
                        }
                        else{
                            System.out.println("La route ne peut pas être placé a cet emplacement !");
                        }
                    }
                }
                else{ //IA
                    int rep=0;
                    boolean reponseValide1=false;
                    while(!reponseValide1){
                        ArrayList<Intersection> interDistRules = emptyIntersectionDistanceRules();

                        rep=interDistRules.get(rand.nextInt(interDistRules.size())).getId();

                        if(distanceRules(rep)){
                            player.buildSettlement(rep, board, turn);
                            reponseValide1=true;
                        }
                    }

                    boolean reponseValide2=false;
                    while(!reponseValide2){
                        ArrayList<Integer> canBuilRoad = canBuildRoadIA(rep);
                        
                        int rep2=canBuilRoad.get(rand.nextInt(canBuilRoad.size()));

                        if(idForRoadHasSettlementsOrCity(rep, rep2, player)){
                            player.buildRoad(rep, rep2, board, turn, this);
                            reponseValide2=true;
                        }
                    }
                }
            }
            tour++;
        }
        for(Player player : players){
            for(int i=0; i<player.lastSettlements().getCaseAdj().length; i++){
                player.collectResources(player.lastSettlements().getCaseAdj()[i].getResource().getResourceType(), 1);
            }
        }
    }

    public void buildAnswer(Player player){ //HUMAIN
        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Que souhaitez-vous construire ? (colonie/ville/route) | Souhaitez-vous revenir aux choix précédent ? (choix)"); //Le choix "choix" nous fait revenir dans la méthode turn.
            String rep=scan.nextLine();
            switch (rep) {
                case "colonie":
                    if(!noIntersectionAvailable()){
                        if(player.resourceForSettlement()){
                            System.out.println("Choisissez l'emplacement de la colonie (0 à 24): ");

                            int rep1 = scan.nextInt();
                            scan.nextLine();

                            if(distanceRules(rep1)){
                                player.buildSettlement(rep1, board, turn);
                                reponseValide=true;
                            }
                            else{
                                if(player instanceof Human)
                                    System.out.println("Vous ne pouvez pas construire sur cette intersection. La règle de distance des colonies n'est pas respecté !");
                            }
                        }
                        else{
                            System.out.println("Vous n'avez pas les resources nécessaires pour construire une colonie !");
                        }
                    }
                    break;
                case "ville":
                    if(player.resourceForCity()){
                        System.out.println("Choisissez l'emplacement de la ville (0 à 24): ");

                        int rep1=scan.nextInt();
                        scan.nextLine();

                        if(distanceRules(rep1)){
                            player.buildCity(rep1, board);
                            reponseValide=true;
                        }
                        else{
                            if(player instanceof Human)
                                System.out.println("Vous ne pouvez pas construire sur cette intersection. La règle de distance des colonies n'est pas respecté !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les resources nécessaires pour construire une ville !");
                    }
                    break;
                case "route":
                    if(player.resourceForRoad()){
                        System.out.println("Choisissez l'emplacement de la route (Saisissez les id de deux intersections adjacentes) :");
                        
                        int rep1=scan.nextInt();
                        scan.nextLine();
                        int rep2=scan.nextInt();
                        scan.nextLine();

                        if(idHasRoadOfPlayer(rep1, rep2, player) || idForRoadHasSettlementsOrCity(rep1, rep2, player)){
                            player.buildRoad(rep1, rep2, board, turn, this);
                            reponseValide=true;
                        }
                        else
                            System.out.println("La route ne peut pas être construite car elle n'est pas placé sur une intersection déjà connecté a une route ou car elle n'est pas placé sur une intersection possedant une ville.");
                    }
                    else{
                        System.out.println("Vous n'avez pas les resources nécessaires pour construire une route !");
                    }
                    break;
                case "choix":
                    reponseValide=true;
                    break;
                default:
                    System.out.println("Ce choix de construction n'existe pas !");
                    break;
            }
        }
    }    

    public boolean buildAnswerIA(IA ia){ //IA
        System.out.println("Test buildAnwerIA");
        ArrayList<Integer> buildChoice=new ArrayList<Integer>();

        if(ia.resourceForSettlement())
            buildChoice.add(0);
        if(ia.resourceForCity())
            buildChoice.add(1);
        if(ia.resourceForRoad())
            buildChoice.add(2);
        
        if(!buildChoice.isEmpty()){
            ArrayList<Intersection> interDistRules = emptyIntersectionDistanceRules();
            ArrayList<Integer> settlementsIA =new ArrayList<Integer>();
            for(Intersection inter : ia.getSettlements()){
                settlementsIA.add(inter.getId());
            }
            ArrayList<Road> emptyRoad = board.getEmptyRoad();

            boolean reponseValide=false;
            while(!reponseValide){
                int buildType = buildChoice.get(rand.nextInt(buildChoice.size()));
                int randChoice=0;
                switch (buildType) {
                    case 0:
                        if(!noIntersectionAvailable()){
                            if(!interDistRules.isEmpty()){
                                randChoice = interDistRules.get(rand.nextInt(interDistRules.size())).getId();

                                if(distanceRules(randChoice)){
                                    ia.buildSettlement(randChoice, board, turn);
                                    reponseValide=true;
                                    return true;
                                }
                                else
                                    interDistRules.remove(randChoice);
                            }
                            else
                                return false;
                        }
                        else
                            buildChoice.remove(0);
                        break;
                    case 1:
                        if(!settlementsIA.isEmpty()){
                            randChoice = settlementsIA.get(rand.nextInt(settlementsIA.size()));

                            if(distanceRules(randChoice)){
                                ia.buildCity(randChoice, board);
                                reponseValide=true;
                                return true;
                            }
                            else
                                settlementsIA.remove(randChoice);
                        }
                        else
                            return false;
                        break;
                    case 2:
                        if(!emptyRoad.isEmpty()){
                            Road roadChoice = emptyRoad.get(rand.nextInt(emptyRoad.size()));
                            int id1 = roadChoice.getId1();
                            int id2 = roadChoice.getId2();

                            if(idHasRoadOfPlayer(id1, id2, ia) || idForRoadHasSettlementsOrCity(id1, id2, ia)){
                                ia.buildRoad(id1, id2, board, turn, this);
                                reponseValide=true;
                                return true;
                            }
                            else
                                emptyRoad.remove(roadChoice);
                        }
                        else 
                            return false;
                        break;
                }
            }
        }
        return false;
    }

    public void tradeAnswer(Player player){
        if(!player.getPortsOfPlayer().isEmpty()){ //Possède port 3:1 et/ou 2:1
            player.resourceOfPlayerToString();
            if(!player.getPortsType2OfPlayer().isEmpty() && !player.getPortsType3OfPlayer().isEmpty()){//Possède 2:1 et 3:1
                trade2or3Resources(player);
            }
            else if(player.getPortsType2OfPlayer().isEmpty() && !player.getPortsType3OfPlayer().isEmpty()){//Possède 3:1
                trade3Resources(player);
            }
            else if(!player.getPortsType2OfPlayer().isEmpty() && player.getPortsType3OfPlayer().isEmpty()){//Possède 2:1
                trade2or4Resources(player);
            }
        }
        else{ //Ne possède pas de port.
            trade4Resources(player);
        }
    }

    public void buyAnswer(Player player){
        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println(player.resourceOfPlayerToString());
            System.out.println("Souhaitez-vous acheter une carte de développement ? (oui/non) | Revenir aux choix précédents : (choix)");

            String rep=scan.nextLine();

            switch (rep) {
                case "oui":
                    if(player.resourceForDevCard()){
                        if(!devCard.isEmpty()){
                            player.addDevCard(devCard.get(0));
                            System.out.println("\n"+player.getName()+" vient de piocher "+devCard.get(0).toString()+" !");
                            devCard.remove(0);
                            player.removeResourceForDevCard();
                            reponseValide=true;
                        }
                        else
                            System.out.println("\nIl n'y a plus de carte de développement disponibles !");
                            reponseValide=true;
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires pour acheter une carte de développement !");
                        reponseValide=true;
                    }
                    break;
                case "non":
                    reponseValide=true;
                    break;
                case "choix":
                    reponseValide=true;
                    break;
                default:
                    System.out.println("Ce choix n'existe pas !");
                    break;
            }
        }
    }

    public boolean buyAnswerIA(IA ia){
        System.out.println("Test buyAnwerIA");
        if(!devCard.isEmpty()){
            if(ia.resourceForDevCard()){
                ia.addDevCard(devCard.get(0));
                System.out.println("\n"+ia.getName()+" vient de piocher "+devCard.get(0).toString()+" !");
                devCard.remove(0);
                ia.removeResourceForDevCard();
                return true;
            }
        }
        return false;
    }

    public void resourceAnswer(Player player){
        System.out.println(player.resourceOfPlayerToString()+"\n");
    }

    public void playCardAnswer(Player player){ //HUMAIN
        if(player.hasDevCard()){
            System.out.println("\nVous n'avez pas de cartes de développement a jouer !");
        }   
        else{
            System.out.println("Voici vos cartes de développement :");
            int index=-1;
            for(DevCard card : player.getCards()){
                index++;
                System.out.println(card.toString() +" "+index);
            }
            System.out.println("Vous pouvez saisir "+(index+1)+" pour revenir aux choix précédents.");

            boolean reponseValide=false;
            while(!reponseValide){
                System.out.println("\nSaisissez l'un des index des cartes de développement | Revenir aux choix précédents : ("+(index+1)+")");
                String rep=scan.nextLine();

                int repToInt=Integer.parseInt(rep);

                if(repToInt>=0 && repToInt<=index){
                    switch (player.getCards().get(repToInt).getCard()) {
                        case DevCard.VICTORY_POINT:
                            System.out.println("Vous venez de jouer une carte point de victoire !");
                            victoryPointCard(player);
                            reponseValide=true;
                            break;
                        case DevCard.PROGRESS_BUILD:
                            System.out.println("Vous venez de jouer une carte progrès : Construction !");
                            progressCard(player, DevCard.PROGRESS_BUILD);
                            reponseValide=true;
                            break;
                        case DevCard.PROGRESS_DISCOVERY:
                            System.out.println("Vous venez de jouer une carte progrès : Découverte !");
                            progressCard(player, DevCard.PROGRESS_DISCOVERY);
                            reponseValide=true;
                            break;
                        case DevCard.PROGRESS_MONOPOLY:
                            System.out.println("Vous venez de jouer une carte progrès : Monopole !");
                            progressCard(player, DevCard.PROGRESS_MONOPOLY);
                            reponseValide=true;
                            break;
                        case DevCard.KNIGHT:
                            System.out.println("Vous venez de jouer une carte chevalier !");
                            knightCard(player);
                            reponseValide=true;
                            break;
                    }
                    player.removeDevCard(player.getCards().get(repToInt));
                }
                else if(repToInt==(index+1)){
                    reponseValide=true;
                }
                else
                    System.out.println("Ce choix n'existe pas !");
            }
        }
    }

    public boolean playCardAnswerIA(IA ia){
        System.out.println("Test playCardAnwerIA");
        ArrayList<DevCard> cards =new ArrayList<DevCard>();
        for(DevCard card : ia.getCards()){
            cards.add(card);
        }

        if(!cards.isEmpty()){

            DevCard randCard = cards.get(rand.nextInt(cards.size()));

            switch (randCard.getCard()) {
                case DevCard.VICTORY_POINT:
                    victoryPointCard(ia);
                    break;
                case DevCard.PROGRESS_BUILD:
                    progressCardIA(ia, DevCard.PROGRESS_BUILD);
                    break;
                case DevCard.PROGRESS_DISCOVERY:
                    progressCardIA(ia, DevCard.PROGRESS_DISCOVERY);
                    break;
                case DevCard.PROGRESS_MONOPOLY:
                    progressCardIA(ia, DevCard.PROGRESS_MONOPOLY);
                    break;
                case DevCard.KNIGHT:
                    knightCardIA(ia);
                    break;
            }
            ia.removeDevCard(randCard);
            return true;
        }
        return false;
    }

    public void trade2or3Resources(Player player){
        player.resourceOfPlayerToString();
        int index = -1;
        System.out.println("Voici les ports 2:1 disponibles :");
        for(Port elem : player.getPortsType2OfPlayer()){
            index++;
            System.out.println(elem.toString()+ ": " +index);
        }
        System.out.println("Vous possèdez aussi un ou plusieurs ports 3:1.");
        System.out.println("Si vous souhaitez échanger avec les ports 3:1, saisissez "+(index+1));

        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("\nSaisissez l'un des index des ports pour échanger des ressources | Revenir aux choix précédents : ("+(index+2)+")");
            String rep=scan.nextLine();
            
            int repToInt=Integer.parseInt(rep);

            if(repToInt>=0 && repToInt<=index+1){
                if(repToInt==index+1){
                    trade3Resources(player);
                    reponseValide=true;
                }
                else{
                    if(player.hasTwoSpecificResources(player.getPortsType2OfPlayer().get(repToInt).getResource().getResourceType())){
                        System.out.println("Quel ressource voulez-vous en échange ? (bois (0)/pierre (1)/ble (2)/mouton (3)/argile (4))");

                        int rep1=scan.nextInt();
                        scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=player.getPortsType2OfPlayer().get(repToInt).getResource().getResourceType()){
                            player.removeResource(player.getPortsType2OfPlayer().get(repToInt).getResource().getResourceType(), 2);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)!=-10)
                                System.out.println("Ce type de ressource n'existe pas !");
                            if(askedResource(rep1)!=player.getPortsType2OfPlayer().get(repToInt).getResource().getResourceType())
                                System.out.println("Vous ne pouvez pas demander une ressource identique a la ressource demandé par le port !");
                        }
                    }
                    else
                        System.out.println("Vous n'avez pas les ressources nécessaires !");    
                }
            }
            else if(repToInt==(index+2))
                reponseValide=true;
            else
                System.out.println("L'index que vous avez saisit n'existe pas !");
        }
    }

    public void trade3Resources(Player player){
        player.resourceOfPlayerToString();
        System.out.println("Au moins une de votre colonie/ville possède un port 3:1, vous pouvez donc échanger 3 ressources de votre choix contre 1 ressources de votre choix.");

        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Quel type de ressource souhaitez-vous échangé ? (bois (0)/pierre (1)/ble (2)/mouton (3)/argile (4)) | Souhaitez-vous revenir aux choix précédent ? (20)");

            int rep=scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (askedResource(rep)) {
                case Resource.BOIS:
                    if(player.hasThreeSpecificResources(Resource.BOIS)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (pierre (1)/ble (2)/mouton (3)/argile (4))");

                        int rep1 = scan.nextInt();
                        scan.nextLine();
                        System.out.println();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=Resource.BOIS){                
                            player.removeResource(Resource.BOIS, 3);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==Resource.BOIS)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.PIERRE:
                    if(player.hasThreeSpecificResources(Resource.PIERRE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois (0)/ble (2)/mouton (3)/argile (4))");

                        int rep1 = scan.nextInt();
                        scan.nextLine();
                        System.out.println();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=Resource.PIERRE){                
                            player.removeResource(Resource.PIERRE, 3);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==Resource.PIERRE)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.BLE:
                    if(player.hasThreeSpecificResources(Resource.BLE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois (0)/pierre (1)/mouton (3)/argile (4))");

                        int rep1 = scan.nextInt();
                        scan.nextLine();
                        System.out.println();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=Resource.BLE){                
                            player.removeResource(Resource.BLE, 3);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==Resource.BLE)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.MOUTON:
                    if(player.hasThreeSpecificResources(Resource.MOUTON)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois (0)/pierre (1)/ble (2)/argile (4))");

                        int rep1 = scan.nextInt();
                        scan.nextLine();
                        System.out.println();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=Resource.MOUTON){                
                            player.removeResource(Resource.MOUTON, 3);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==Resource.MOUTON)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.ARGILE:
                    if(player.hasThreeSpecificResources(Resource.ARGILE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois (0)/pierre (1)/ble (2)/mouton (3))");

                        int rep1 = scan.nextInt();
                        scan.nextLine();
                        System.out.println();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=Resource.ARGILE){                
                            player.removeResource(Resource.ARGILE, 3);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==Resource.ARGILE)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case 20:
                    reponseValide=true;
                    break;
                default:
                    System.out.println("Ce type de ressource n'existe pas !");
                    break;
            }
        }
    }  

    public boolean trade3ResourceIA(IA ia){ //IA
        System.out.println("Test trade3ResourceIA");
        if(!ia.getPortsType3OfPlayer().isEmpty()){
            ArrayList<Resource> resources =new ArrayList<Resource>();
            for(int i=0; i<ia.getPlayerResources().length; i++){
                if(ia.getPlayerResources()[i]>=3)
                    resources.add(new Resource(i));
            }

            if(!resources.isEmpty()){
                Resource randResourceTypeSent = resources.get(rand.nextInt(resources.size()));

                ArrayList<Resource> resourceChoice =new ArrayList<Resource>();

                if(randResourceTypeSent.getResourceType()==Resource.BOIS){
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(3));
                    resourceChoice.add(new Resource(4));
                }
                else if(randResourceTypeSent.getResourceType()==Resource.PIERRE){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(3));
                    resourceChoice.add(new Resource(4));
                }
                else if(randResourceTypeSent.getResourceType()==Resource.BLE){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(3));
                    resourceChoice.add(new Resource(4));
                }
                else if(randResourceTypeSent.getResourceType()==Resource.MOUTON){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(4));
                }
                else if(randResourceTypeSent.getResourceType()==Resource.ARGILE){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(3));
                }

                Resource resource = resourceChoice.get(rand.nextInt(resourceChoice.size()));

                System.out.println(ia.toString()+" a échanger 4 ressources "+randResourceTypeSent.toString()+
                " contre "+ resource.toString());

                ia.removeResource(randResourceTypeSent.getResourceType(), 3);
                ia.collectResources(resource.getResourceType(), 1);

                return true;
            }
        }
        return false;
    }

    public void trade4Resources(Player player){
        player.resourceOfPlayerToString();
        System.out.println();
        System.out.println("Aucune de vos colonies ne possède de port." +System.lineSeparator()+ "Vous pouvez choisir d'échanger 4 ressources de votre choix contre 1 ressource de votre choix.");
        System.out.println();

        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Quel type de ressource souhaitez-vous échangé ? (bois (0)/pierre (1)/ble (2)/mouton (3)/argile (4)) | Souhaitez-vous revenir aux choix précédent ? (20)");
            
            int rep=scan.nextInt();
            scan.nextLine();

            switch (askedResource(rep)) {
                case Resource.BOIS:
                    if(player.hasFourSpecificResources(Resource.BOIS)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (pierre (1)/ble (2)/mouton (3)/argile (4))");

                        int rep1 = scan.nextInt();
                        scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=Resource.BOIS){                
                            player.removeResource(Resource.BOIS, 4);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==Resource.BOIS)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.PIERRE:
                    if(player.hasFourSpecificResources(Resource.PIERRE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois (0)/ble (2)/mouton (3)/argile (4))");

                        int rep1 = scan.nextInt();
                        scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=Resource.PIERRE){                
                            player.removeResource(Resource.PIERRE, 4);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==Resource.PIERRE)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.BLE:
                    if(player.hasFourSpecificResources(Resource.BLE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois (0)/pierre (1)/mouton (3)/argile (4))");

                        int rep1 = scan.nextInt();
                        scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=Resource.BLE){                
                            player.removeResource(Resource.BLE, 4);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==Resource.BLE)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.MOUTON:
                    if(player.hasFourSpecificResources(Resource.MOUTON)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois (0)/pierre (1)/ble (2)/argile (4))");

                        int rep1 = scan.nextInt();
                        scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=Resource.MOUTON){                
                            player.removeResource(Resource.MOUTON, 4);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==Resource.MOUTON)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.ARGILE:
                    if(player.hasFourSpecificResources(Resource.ARGILE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois (0)/pierre (1)/ble (2)/mouton (3))");

                        int rep1 = scan.nextInt();
                        scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=Resource.ARGILE){                
                            player.removeResource(Resource.ARGILE, 4);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==Resource.ARGILE)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case 20:
                    reponseValide=true;
                    break;
                default:
                    System.out.println("Ce type de ressource n'existe pas !");
                    break;
            }
        }
    }

    public boolean trade4ResourcesIA(IA ia){ //IA
        System.out.println("Test trade4ResourceIA");
        ArrayList<Resource> resources =new ArrayList<Resource>();
        for(int i=0; i<ia.getPlayerResources().length; i++){
            if(ia.getPlayerResources()[i]>=4)
                resources.add(new Resource(i));
        }
        Resource randResourceTypeSent = resources.get(rand.nextInt(resources.size()));

        if(!resources.isEmpty()){
            ArrayList<Resource> resourceChoice =new ArrayList<Resource>();

            if(randResourceTypeSent.getResourceType()==Resource.BOIS){
                resourceChoice.add(new Resource(1));
                resourceChoice.add(new Resource(2));
                resourceChoice.add(new Resource(3));
                resourceChoice.add(new Resource(4));
            }
            else if(randResourceTypeSent.getResourceType()==Resource.PIERRE){
                resourceChoice.add(new Resource(0));
                resourceChoice.add(new Resource(2));
                resourceChoice.add(new Resource(3));
                resourceChoice.add(new Resource(4));
            }
            else if(randResourceTypeSent.getResourceType()==Resource.BLE){
                resourceChoice.add(new Resource(0));
                resourceChoice.add(new Resource(1));
                resourceChoice.add(new Resource(3));
                resourceChoice.add(new Resource(4));
            }
            else if(randResourceTypeSent.getResourceType()==Resource.MOUTON){
                resourceChoice.add(new Resource(0));
                resourceChoice.add(new Resource(1));
                resourceChoice.add(new Resource(2));
                resourceChoice.add(new Resource(4));
            }
            else if(randResourceTypeSent.getResourceType()==Resource.ARGILE){
                resourceChoice.add(new Resource(0));
                resourceChoice.add(new Resource(1));
                resourceChoice.add(new Resource(2));
                resourceChoice.add(new Resource(3));
            }

            Resource resource = resourceChoice.get(rand.nextInt(resourceChoice.size()));

            System.out.println(ia.toString()+" a échanger 4 ressources "+randResourceTypeSent.toString()+
            " contre "+ resource.toString());
            
            ia.removeResource(randResourceTypeSent.getResourceType(), 4);
            ia.collectResources(resource.getResourceType(), 1);

            return true;
        }
        return false;
    }

    public void trade2or4Resources(Player player){
        player.resourceOfPlayerToString();
        int index=0;
        for(Port elem : player.getPortsType2OfPlayer()){
            System.out.println(elem.toString()+ ": " +index);
            index++;
        }
        System.out.println("Si vous ne voulez pas échanger avec les ports 2:1, saisissez "+(index+1));
            
        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("\nSaissisez l'un des index des ports pour échanger des ressources : | Revenir aux choix précédents : (choix)");
            String rep=scan.nextLine();
                    
            int repToInt = Integer.parseInt(rep);

            if(repToInt>=0 && repToInt<=index+1){
                if(repToInt==index+1)
                    trade4Resources(player);
                else{
                    if(player.hasTwoSpecificResources(player.getPortsType2OfPlayer().get(repToInt).getResource().getResourceType())){
                        System.out.println("Quel ressource voulez-vous en échange ? (bois (0)/pierre (1)/ble (2)/mouton (3)/argile (4))");

                        int rep1=scan.nextInt();
                        scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=player.getPortsType2OfPlayer().get(repToInt).getResource().getResourceType()){
                            player.removeResource(player.getPortsType2OfPlayer().get(repToInt).getResource().getResourceType(), 2);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)!=-10)
                                System.out.println("Ce type de ressource n'existe pas !");
                            if(askedResource(rep1)!=player.getPortsType2OfPlayer().get(repToInt).getResource().getResourceType())
                                System.out.println("Vous ne pouvez pas demander une ressource identique a la ressource demandé par le port !");
                        }
                    }
                    else
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                }
            }
            else if(rep=="choix")
                reponseValide=true;
            else
                System.out.println("L'index que vous avez saisit n'existe pas !");
                
        }
    }

    public boolean trade2ResourcesIA(IA ia){ //IA
        System.out.println("Test trade2ResourceIA");
        if(ia.getPortsType2OfPlayer().size()==1){
            int resourceTypeOfPort=ia.getPortsType2OfPlayer().get(0).getResource().getResourceType();
            if(ia.hasTwoSpecificResources(resourceTypeOfPort)){
                
                ArrayList<Resource> resourceChoice =new ArrayList<Resource>();
                
                if(resourceTypeOfPort==Resource.BOIS){
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(3));
                    resourceChoice.add(new Resource(4));
                }
                else if(resourceTypeOfPort==Resource.PIERRE){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(3));
                    resourceChoice.add(new Resource(4));
                }
                else if(resourceTypeOfPort==Resource.BLE){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(3));
                    resourceChoice.add(new Resource(4));
                }
                else if(resourceTypeOfPort==Resource.MOUTON){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(4));
                }
                else if(resourceTypeOfPort==Resource.ARGILE){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(3));
                }
                
                Resource resource = resourceChoice.get(rand.nextInt(resourceChoice.size()));


                System.out.println(ia.toString()+" a échanger 2 ressources "+ia.getPortsType2OfPlayer().get(0).getResource().toString()+
                " contre "+ resource.toString());

                ia.removeResource(resourceTypeOfPort, 2);
                ia.collectResources(resource.getResourceType(), 1);

                return true;
            }
            return false;
        }
        else{
            Port portChoice = ia.getPortsType2OfPlayer().get(rand.nextInt(ia.getPortsType2OfPlayer().size()));

            int resourceTypeOfPort=portChoice.getResource().getResourceType();
            if(ia.hasTwoSpecificResources(resourceTypeOfPort)){
                
                ArrayList<Resource> resourceChoice =new ArrayList<Resource>();
                
                if(resourceTypeOfPort==Resource.BOIS){
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(3));
                    resourceChoice.add(new Resource(4));
                }
                else if(resourceTypeOfPort==Resource.PIERRE){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(3));
                    resourceChoice.add(new Resource(4));
                }
                else if(resourceTypeOfPort==Resource.BLE){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(3));
                    resourceChoice.add(new Resource(4));
                }
                else if(resourceTypeOfPort==Resource.MOUTON){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(4));
                }
                else if(resourceTypeOfPort==Resource.ARGILE){
                    resourceChoice.add(new Resource(0));
                    resourceChoice.add(new Resource(1));
                    resourceChoice.add(new Resource(2));
                    resourceChoice.add(new Resource(3));
                }
                
                Resource resource = resourceChoice.get(rand.nextInt(resourceChoice.size()));

                System.out.println(ia.toString()+" a échanger 2 ressources "+portChoice.getResource().toString()+
                " contre "+ resource.toString());

                ia.removeResource(resourceTypeOfPort, 2);
                ia.collectResources(resource.getResourceType(), 1);
                return true;
            }
            return false;
        }
    }

    public Resource intToResource(int resourceType){
        Resource resource=null;
        switch (resourceType) {
            case Resource.BOIS:
                resource=new Resource(Resource.BOIS);
                break;
            case Resource.PIERRE:
                resource=new Resource(Resource.PIERRE);
                break;
            case Resource.BLE:
                resource=new Resource(Resource.BLE);
                break;
            case Resource.MOUTON:
                resource=new Resource(Resource.MOUTON);
                break;
            case Resource.ARGILE:
                resource=new Resource(Resource.ARGILE);
                break;
        }
        return resource;
    }

    public int askedResource(int resourceType){
        int resource=0;
        switch (resourceType) {
            case Resource.BOIS:
                resource=Resource.BOIS;
                break;
            case Resource.PIERRE:
                resource=Resource.PIERRE;
                break;
            case Resource.BLE:
                resource=Resource.BLE;
                break;
            case Resource.MOUTON:
                resource=Resource.MOUTON;
                break;
            case Resource.ARGILE:
                resource=Resource.ARGILE;
                break;
            case 20:
                resource=20;
                break;
            default:
                resource=-10;
                break;
        }
        return resource;
    }

    public boolean winner(){
        for(Player player : players){
            if(player.getVictoryPoint()==10){
                return true;
            }
        }
        return false;
    }

    public void strongestKnight(){
        for(Player player : players){
            int strongestKnight = player.getKnightPlayed();
            if(strongestKnight >=3 && strongestKnight>strongestKnightSize){
                strongestKnightSize=strongestKnight;
                if(strongestKnightOwner!=null){
                    strongestKnightOwner.setStrongestKnight(false);
                    strongestKnightOwner.removeVictoryPoint(2);
                    System.out.println(strongestKnightOwner.toString()+" perd la carte spéciale \"Chevalier le plus fort\" et perd 2 points de victoire !");
                    strongestKnightOwner=player;
                    player.setStrongestKnight(true);
                    System.out.println(player.toString()+" obtient la carte spéciale \"Chevalier le plus fort\" et obtient 2 points de victoire !");
                    player.addVictoryPoint(2);
                }
                else{
                    strongestKnightOwner=player;
                    player.setStrongestKnight(true);
                    System.out.println(player.toString()+" obtient la carte spéciale \"Chevalier le plus fort\"");
                    player.addVictoryPoint(2);
                }
            }
        }
    }

    public int diceRolls(){
        Random rand =new Random();
        return 1+rand.nextInt(6-1);
    }

    public void diceRolls7(){
        for(Player player : players){
            if(player.totalResource()>7){
                int discardResource=player.totalResource()/2;
                if(player instanceof Human){
                    System.out.println(player.toString()+" vous avez plus de 7 resources aux total !");
                    System.out.println("Vous devez vous défausser de la moitié de vos ressources. C'est à dire "+ discardResource);
                }
                
                while(discardResource!=0){
                    boolean reponseValide=false;
                    while(!reponseValide){
                        int rep=0;
                        if(player instanceof Human){
                            System.out.println("\nChoisissez une ressource à défausser : (bois (0)/pierre (1)/ble (2)/mouton (3)/argile (4))");

                            rep=scan.nextInt();
                            scan.nextLine();
                        }
                        else{
                            ArrayList<Integer> hasOneResource = new ArrayList<Integer>();
                            for(int resourceType : player.getPlayerResources()){
                                if(player.hasOneSpecificResources(resourceType))
                                    hasOneResource.add(resourceType);
                            }
                            rep=hasOneResource.get(rand.nextInt(hasOneResource.size()));
                        }

                        switch (askedResource(rep)) {
                            case Resource.BOIS:
                                if(player.hasOneSpecificResources(Resource.BOIS)){
                                    player.removeResource(Resource.BOIS, 1);
                                    System.out.println(player.toString()+" c'est défaussé 1 ressource bois.");
                                    reponseValide=true;
                                }
                                else{
                                    if(player instanceof Human)
                                        System.out.println("Vous n'avez pas assez de cette ressource !");
                                }
                                break;
                            case Resource.PIERRE:
                                if(player.hasOneSpecificResources(Resource.PIERRE)){
                                    player.removeResource(Resource.PIERRE, 1);
                                    System.out.println(player.toString()+" c'est défaussé 1 ressource pierre.");
                                    reponseValide=true;
                                }
                                else{
                                    if(player instanceof Human)
                                        System.out.println("Vous n'avez pas assez de cette ressource !");
                                }
                                break;
                            case Resource.BLE:
                                if(player.hasOneSpecificResources(Resource.BLE)){
                                    player.removeResource(Resource.BLE, 1);
                                    System.out.println(player.toString()+" c'est défaussé 1 ressource ble.");
                                    reponseValide=true;
                                }
                                else{
                                    if(player instanceof Human)
                                        System.out.println("Vous n'avez pas assez de cette ressource !");
                                }
                                break;
                            case Resource.MOUTON:
                                if(player.hasOneSpecificResources(Resource.MOUTON)){
                                    player.removeResource(Resource.MOUTON, 1);
                                    System.out.println(player.toString()+" c'est défaussé 1 ressource mouton.");
                                    reponseValide=true;
                                }
                                else{
                                    if(player instanceof Human)
                                        System.out.println("Vous n'avez pas assez de cette ressource !");
                                }
                                break;
                            case Resource.ARGILE:
                                if(player.hasOneSpecificResources(Resource.ARGILE)){
                                    player.removeResource(Resource.ARGILE, 1);
                                    System.out.println(player.toString()+" c'est défaussé 1 ressource argoile.");
                                    reponseValide=true;
                                }
                                else{
                                    if(player instanceof Human)
                                        System.out.println("Vous n'avez pas assez de cette ressource !");
                                }
                                break;
                            default:
                                if(player instanceof Human){
                                    if(askedResource(rep)==-10)
                                        System.out.println("Cette ressource n'existe pas !");
                                }
                                break;
                        }
                    }
                    discardResource--;
                    System.out.println(player.resourceOfPlayerToString());
                }
            }
        }    
    }

    public void knightCard(Player player){
        player.moveRobber(board, this);

        int playerSettlementOrCity=0;
        for(Intersection inter : board.getCases()[board.getIndexRobber()].getCaseIntersections()){
            if(inter.getPlayer()!=null){
                playerSettlementOrCity++;
            }
        }
        if(playerSettlementOrCity==0){
            System.out.println("Cette case n'a pas d'intersections où une colonie/ville est présente !");
        }
        else{
            System.out.println("Voici les joueurs auxquelles vous pouvez voler une ressource: ");
            int index=-1;
            for(Intersection inter : board.getCases()[board.getIndexRobber()].getCaseIntersections()){
                index++;
                if(inter.getPlayer()!=null){
                    System.out.println(inter.getPlayer().getName()+" : "+index);
                }
            }
            
            boolean reponseValide=false;
            while(!reponseValide){
                System.out.println("Saisissez l'index du joueur a qui vous voulez voler une ressource :");

                int rep =scan.nextInt();
                scan.nextLine();

                if(rep<=index){
                    Random rand =new Random();
                    int randResource = rand.nextInt(5);
                    board.getCases()[board.getIndexRobber()].getCaseIntersections()[rep].getPlayer().removeResource(randResource, 1);
                    player.collectResources(randResource, 1);
                    reponseValide=true;
                }
                else
                    System.out.println("Cet index n'existe pas !");
            }
        }
        player.setKnightPlayed(player.getKnightPlayed()+1);
        strongestKnight();
    }

    public void knightCardIA(IA ia){
        System.out.println("Test knightCardIA");
        ia.moveRobber(board, this);

        int playerSettlementOrCity=0;
        for(Intersection inter : board.getCases()[board.getIndexRobber()].getCaseIntersections()){
            if(inter.getPlayer()!=null){
                playerSettlementOrCity++;
            }
        }
        if(playerSettlementOrCity!=0){
            ArrayList<Player> playerInter =new ArrayList<Player>();
            for(Intersection inter : board.getCases()[board.getIndexRobber()].getCaseIntersections()){
                if(inter.getPlayer()!=null){
                    playerInter.add(inter.getPlayer());
                }
            }

            Player randPlayer = playerInter.get(rand.nextInt(playerInter.size()));
        
            int randResource = rand.nextInt(5);
            randPlayer.removeResource(randResource, 1);
            ia.collectResources(randResource, 1);
        }
    }

    public void progressCard(Player player, int cardType){
        switch (cardType) {
            case DevCard.PROGRESS_BUILD:
                progressBuildCard(player);
                break;
            case DevCard.PROGRESS_DISCOVERY:
                progressDiscoveryCard(player);
                break;
            case DevCard.PROGRESS_MONOPOLY:
                progressMonopolyCard(player);
                break;
        }
    }

    public void progressCardIA(IA ia, int cardType){
        System.out.println("Test progressCardIA");
        switch (cardType) {
            case DevCard.PROGRESS_BUILD:
                progressBuildCardIA(ia);
                break;
            case DevCard.PROGRESS_DISCOVERY:
                progressDiscoveryCardIA(ia);
                break;
            case DevCard.PROGRESS_MONOPOLY:
                progressMonopolyCardIA(ia);
                break;
        }
    }

    public void progressBuildCard(Player player){
        System.out.println("\nVous devez maintenant poser deux routes sur le plateau.");

        int route=2;
        while(route!=0){
            ArrayList<Road> emptyRoad = board.getEmptyRoad();
            if(emptyRoad.size()>=2){
                boolean reponseValide=false;
                while(!reponseValide){
                    System.out.println("Choisissez l'emplacement de la route (Saisissez les id de deux intersections adjacentes) :");
                        
                    int rep1=scan.nextInt();
                    scan.nextLine();
                    int rep2=scan.nextInt();
                    scan.nextLine();
            
                    if(idHasRoadOfPlayer(rep1, rep2, player) || idForRoadHasSettlementsOrCity(rep1, rep2, player)){
                        player.buildRoad(rep1, rep2, board, turn, this);
                        reponseValide=true;
                        route--;
                    }
                    else 
                        System.out.println("La route ne peut pas être construite car elle n'est pas placé sur une intersection déjà connecté a une route ou car elle n'est pas placé sur une intersection possedant une ville.");
            
                }
            }
            else{
                System.out.println(player.getName()+" n'a pas pu utilisé sa carte progrès : construction car il n'y a plus de route libre.");
                route=0;
            }
        }
    }

    public void progressDiscoveryCard(Player player){
        System.out.println("Vous devez choisir deux ressources.");

        int resource=2;
        while(resource!=0){
            boolean reponseValide=false;
            while(!reponseValide){
                System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois (0)/pierre (1)/ble (2)/mouton (3)/argile (4))");
    
                int rep1 = scan.nextInt();
                scan.nextLine();
    
                if(askedResource(rep1)!=-10){                
                    player.collectResources(askedResource(rep1), 1);
                    reponseValide=true;
                }
                else{
                    if(askedResource(rep1)==-10)
                        System.out.println("Ce type de resource n'existe pas !");
                }
            }
            resource--;
        }
    }

    public void progressMonopolyCard(Player p){
        System.out.println("Vous devez choisir un type de ressource. Tous les joueurs devront vous donnez toutes leurs ressources du même type.");
        
        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Quel type de resource souhaitez-vous monopolisé ? (bois (0)/pierre (1)/ble (2)/mouton (3)/argile (4))");

            int rep1 =scan.nextInt();
            scan.nextLine();

            if(askedResource(rep1)!=-10){                
                for(Player player : players){
                    if(player.getPlayerResources()[askedResource(rep1)]>0){
                        player.removeResource(askedResource(rep1), player.getPlayerResources()[askedResource(rep1)]);
                        p.collectResources(askedResource(rep1), player.getPlayerResources()[askedResource(rep1)]);
                    }
                }
                reponseValide=true;
            }
            else{
                if(askedResource(rep1)==-10)
                    System.out.println("Ce type de resource n'existe pas !");
            }
        }
    }

    public void progressBuildCardIA(IA ia){
        System.out.println("Test progressBuildCardIA");
        int route=2;

        ArrayList<Road> emptyRoad = board.getEmptyRoad();
        if(emptyRoad.size()>=2){
            while(route!=0){
                Road randRoad = emptyRoad.get(rand.nextInt(emptyRoad.size()));

                int id1=randRoad.getId1();
                int id2=randRoad.getId2();

                if(idHasRoadOfPlayer(id1, id2, ia) || idForRoadHasSettlementsOrCity(id1, id2, ia)){
                    ia.buildRoad(id1, id2, board, turn, this);
                    route--;
                }
            }
        }
        else{
            System.out.println(ia.getName()+" n'a pas pu utilisé sa carte progrès : construction car il n'y a plus de route libre.");
            route=0;
        }
    }

    public void progressDiscoveryCardIA(IA ia){
        System.out.println("Test progressDiscoveryIA");
        int resource=2;
        while(resource!=0){
            int randResource=rand.nextInt(5);

            ia.collectResources(randResource, 1);

            resource--;
        }
    }

    public void progressMonopolyCardIA(IA ia){
        System.out.println("Test progressMonopolyCardIA");
        int randResource = rand.nextInt(5);

        for(Player player : players){
            if(player.getPlayerResources()[randResource]>0){
                player.removeResource(randResource, player.getPlayerResources()[randResource]);
                ia.collectResources(randResource, player.getPlayerResources()[randResource]);
            }
        }
    }

    public void victoryPointCard(Player player){
        player.addVictoryPoint(1);
    }

    public boolean distanceRules(int id){
        if(id==0){
            if(board.getIntersections()[id+5].getPlayer()==null && board.getIntersections()[id+1].getPlayer()==null){
                return true;
            }
        }
        else if(id==4){
            if(board.getIntersections()[id+5].getPlayer()==null && board.getIntersections()[id-1].getPlayer()==null){
                return true;
            }
        }
        else if(id==20){
            if(board.getIntersections()[id-5].getPlayer()==null && board.getIntersections()[id+1].getPlayer()==null){
                return true;
            }
        }
        else if(id==24){
            if(board.getIntersections()[id-5].getPlayer()==null && board.getIntersections()[id-1].getPlayer()==null){
                return true;
            }
        }
        else if(id>0 && id<4){
            if(board.getIntersections()[id+5].getPlayer()==null && board.getIntersections()[id+1].getPlayer()==null && board.getIntersections()[id-1].getPlayer()==null){
                return true;
            }
        }
        else if(id>20 && id<24){
            if(board.getIntersections()[id-5].getPlayer()==null && board.getIntersections()[id+1].getPlayer()==null && board.getIntersections()[id-1].getPlayer()==null){
                return true;
            }
        }
        else if(id==5 || id==10 || id==15){
            if(board.getIntersections()[id-5].getPlayer()==null && board.getIntersections()[id+1].getPlayer()==null && board.getIntersections()[id+5].getPlayer()==null){
                return true;
            }
        }
        else if(id==9 || id==14 || id==19){
            if(board.getIntersections()[id-5].getPlayer()==null && board.getIntersections()[id-1].getPlayer()==null && board.getIntersections()[id+5].getPlayer()==null){
                return true;
            }
        }
        else if((id>5 && id<9)||(id>10 && id<14)||(id>15 && id<19)){
            if(board.getIntersections()[id-5].getPlayer()==null && board.getIntersections()[id-1].getPlayer()==null 
            && board.getIntersections()[id+5].getPlayer()==null && board.getIntersections()[id+1].getPlayer()==null){
                return true;
            }
        }
        return false;
    }

    public ArrayList<DevCard> initDevCardGame() throws Exception{
        devCard =new ArrayList<DevCard>();
        int victoryCard=5;
        int progressBuildCard=2;
        int progressDiscoveryCard=2;
        int progressMonopolyCard=2;
        int knightCard=14;
        
        while(victoryCard!=0){
            devCard.add(new DevCard(DevCard.VICTORY_POINT));
            victoryCard--;
        }
        while(progressBuildCard!=0){
            devCard.add(new DevCard(DevCard.PROGRESS_BUILD));
            progressBuildCard--;
        }
        while(progressDiscoveryCard!=0){
            devCard.add(new DevCard(DevCard.PROGRESS_DISCOVERY));
            progressDiscoveryCard--;
        }
        while(progressMonopolyCard!=0){
            devCard.add(new DevCard(DevCard.PROGRESS_MONOPOLY));
            progressMonopolyCard--;
        }
        while(knightCard!=0){
            devCard.add(new DevCard(DevCard.KNIGHT));
            knightCard--;
        }

        Collections.shuffle(devCard);

        return devCard;
    }

    public boolean idForRoadHasSettlementsOrCity(int id1, int id2, Player player){
        if(id1<0 || id1>24 || id2<0 || id2>24)
            return false;
        if(board.getIntersections()[id1].getPlayer()==player || board.getIntersections()[id2].getPlayer()==player)
            return true;
        return false;
    }

    public boolean idHasRoadOfPlayer(int id1, int id2, Player player){
        if(id1<0 || id1>24 || id2<0 || id2>24)
            return false;
        for(Road road : board.getRoads()){
            if((road.getId1()==id1 || road.getId2()==id2) && road.getPlayer()==player)
                return true;
        }
        return false;
    }

    public ArrayList<Road> possessedRoads(){
        ArrayList<Road> roads = new ArrayList<Road>();
        for(Road road : board.getRoads()){
            if(road.getPlayer()!=null){
                roads.add(road);
            }
        }
        return roads;
    }

    public ArrayList<Integer> canBuildRoadIA(int id){
        ArrayList<Integer> canBuilRoad =new ArrayList<Integer>();

        if(id==0){
            canBuilRoad.add(board.getIntersections()[id+1].getId());
            canBuilRoad.add(board.getIntersections()[id+5].getId());
        }
        else if(id==4){
            canBuilRoad.add(board.getIntersections()[id-1].getId());
            canBuilRoad.add(board.getIntersections()[id+5].getId());
        }
        else if(id==20){
            canBuilRoad.add(board.getIntersections()[id-5].getId());
            canBuilRoad.add(board.getIntersections()[id+1].getId());
        }
        else if(id==24){
            canBuilRoad.add(board.getIntersections()[id-1].getId());
            canBuilRoad.add(board.getIntersections()[id-5].getId());
        }
        else if(id>0 && id<4){
            canBuilRoad.add(board.getIntersections()[id-1].getId());
            canBuilRoad.add(board.getIntersections()[id+5].getId());
            canBuilRoad.add(board.getIntersections()[id+1].getId());
        }
        else if(id>20 && id<24){
            canBuilRoad.add(board.getIntersections()[id-1].getId());
            canBuilRoad.add(board.getIntersections()[id-5].getId());
            canBuilRoad.add(board.getIntersections()[id+1].getId());
        }
        else if((id>5 && id<9)||(id>10 && id<14)||(id>15 && id<19)){
            canBuilRoad.add(board.getIntersections()[id-1].getId());
            canBuilRoad.add(board.getIntersections()[id+5].getId());
            canBuilRoad.add(board.getIntersections()[id+1].getId());
            canBuilRoad.add(board.getIntersections()[id-5].getId());
        }
        else if(id==5 || id==10 || id==15){
            canBuilRoad.add(board.getIntersections()[id+5].getId());
            canBuilRoad.add(board.getIntersections()[id-5].getId());
            canBuilRoad.add(board.getIntersections()[id+1].getId());
        }
        else if(id==9 || id==14 || id==19){
            canBuilRoad.add(board.getIntersections()[id+5].getId());
            canBuilRoad.add(board.getIntersections()[id-5].getId());
            canBuilRoad.add(board.getIntersections()[id-1].getId());
        }
        return canBuilRoad;
    }
    
    public boolean noIntersectionAvailable(){
        for(Intersection inter : board.getIntersections()){
            if(distanceRules(inter.getId())){
                return false;
            }
        }
        System.out.println("\nIl n'y a plus d'intersection où une colonie peut être construite !");
        return true;
    }

    public void longestRoad(){
        int compteur = 0;
        int compteurMax = 0;
        Player owner = null;

        ArrayList<Road> rCrossed =new ArrayList<Road>();

        for(Player player : players){
            if(!player.getRoads().isEmpty()){
                for(Road road : player.getRoads()){
                    compteur += findLongestRoad(road, player, rCrossed);

                    if(compteur > compteurMax){
                        compteurMax = compteur;
                        owner = player;
                    }

                    compteur = 0;
                }
            }
        }
        if(compteurMax>=5 && compteurMax>longestRoadSize){
            if(longestRoadOwner !=null){
                longestRoadSize = compteurMax;
                System.out.println(longestRoadOwner.toString()+" perd la carte spéciale \"Route la plus longue\" et "+owner.toString()+" obtient la carte avec "+longestRoadSize+" routes !");
                longestRoadOwner.setLongestRoad(false);
                owner.setLongestRoad(true);
                longestRoadOwner = owner;
            }
            else{
                longestRoadSize = compteurMax;
                System.out.println(owner.toString()+" obtient la carte avec "+longestRoadSize+" routes !");
                owner.setLongestRoad(true);
                longestRoadOwner = owner;
            }
        }
    }
    public int findLongestRoad(Road r, Player player, ArrayList<Road> rCrossed){  //r : route sur laquelle on test si elle fait partie de la route la plus longue. rCrossed : route qu'on doit plus tester.
        int compteur = 0;
        int compteurMax = 0;

        ArrayList<Road> roads =new ArrayList<Road>();

        for(Road road1 : player.getRoads()){
            if(!rCrossed.contains(road1)){
                roads.add(road1);
            }
        }

        for(Road road2 : roads){
            if(r.getId1()==road2.getId1() || r.getId2()==road2.getId2()){
                rCrossed.add(r);
                compteur += 1;
                compteur += findLongestRoad(road2, player, rCrossed);

                if(compteur > compteurMax){
                    compteurMax = compteur;
                }
                compteur = 0;
            }
        }
        return compteurMax;
    }

    public ArrayList<Intersection> emptyIntersectionDistanceRules(){
        ArrayList<Intersection> interDistRules=new ArrayList<Intersection>();

        for(Intersection inter : board.getIntersections()){
            if(distanceRules(inter.getId())){
                interDistRules.add(inter);
            }
        }

        return interDistRules;
    }

    //Getters et setters
    public Player[] getPlayers() {return players;}
    public void setPlayers(Player[] players) {this.players = players;}
    public Board getBoard() {return board;}
    public void setBoard(Board board) {this.board = board;}
    public ArrayList<DevCard> getDevCard() {return devCard;}
    public void setDevCard(ArrayList<DevCard> devCard) {this.devCard = devCard;}
    public int getWinner() {return winner;}
    public void setWinner(int winner) {this.winner = winner;}
    public int getTurn() {return turn;}
    public void setTurn(int turn) {this.turn = turn;}
    public Scanner getScan() {return scan;}
    public void setScan(Scanner scan) {this.scan = scan;}
    public int getPlayerTurn() {return playerTurn;}
    public void setPlayerTurn(int playerTurn) {this.playerTurn = playerTurn;}
    public int getStrongestKnightSize() {return strongestKnightSize;}
    public void setStrongestKnightSize(int strongestKnightSize) {this.strongestKnightSize = strongestKnightSize;}
    public Player getStrongestKnightOwner() {return strongestKnightOwner;}
    public void setStrongestKnightOwner(Player strongestKnightOwner) {this.strongestKnightOwner = strongestKnightOwner;}
    public Random getRand() {return rand;}
    public void setRand(Random rand) {this.rand = rand;}
}