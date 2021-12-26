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

            boolean reponseValide = false;
            while(!reponseValide){
                System.out.println("Vous pouvez construire, faire du commerce avec les ports ou jouer une carte de developpement.");
                System.out.println("(construire | echange | carte");

                String rep = scan.nextLine();

                switch (rep) {
                    case "construire":

                        reponseValide=true;
                        break;
                    case "echange":

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

    public int diceRolls(){
        Random rand =new Random();
        return 2+rand.nextInt(12-2);
    }


    public void moveRobber(){
      boolean reponseValide = false;

      while(!reponseValide){
      System.out.println("Dans quel case voulez-vous placer le voleur ?");
      int rep = scan.nextInt();
      scan.nextLine();

      if(rep<0 || rep>15){
          System.out.println("Veillez choisir un tuile parmi celle du jeu");
      }else{
        board.getIndexRobber().setRobber(false);
        board.setIndexRobber(rep);
        board.getIndexRobber().setRobber(true);
        reponseValide = true;
        System.out.println("Le voleur est maintenant à la tuile "+rep+ ".");
      }
    }
  }
}
