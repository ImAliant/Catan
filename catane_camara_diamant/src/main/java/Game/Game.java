package Game;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player[] players;
    private Board board;
    private GameDisplay display;

    private int winner;
    private int playerTurn;
    private int turn;

    private Scanner scan =new Scanner(System.in);
 
    public Game(Player[] players){
        this.players=players;
        board =new Board();
    }

    public void gameCatane(){
        turn=0;
        foundation();

        winner=-1;
        playerTurn=0;

        

        while(winner==-1){
            turn++;
            turn(playerTurn);
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

    public void foundation(){ //AUCUN BUG DANS LA FONDATION
        int tour=0;
        while(tour!=2){
            for(Player player : players){
                System.out.println("Tour : " + player.toString());
                if(tour==0)
                    System.out.println("\nChoisissez la position de votre première colonie : (0 à 24)");
                else 
                    System.out.println("\nChoisissez la position de votre deuxième colonie : (0 à 24)");
                int rep = scan.nextInt(); //VOIR SI CETTE CONSTRUCTION RESPECTE LA REGLE DE DISTANCE DES COLONIES
                player.buildSettlement(rep, board, turn);
                System.out.println("Colonie construite en ("+rep+").");
    
                if(tour==0)
                    System.out.println("\nChoisissez la position de votre première route (Saisir les deux id des intersections où est présente l'arête) : ");
                else 
                    System.out.println("\nChoisissez la position de votre deuxième route (Saisir les deux id des intersections où est présente l'arête) : ");
                int rep1 = scan.nextInt(); //Tester si id1 ou id2 est identique a rep
                int rep2 = scan.nextInt();
                player.buildRoad(rep1, rep2, board, turn); 
                System.out.println("Route construite en ("+rep1+" "+rep2+").\n");
            }
            tour++;
        }
        for(Player player : players){
            for(int i=0; i<player.lastSettlements().getCaseAdj().length; i++){
                player.collectResources(player.lastSettlements().getCaseAdj()[i].getResource().getResourceType());
            }
        }
        scan.nextLine();
    }

    public void turn(int playerTurn){
        System.out.println(players[playerTurn].toString() + " lance ses dés !");
        int diceRolls = diceRolls();
        System.out.println(players[playerTurn].toString() + " obtient " + diceRolls);

        //if(diceRolls==7){

        //}
        //else{
            for(Player player : players){
                for(int i=0; i<player.getSettlements().size(); i++){
                    for(Case c : player.getSettlements().get(i).getCaseAdj()){
                        if(diceRolls==c.getDiceRoll()){
                            System.out.println(player.toString() + " obtient 1 ressource : " + c.getResource().toString());
                            player.collectResources(c.getResource().getResourceType());
                        }
                    }
                }
            }
        //}

        System.out.println(players[playerTurn].resourceOfPlayerToString()+"\n");

        boolean turnCompleted = false;
        while(!turnCompleted){
            System.out.println("Que souhaitez-vous faire a présent ?");
            System.out.println("Vous pouvez construire, faire du commerce avec les ports, jouer une carte de developpement et/ou consulter vos ressources.\n");
            
            boolean reponseValide = false;
            while(!reponseValide){
                System.out.println("Choix : construire | echange | carte | ressource | rien");
                
                String rep=scan.nextLine();
                
                switch (rep) {
                    case "construire":
                        buildAnswer(playerTurn);
                        reponseValide=true;
                        break;
                    case "echange":
                        tradeAnswer(playerTurn);
                        reponseValide=true;
                        break;
                    case "carte":

                        reponseValide=true;
                        break;
                    case "ressource":
                        System.out.println(players[playerTurn].resourceOfPlayerToString()+"\n");
                        reponseValide=true;
                        break;
                    case "rien":
                        System.out.println("Vous choisissez de ne rien faire ce tour.\n");
                        reponseValide=true;
                        turnCompleted=true;
                        break;
                    default:
                        System.out.println("Ce choix n'existe pas!\n");
                        break;
                }
            }

            if(winner()){
                winner = playerTurn;
            }
        }
    }

    public void buildAnswer(int playerTurn){
        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Que souhaitez-vous construire ? (colonie/ville/route) | Souhaitez-vous revenir aux choix précédent ? (choix)"); //Le choix "choix" nous fait revenir dans la méthode turn.
            String rep=scan.nextLine();
            switch (rep) {
                case "colonie":
                    if(players[playerTurn].resourceForSettlement()){
                        System.out.println("Choisissez l'emplacement de la colonie (0 à 24): ");

                        int rep1 = scan.nextInt();
                        scan.nextLine();

                        players[playerTurn].buildSettlement(rep1, board, turn);
                        System.out.println("Colonie construite en ("+rep1+").\n");
                        reponseValide=true;
                    }
                    else{
                        System.out.println("Vous n'avez pas les resources nécessaires pour construire une colonie !");
                    }
                    break;
                case "ville":
                    if(players[playerTurn].resourceForCity()){
                        System.out.println("Choisissez l'emplacement de la ville (0 à 24): ");

                        int rep1=scan.nextInt();
                        scan.nextLine();

                        players[playerTurn].buildCity(rep1, board);
                        System.out.println("Ville construite en ("+rep1+").\n");
                        reponseValide=true;
                    }
                    else{
                        System.out.println("Vous n'avez pas les resources nécessaires pour construire une ville !");
                    }
                    break;
                case "route":
                    if(players[playerTurn].resourceForRoad()){
                        System.out.println("Choisissez l'emplacement de la route (Saisissez les id de deux intersections adjacentes) :");
                        
                        int rep1=scan.nextInt();
                        scan.nextLine();
                        int rep2=scan.nextInt();
                        scan.nextLine();

                        players[playerTurn].buildRoad(rep1, rep2, board, turn);
                        System.out.println("Route construite en ("+rep1+" "+rep2+").\n");
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

    public void tradeAnswer(int playerTurn){
        if(players[playerTurn].getPortsOfPlayer()!=null){ //Possède port 3:1 et/ou 2:1
            players[playerTurn].resourceOfPlayerToString();
            if(players[playerTurn].getPortsType2OfPlayer() !=null && players[playerTurn].getPortsType3OfPlayer() !=null){//Possède 2:1 et 3:1
                trade2or3Resources(playerTurn);
            }
            else if(players[playerTurn].getPortsType2OfPlayer()==null && players[playerTurn].getPortsType3OfPlayer() !=null){//Possède 3:1
                trade3Resources(playerTurn);
            }
            else if(players[playerTurn].getPortsType2OfPlayer() !=null && players[playerTurn].getPortsType3OfPlayer() ==null){//Possède 2:1
                trade2or4Resources(playerTurn);
            }
        }
        else{ //Ne possède pas de port.
            trade4Resources(playerTurn);
        }
    }

    public void trade2or3Resources(int playerTurn){
        players[playerTurn].resourceOfPlayerToString();
        int index = -1;
        System.out.println("Voici les ports 2:1 disponibles :");
        for(Port elem : players[playerTurn].getPortsType2OfPlayer()){
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
                    trade3Resources(playerTurn);
                    reponseValide=true;
                }
                else{
                    if(players[playerTurn].hasTwoResources(players[playerTurn].getPortsType2OfPlayer().get(repToInt).getResource().getResourceType())){
                        System.out.println("Quel ressource voulez-vous en échange ? (bois/pierre/ble/mouton/argile)");

                        String rep1=scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=players[playerTurn].getPortsType2OfPlayer().get(repToInt).getResource().getResourceType()){
                            players[playerTurn].removeResource(players[playerTurn].getPortsType2OfPlayer().get(repToInt).getResource().getResourceType(), 2);
                            players[playerTurn].collectResources(askedResource(rep1));
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)!=-10)
                                System.out.println("Ce type de ressource n'existe pas !");
                            if(askedResource(rep1)!=players[playerTurn].getPortsType2OfPlayer().get(repToInt).getResource().getResourceType())
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

    public void trade3Resources(int playerTurn){
        players[playerTurn].resourceOfPlayerToString();
        System.out.println("Au moins une de votre colonie/ville possède un port 3:1, vous pouvez donc échanger 3 ressources de votre choix contre 1 ressources de votre choix.");

        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Quel type de ressource souhaitez-vous échangé ? (bois/pierre/ble/mouton/argile) | Souhaitez-vous revenir aux choix précédent ? (choix)");

            String rep=scan.nextLine();

            switch (askedResource(rep)) {
                case Resource.BOIS:
                    if(players[playerTurn].hasThreeResources(Resource.BOIS)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (pierre/ble/mouton/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=0){                
                            players[playerTurn].removeResource(Resource.BOIS, 3);
                            players[playerTurn].collectResources(askedResource(rep1));
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
                    if(players[playerTurn].hasThreeResources(Resource.PIERRE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/ble/mouton/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=1){                
                            players[playerTurn].removeResource(Resource.PIERRE, 3);
                            players[playerTurn].collectResources(askedResource(rep1));
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
                    if(players[playerTurn].hasThreeResources(Resource.BLE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/mouton/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=2){                
                            players[playerTurn].removeResource(Resource.BLE, 3);
                            players[playerTurn].collectResources(askedResource(rep1));
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
                    if(players[playerTurn].hasThreeResources(Resource.MOUTON)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/ble/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=3){                
                            players[playerTurn].removeResource(Resource.MOUTON, 3);
                            players[playerTurn].collectResources(askedResource(rep1));
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
                    if(players[playerTurn].hasThreeResources(Resource.ARGILE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/ble/mouton)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=4){                
                            players[playerTurn].removeResource(Resource.ARGILE, 3);
                            players[playerTurn].collectResources(askedResource(rep1));
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
                default:
                    if(rep=="choix"){
                        reponseValide=true;
                        break;
                    }
                    else
                        System.out.println("Ce type de ressource n'existe pas !");
                    break;
            }
        }
    }  

    public void trade4Resources(int playerTurn){
        players[playerTurn].resourceOfPlayerToString();
        System.out.println();
        System.out.println("Aucune de vos colonies ne possède de port." +System.lineSeparator()+ "Vous pouvez choisir d'échanger 4 ressources de votre choix contre 1 ressource de votre choix.");
        System.out.println();

        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Quel type de ressource souhaitez-vous échangé ? (bois/pierre/ble/mouton/argile) | Souhaitez-vous revenir aux choix précédent ? (choix)");
            
            String rep=scan.nextLine();

            switch (askedResource(rep)) {
                case Resource.BOIS:
                    if(players[playerTurn].hasFourResources(Resource.BOIS)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (pierre/ble/mouton/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=0){                
                            players[playerTurn].removeResource(Resource.BOIS, 4);
                            players[playerTurn].collectResources(askedResource(rep1));
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
                    if(players[playerTurn].hasFourResources(Resource.PIERRE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/ble/mouton/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=1){                
                            players[playerTurn].removeResource(Resource.PIERRE, 4);
                            players[playerTurn].collectResources(askedResource(rep1));
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
                    if(players[playerTurn].hasFourResources(Resource.BLE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/mouton/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=2){                
                            players[playerTurn].removeResource(Resource.BLE, 4);
                            players[playerTurn].collectResources(askedResource(rep1));
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
                    if(players[playerTurn].hasFourResources(Resource.MOUTON)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/ble/argile)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=3){                
                            players[playerTurn].removeResource(Resource.MOUTON, 4);
                            players[playerTurn].collectResources(askedResource(rep1));
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
                    if(players[playerTurn].hasFourResources(Resource.ARGILE)){
                        System.out.println("Quel type de ressource souhaitez-vous recevoir ? (bois/pierre/ble/mouton)");

                        String rep1 = scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=4){                
                            players[playerTurn].removeResource(Resource.ARGILE, 4);
                            players[playerTurn].collectResources(askedResource(rep1));
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
                default:
                    if(rep=="choix"){
                        reponseValide=true;
                        break;
                    }
                    System.out.println("Ce type de ressource n'existe pas !");
                    break;
            }
        }
    }

    public void trade2or4Resources(int playerTurn){
        players[playerTurn].resourceOfPlayerToString();
        int index=0;
        for(Port elem : players[playerTurn].getPortsType2OfPlayer()){
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
                    trade4Resources(playerTurn);
                else{
                    if(players[playerTurn].hasTwoResources(players[playerTurn].getPortsType2OfPlayer().get(repToInt).getResource().getResourceType())){
                        System.out.println("Quel ressource voulez-vous en échange ? (bois/pierre/ble/mouton/argile)");

                        String rep1=scan.nextLine();

                        if(askedResource(rep1)!=-10 && askedResource(rep1)!=players[playerTurn].getPortsType2OfPlayer().get(repToInt).getResource().getResourceType()){
                            players[playerTurn].removeResource(players[playerTurn].getPortsType2OfPlayer().get(repToInt).getResource().getResourceType(), 2);
                            players[playerTurn].collectResources(askedResource(rep1));
                            reponseValide=true;
                        }
                        else{
                            if(askedResource(rep1)!=-10)
                                System.out.println("Ce type de ressource n'existe pas !");
                            if(askedResource(rep1)!=players[playerTurn].getPortsType2OfPlayer().get(repToInt).getResource().getResourceType())
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

    //public boolean longestRoad(){}

    //public boolean strongestKnight(){}

    public int diceRolls(){
        Random rand =new Random();
        return 2+rand.nextInt(12-2);
    }
}
