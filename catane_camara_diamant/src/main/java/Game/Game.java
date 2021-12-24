package Game;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player[] players;
    private Board board;
    private GameDisplay display;

    private int winner;
    private int playerTurn;

    private Scanner scan =new Scanner(System.in);

    public Game(Player[] players){
        this.players=players;
        board =new Board();
    }

    public void gameCatane(){
        foundation();

        winner=-1;
        playerTurn=0;

        while(winner==-1){
            turn(playerTurn);
        }
    }

    public void foundation(){
        int tour=0;
        while(tour!=2){
            for(Player player : players){
                System.out.println("Tour : " + player.toString());
                if(tour==1)
                    System.out.println("Choisissez la position de votre première colonie : (0 à 24)");
                else System.out.println("Choisissez la position de votre deuxième colonie : (0 à 24)");
                int rep = scan.nextInt();
                player.buildSettlement(rep, board); //METHODE A REALISER
    
                if(tour==1)
                    System.out.println("Choisissez la position de votre première route (Saisir les deux id des intersections où est présente l'arête) : ");
                else System.out.println("Choisissez la position de votre deuxième route (Saisir les deux id des intersections où est présente l'arête) : ");
                int rep1 = scan.nextInt();
                int rep2 = scan.nextInt();
                player.buildRoad(rep1, rep2, board); //METHODE A REALISER
            }
            tour++;
        }
        for(Player player : players){
            for(int i=0; i<player.lastSettlements().getCaseAdj().length; i++){
                player.collectResources(player.lastSettlements().getCaseAdj()[i].getResource().getResourceType());
            }
        }
    }

    public void turn(int playerTurn){
        System.out.println(players[playerTurn].toString() + " lance ses dés !");
        int diceRolls = diceRolls();
        for(Player player : players){
            for(int i=0; i<player.getSettlements().size(); i++){
                for(Case c : player.getSettlements().get(i).getCaseAdj()){
                    if(diceRolls==c.getDiceRoll()){
                        player.collectResources(c.getResource().getResourceType());
                    }
                }
            }
        }

        boolean turnCompleted = false;
        while(!turnCompleted){
            System.out.println("Que souhaitez-vous faire a présent ?");
            System.out.println("Vous pouvez construire, faire du commerce avec les ports ou jouer une carte de developpement.");
            
            boolean reponseValide = false;
            while(!reponseValide){
                System.out.println("Choix : construire | echange | carte");
                
                String rep = scan.nextLine();
                
                switch (rep) {
                    case "construire":
                        buildAnswer(playerTurn);
                        reponseValide=true;
                        break;
                    case "echange":
                        //methode tradeAnswer()
                        reponseValide=true;
                        break;
                    case "carte":

                        reponseValide=true;
                        break;
                    default:
                        System.out.println("Ce choix n'existe pas!");
                        break;
                }
            }
        }
    }



    public void buildAnswer(int playerTurn){
        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Que souhaitez-vous construire (ou souhaitez-vous revenir dans les choix précédent) ? (colonie/ville/route/choix)"); //Le choix "choix" nous fait revenir dans la méthode turn.
            String rep=scan.nextLine();
            switch (rep) {
                case "colonie":
                    if(players[playerTurn].resourceForSettlement()){
                        System.out.println("Choisissez l'emplacement de la colonie (0 à 24): ");

                        int rep1 = scan.nextInt();

                        players[playerTurn].buildSettlement(rep1, board);
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

                        players[playerTurn].buildCity(rep1, board);
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
                        int rep2=scan.nextInt();

                        players[playerTurn].buildRoad(rep1, rep2, board);
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
        System.out.println("Voici tous les ports du plateau : ");
        for(Port p : board.getAllPorts()){
            int index=0;
            System.out.println("    "+p.toString() + ": "+ index);
            index++;
        }

        


             /*if(rep<=players[playerTurn].getPortsOfPlayer().size()-1){
                switch (players[playerTurn].getPortsOfPlayer().get(rep).getPortType()) {
                    case 0:
                        if(players[playerTurn].getPlayerResources()[players[playerTurn].getPortsOfPlayer().get(rep).getResource().getResourceType()]>=2){
                            System.out.println("Choisissez la ressource que voulez en échange : (bois/pierre/ble/mouton/argile)");
                                
                            String rep1=scan.nextLine();
                                
                            players[playerTurn].removeResource(players[playerTurn].getPortsOfPlayer().get(rep).getResource().getResourceType(), 2);
                            players[playerTurn].collectResources(askedResource(rep1));
                            reponseValide=true;
                        }
                        else{
                            System.out.println("Vous n'avez pas assez de ressource pour échanger avec ce port !");
                        }
                    break;
                case 1:
                    if(players[playerTurn].getPlayerResources()[players[playerTurn].getPortsOfPlayer().get(rep).getResource().getResourceType()]>=2){
                        System.out.println("Choisissez la ressource que voulez en échange : (bois/pierre/ble/mouton/argile)");
                            
                        String rep1=scan.nextLine();
                            
                        players[playerTurn].removeResource(players[playerTurn].getPortsOfPlayer().get(rep).getResource().getResourceType(), 2);
                        players[playerTurn].collectResources(askedResource(rep1));
                        reponseValide=true;
                    }
                    else{
                        System.out.println("Vous n'avez pas assez de ressource pour échanger avec ce port !");
                    }
                    break;
            }
        }*/
    }

    public int askedResource(String resourceType){
        int resource=0;
        switch (resourceType) {
            case "bois":
                resource=Resource.BOIS;
                break;
            case "pierre":
                resource=Resource.BOIS;
                break;
            case "ble":
                resource=Resource.BOIS;
                break;
            case "mouton":
                resource=Resource.BOIS;
                break;
            case "argile":
                resource=Resource.BOIS;
                break;
        }
        return resource;
    }

    public int diceRolls(){
        Random rand =new Random();
        return 2+rand.nextInt(12-2);
    }
}
