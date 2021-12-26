package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player[] players;
    private Board board;
    private ArrayList<DevCard> devCard;
    //private GameDisplay display;

    private int winner;
    private int playerTurn;
    private int turn;

    private int strongestKnightSize;
    private Player strongestKnightOwner;
    private int longestRoadSize;
    private Player longestRoadOwner;

    private Scanner scan =new Scanner(System.in);
 
    public Game(Player[] players) throws Exception{
        this.players=players;
        board =new Board();
        initDevCardGame(); 
    }

    public void gameCatane(){
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

    public void foundation(){
        int tour=0;
        while(tour!=2){
            for(Player player : players){
                System.out.println("Tour : " + player.toString());

                boolean reponseValide1=false;
                while(!reponseValide1){
                    if(tour==0)
                        System.out.println("\nChoisissez la position de votre première colonie : (0 à 24)");
                    else 
                        System.out.println("\nChoisissez la position de votre deuxième colonie : (0 à 24)");
                
                    int rep = scan.nextInt();

                    if(distanceRules(rep)){
                        player.buildSettlement(rep, board, turn);
                        reponseValide1=true;
                    }
                    else{
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
                    int rep2 = scan.nextInt();

                    if(idForRoadHasSettlementsOrCity(rep1, rep2, player)){
                        player.buildRoad(rep1, rep2, board, turn);
                        reponseValide2=true;
                    }
                    else{
                        System.out.println("La route ne peut pas être placé a cet emplacement !");
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
        scan.nextLine();
    }

    public void buildAnswer(Player player){
        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Que souhaitez-vous construire ? (colonie/ville/route) | Souhaitez-vous revenir aux choix précédent ? (choix)"); //Le choix "choix" nous fait revenir dans la méthode turn.
            String rep=scan.nextLine();
            switch (rep) {
                case "colonie":
                    if(player.resourceForSettlement()){
                        System.out.println("Choisissez l'emplacement de la colonie (0 à 24): ");

                        int rep1 = scan.nextInt();
                        scan.nextLine();

                        player.buildSettlement(rep1, board, turn);
                        reponseValide=true;
                    }
                    else{
                        System.out.println("Vous n'avez pas les resources nécessaires pour construire une colonie !");
                    }
                    break;
                case "ville":
                    if(player.resourceForCity()){
                        System.out.println("Choisissez l'emplacement de la ville (0 à 24): ");

                        int rep1=scan.nextInt();
                        scan.nextLine();

                        player.buildCity(rep1, board);
                        reponseValide=true;
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

                        player.buildRoad(rep1, rep2, board, turn);
                        reponseValide=true;
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
                        player.addDevCard(devCard.get(0));
                        System.out.println("\nVous venez de piocher "+devCard.get(0).toString()+" !");
                        devCard.remove(0);
                        player.removeResourceForDevCard();
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

    public void resourceAnswer(Player player){
        System.out.println(player.resourceOfPlayerToString()+"\n");
    }

    public void playCardAnswer(Player player){
        if(player.getCards().isEmpty()){
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
                    if(player.hasTwoResources(player.getPortsType2OfPlayer().get(repToInt).getResource().getResourceType())){
                        System.out.println("Quel ressource voulez-vous en échange ? (bois/pierre/ble/mouton/argile)");

                        String rep1=scan.nextLine();

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
            System.out.println("Quel type de ressource souhaitez-vous échangé ? (bois/pierre/ble/mouton/argile) | Souhaitez-vous revenir aux choix précédent ? (choix)");

            String rep=scan.nextLine();
            System.out.println();

            switch (askedResource(rep)) {
                case Resource.BOIS:
                    if(player.hasThreeResources(Resource.BOIS)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (pierre/ble/mouton/argile)");

                        String rep1 = scan.nextLine();
                        System.out.println();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=0){                
                            player.removeResource(Resource.BOIS, 3);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==0)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.PIERRE:
                    if(player.hasThreeResources(Resource.PIERRE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/ble/mouton/argile)");

                        String rep1 = scan.nextLine();
                        System.out.println();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=1){                
                            player.removeResource(Resource.PIERRE, 3);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==1)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.BLE:
                    if(player.hasThreeResources(Resource.BLE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/mouton/argile)");

                        String rep1 = scan.nextLine();
                        System.out.println();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=2){                
                            player.removeResource(Resource.BLE, 3);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==2)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.MOUTON:
                    if(player.hasThreeResources(Resource.MOUTON)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/ble/argile)");

                        String rep1 = scan.nextLine();
                        System.out.println();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=3){                
                            player.removeResource(Resource.MOUTON, 3);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==3)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.ARGILE:
                    if(player.hasThreeResources(Resource.ARGILE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/ble/mouton)");

                        String rep1 = scan.nextLine();
                        System.out.println();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=4){                
                            player.removeResource(Resource.ARGILE, 3);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==4)
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

    public void trade4Resources(Player player){
        player.resourceOfPlayerToString();
        System.out.println();
        System.out.println("Aucune de vos colonies ne possède de port." +System.lineSeparator()+ "Vous pouvez choisir d'échanger 4 ressources de votre choix contre 1 ressource de votre choix.");
        System.out.println();

        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Quel type de ressource souhaitez-vous échangé ? (bois/pierre/ble/mouton/argile) | Souhaitez-vous revenir aux choix précédent ? (choix)");
            
            String rep=scan.nextLine();

            switch (askedResource(rep)) {
                case Resource.BOIS:
                    if(player.hasFourResources(Resource.BOIS)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (pierre/ble/mouton/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=0){                
                            player.removeResource(Resource.BOIS, 4);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==0)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.PIERRE:
                    if(player.hasFourResources(Resource.PIERRE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/ble/mouton/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=1){                
                            player.removeResource(Resource.PIERRE, 4);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==1)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.BLE:
                    if(player.hasFourResources(Resource.BLE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/mouton/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=2){                
                            player.removeResource(Resource.BLE, 4);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==2)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.MOUTON:
                    if(player.hasFourResources(Resource.MOUTON)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/ble/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=3){                
                            player.removeResource(Resource.MOUTON, 4);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==3)
                                System.out.println("Vous ne pouvez pas choisir la même ressource !");
                        }
                    }
                    else{
                        System.out.println("Vous n'avez pas les ressources nécessaires !");
                    }
                    break;
                case Resource.ARGILE:
                    if(player.hasFourResources(Resource.ARGILE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/ble/mouton)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=4){                
                            player.removeResource(Resource.ARGILE, 4);
                            player.collectResources(askedResource(rep1), 1);
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)==-10)
                                System.out.println("Ce type de resource n'existe pas !");
                            if(askedResource(rep1)==4)
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
                    if(player.hasTwoResources(player.getPortsType2OfPlayer().get(repToInt).getResource().getResourceType())){
                        System.out.println("Quel ressource voulez-vous en échange ? (bois/pierre/ble/mouton/argile)");

                        String rep1=scan.nextLine();

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

    public int askedResource(String resourceType){
        int resource=0;
        switch (resourceType) {
            case "bois":
                resource=Resource.BOIS;
                break;
            case "pierre":
                resource=Resource.PIERRE;
                break;
            case "ble":
                resource=Resource.BLE;
                break;
            case "mouton":
                resource=Resource.MOUTON;
                break;
            case "argile":
                resource=Resource.ARGILE;
                break;
            case "choix":
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

    /*public void longestRoad(){
        int roadSize = findLongestRoad(0, 1);
        if(roadSize>=5){

        }
    }

    public int findLongestRoad(int i1, int j1){
        ArrayList<Road> roads = possessedRoads();
        int compteur=0;
        for(int i=i1; i<roads.size(); i++){
            for(int j=j1; j<roads.size(); j++){
                if((roads.get(i).getId1()==roads.get(j).getId1())||(roads.get(i).getId1()==roads.get(j).getId2())
                ||(roads.get(i).getId2()==roads.get(j).getId1())||(roads.get(i).getId2()==roads.get(j).getId2())){
                    if((roads.get(i).getPlayer()==roads.get(j).getPlayer())){
                        int compteurTemp=compteur;
                        if(findLongestRoad(i, j)>compteurTemp)
                            compteur = findLongestRoad(i, j);
                    }
                }
            }
        }
        return compteur;
    }

    public Player findLongestRoadOwner(int i1, int j1){
        ArrayList<Road> roads = possessedRoads();
        int compteur=0;
        for(int i=i1; i<roads.size(); i++){
            for(int j=j1; j<roads.size(); j++){
                if((roads.get(i).getId1()==roads.get(j).getId1())||(roads.get(i).getId1()==roads.get(j).getId2())
                ||(roads.get(i).getId2()==roads.get(j).getId1())||(roads.get(i).getId2()==roads.get(j).getId2())){
                    if((roads.get(i).getPlayer()==roads.get(j).getPlayer())){
                        int compteurTemp=compteur;
                        if(findLongestRoad(i, j)>compteurTemp)
                            compteur = findLongestRoad(i, j);
                    }
                }
            }
        }
        return compteur;
    }*/

    public void strongestKnight(){
        for(Player player : players){
            int strongestKnight = player.getKnightPlayed();
            if(strongestKnight >=3 && strongestKnight>strongestKnightSize){
                strongestKnightSize=strongestKnight;
                if(strongestKnightOwner!=null){
                    strongestKnightOwner.setStrongestKnight(false);
                    strongestKnightOwner.removeVictoryPoint(2);
                    strongestKnightOwner=player;
                    player.setStrongestKnight(true);
                    player.addVictoryPoint(2);
                }
                else{
                    strongestKnightOwner=player;
                    player.setStrongestKnight(true);
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
                System.out.println(player.toString()+" vous avez plus de 7 resources aux total !");
                int discardResource=player.totalResource()/2;
                System.out.println("Vous devez vous défausser de la moitié de vos ressources. C'est à dire "+ discardResource);

                while(discardResource!=0){
                    System.out.println(player.resourceOfPlayerToString());

                    boolean reponseValide=false;
                    while(!reponseValide){
                        System.out.println("\nChoisissez une ressource à défausser : (bois/pierre/ble/mouton/argile)");

                        String rep=scan.nextLine();

                        switch (askedResource(rep)) {
                            case Resource.BOIS:
                                if(player.hasOneResources(Resource.BOIS)){
                                    player.removeResource(Resource.BOIS, 1);
                                    System.out.println(player.toString()+" c'est défaussé 1 ressource bois.");
                                    reponseValide=true;
                                }
                                else
                                    System.out.println("Vous n'avez pas assez de cette ressource !");
                                break;
                            case Resource.PIERRE:
                                if(player.hasOneResources(Resource.PIERRE)){
                                    player.removeResource(Resource.PIERRE, 1);
                                    System.out.println(player.toString()+" c'est défaussé 1 ressource pierre.");
                                    reponseValide=true;
                                }
                                else
                                    System.out.println("Vous n'avez pas assez de cette ressource !");
                                break;
                            case Resource.BLE:
                                if(player.hasOneResources(Resource.BLE)){
                                    player.removeResource(Resource.BLE, 1);
                                    System.out.println(player.toString()+" c'est défaussé 1 ressource ble.");
                                    reponseValide=true;
                                }
                            else
                                System.out.println("Vous n'avez pas assez de cette ressource !");
                                break;
                            case Resource.MOUTON:
                                if(player.hasOneResources(Resource.MOUTON)){
                                    player.removeResource(Resource.MOUTON, 1);
                                    System.out.println(player.toString()+" c'est défaussé 1 ressource mouton.");
                                    reponseValide=true;
                                }
                                else
                                    System.out.println("Vous n'avez pas assez de cette ressource !");
                                break;
                            case Resource.ARGILE:
                                if(player.hasOneResources(Resource.ARGILE)){
                                    player.removeResource(Resource.ARGILE, 1);
                                    System.out.println(player.toString()+" c'est défaussé 1 ressource argoile.");
                                    reponseValide=true;
                                }
                                else
                                    System.out.println("Vous n'avez pas assez de cette ressource !");
                                break;
                            default:
                                if(askedResource(rep)==-10)
                                    System.out.println("Cette ressource n'existe pas !");
                                break;
                        }
                    }
                    discardResource--;
                }
            }
        }    
    }

    public void moveRobber(){
        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Dans quel case voulez-vous placer le voleur ?");
            
            int rep=scan.nextInt();
            scan.nextLine();

            if(rep>=0 || rep<=15){
                for(Case c : board.getCases()){
                    if(c.isRobber()){
                        c.setRobber(false);
                        break;
                    }
                }
                board.getCases()[rep].setRobber(true);
                board.setIndexRobber(rep);
                reponseValide=true;
            }   
            else
                System.out.println("Cette case n'existe pas !");
        }
    }

    public void knightCard(Player player){
        moveRobber();

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
                    int randResource = rand.nextInt(4);
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

    public void progressBuildCard(Player player){
        System.out.println("\nVous devez maintenant poser deux routes sur le plateau.");

        int route=2;
        while(route!=0){
            System.out.println("Choisissez l'emplacement de la route (Saisissez les id de deux intersections adjacentes) :");
                        
            int rep1=scan.nextInt();
            scan.nextLine();
            int rep2=scan.nextInt();
            scan.nextLine();

            player.buildRoad(rep1, rep2, board, turn);
            System.out.println("Route construite en ("+rep1+" "+rep2+").\n");

            route--;
        }
    }

    public void progressDiscoveryCard(Player player){
        System.out.println("Vous devez choisir deux ressources.");

        int resource=2;
        while(resource!=0){
            boolean reponseValide=false;
            while(!reponseValide){
                System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/ble/mouton/argile)");
    
                String rep1 = scan.nextLine();
    
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
            System.out.println("Quel type de resource souhaitez-vous monopolisé ? (bois/pierre/ble/mouton/argile)");

            String rep1 =scan.nextLine();

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
        if((board.getIntersections()[id1].getPlayer()!=null || board.getIntersections()[id2].getPlayer()!=null) && 
        (board.getIntersections()[id1].getPlayer()==player || board.getIntersections()[id2].getPlayer()==player))
            return true;
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
}