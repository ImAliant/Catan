package Game;

import java.util.ArrayList;
import java.util.Scanner;

public class Human extends Player{

    private Scanner scan =new Scanner(System.in);
    
    public Human(String name, int color) {
        super(name + " (Humain)", color);
    }

    @Override
    public void turn(Board board, Game game) {
        System.out.println(this.toString() + " lance ses dés !");
        int diceRolls1 = game.diceRolls();
        int diceRolls2 = game.diceRolls();
        int diceRolls=diceRolls1+diceRolls2;
        System.out.println(this.toString() + " obtient " + diceRolls + "\n");

        if(diceRolls==7){
            game.diceRolls7();
            moveRobber(board, game);
        }
        else{
            for(Player player : game.getPlayers()){
                for(int i=0; i<player.getSettlements().size(); i++){
                    for(Case c : player.getSettlements().get(i).getCaseAdj()){
                        if(diceRolls==c.getDiceRoll()){
                            System.out.println(player.toString() + " obtient 1 ressource : " + c.getResource().toString());
                            player.collectResources(c.getResource().getResourceType(), 1);
                        }
                    }
                }
                for(int i=0; i<player.getCities().size(); i++){
                    for(Case c : player.getCities().get(i).getCaseAdj()){
                        if(diceRolls==c.getDiceRoll()){
                            System.out.println(player.toString()+ " obtient 2 ressource : "+c.getResource().toString());
                            player.collectResources(c.getResource().getResourceType(), 2);
                        }
                    }
                }
            }
        }

        game.resourceAnswer(this);

        boolean turnCompleted = false;
        while(!turnCompleted){
            System.out.println("Que souhaitez-vous faire a présent ?");
            System.out.println("Vous pouvez construire, faire du commerce avec les ports, acheter une carte de développent, jouer une carte de developpement et/ou consulter vos ressources.\n");
            
            boolean reponseValide = false;
            while(!reponseValide){
                System.out.println("Choix : construire | echange | achat | carte | ressource | rien");
                
                String rep=game.getScan().nextLine();
                
                switch (rep) {
                    case "construire":
                        game.buildAnswer(this);
                        reponseValide=true;
                        break;
                    case "echange":
                        game.tradeAnswer(this);
                        reponseValide=true;
                        break;
                    case "achat":
                        game.buyAnswer(this);
                        reponseValide=true;
                        break;
                    case "carte":
                        game.playCardAnswer(this);
                        reponseValide=true;
                        break;
                    case "ressource":
                        game.resourceAnswer(this);
                        reponseValide=true;
                        break;
                    case "rien":
                        System.out.println(getName()+" choisit de ne rien faire ce tour.\n");
                        reponseValide=true;
                        turnCompleted=true;
                        break;
                    default:
                        System.out.println("Ce choix n'existe pas!\n");
                        break;
                }
            }
            
            //game.longestRoad();
            if(game.winner()){
                game.setWinner(game.getPlayerTurn());
            }
        }
    }

    @Override
    public void buildSettlement(int id, Board board, int turn){
        if(id>=0 && id<=24){
            if(board.getIntersections()[id].getBuilding().upgradeToSettlements()){
                getSettlements().add(board.getIntersections()[id]);
                board.getIntersections()[id].setPlayer(this);
                if(turn!=0){
                    removeResourceForSettlements();
                }
                System.out.println("Colonie construite en ("+id+").");
                addVictoryPoint(1);
            }
            else{
                System.out.println("Cette intersection ne peut pas pas être amélioré en colonie !");
                System.out.println("Saisissez un nouvel id (0 à 24) :");
                int rep = scan.nextInt();
                scan.nextLine();

                buildSettlement(rep, board, turn);
            }
        }
        else{
            System.out.println("Cette intersection n'existe pas !");
            System.out.println("Saisissez un nouvel id (0 à 24) :");
            int rep=scan.nextInt();
            scan.nextLine();

            buildSettlement(rep, board, turn);
        }
    }

    @Override
    public void buildCity(int id, Board board){
        if(id >= 0 && id <=24){
            if(board.getIntersections()[id].getBuilding().upgradeToCity()){
                getCities().add(board.getIntersections()[id]);
                getSettlements().remove(board.getIntersections()[id]);
                removeResourceForCities();
                addVictoryPoint(2);
                System.out.println("Ville construite en ("+id+").\n");
            }
            else{
                System.out.println("Cette colonie ne peut pas être amélioré en ville !");
                System.out.println("Saississez un nouvel id (0 à 24) : ");
                int rep=scan.nextInt();
                scan.nextLine();
                buildCity(rep, board);
            }
        }
        else{
            System.out.println("Cette intersection n'existe pas !");
            System.out.println("Saississez un nouvel id (0 à 24) : ");
            int rep=scan.nextInt();
            scan.nextLine();
            buildCity(rep, board);
        }
    }
    
    public void buildRoad(int id1, int id2, Board board, int turn,  Game game){
        if((id1 >= 0 && id1 <= 24) && (id2 >= 0 && id2 <= 24)){
            if(board.getSpecificRoad(id1, id2)!=null){
                if(board.getSpecificRoad(id1, id2).upgradeRoad(this)){
                    if(turn!=0){
                        removeResourceForRoad();
                    }
                    getRoads().add(board.getSpecificRoad(id1, id2));
                    System.out.println("Route construite en ("+id1+" "+id2+").\n");
                }
                else{
                    System.out.println("Cette arête possède déjà une route !");
                    System.out.println("Saississez deux nouveaux id adjacents (0 à 24) : ");
                    int rep1=scan.nextInt();
                    scan.nextLine();
                    int rep2=scan.nextInt();
                    scan.nextLine();
                    
                    buildRoad(rep1, rep2, board, turn, game);
                }
            }
            else{
                boolean reponseValide=false;
                while(!reponseValide){
                    System.out.println("Cette route n'existe pas !");
                    System.out.println("Saississez deux nouveaux id adjacents (0 à 24) : ");
                    int rep1=scan.nextInt();
                    scan.nextLine();
                    int rep2=scan.nextInt();
                    scan.nextLine();
                
                    if(game.idIsRoad(id1, id2, this) || game.idForRoadIsSettlementsOrCity(rep1, rep2, this)){
                        buildRoad(rep1, rep2, board, turn, game);
                        reponseValide=true;
                    }
                }
            }
        }
        else{
            boolean reponseValide=false;
            while(!reponseValide){
                System.out.println("Cette route n'existe pas !");
                System.out.println("Saississez deux nouveaux id adjacents (0 à 24) : ");
                int rep1=scan.nextInt();
                scan.nextLine();
                int rep2=scan.nextInt();
                scan.nextLine();
                
                if(game.idIsRoad(id1, id2, this) || game.idForRoadIsSettlementsOrCity(rep1, rep2, this)){
                    buildRoad(rep1, rep2, board, turn, game);
                    reponseValide=true;
                }
            }
        }
    }

    @Override
    public void moveRobber(Board board, Game game){
        boolean reponseValide=false;
        while(!reponseValide){
            System.out.println("Dans quel case voulez-vous placer le voleur ?");
            
            int rep=game.getScan().nextInt();
            game.getScan().nextLine();

            if((rep>=0 && rep<=15) && board.getIndexRobber()!=rep){
                for(Case c : board.getCases()){
                    if(c.isRobber()){
                        c.setRobber(false);
                        break;
                    }
                }
                board.getCases()[rep].setRobber(true);
                board.setIndexRobber(rep);
                reponseValide=true;
                System.out.println("\nLe voleur à été déplacé sur la case "+board.getIndexRobber());            
            }   
            else
                if(board.getIndexRobber()==rep){
                    System.out.println("Le voleur est déjà présent sur cette case !");
                }
                else
                    System.out.println("Cette case n'existe pas !");
        }

        //Voler une ressource comme un chevalier

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
            System.out.println("Voici les joueurs auxquelles vous pouvez voler une ressources:");
            int index = -1;
            for(Intersection inter : game.getBoard().getCases()[game.getBoard().getIndexRobber()].getCaseIntersections()){
                index++;
                if(inter.getPlayer()!=null && inter.getPlayer().hasOneResources()){
                    System.out.println(inter.getPlayer().getName()+" : "+index);
                }
            }

            boolean reponseValide2=false;
            while(!reponseValide2){
                System.out.println("Saisissez l'index du joueur a qui vous voulez voler une ressource :");

                int rep =scan.nextInt();
                scan.nextLine();

                if(rep<=index){
                    ArrayList<Integer> randType =new ArrayList<>();

                    for(int resourceType : board.getCases()[board.getIndexRobber()].getCaseIntersections()[rep].getPlayer().getPlayerResources()){
                        if(board.getCases()[board.getIndexRobber()].getCaseIntersections()[rep].getPlayer().hasOneSpecificResources(resourceType)){
                            randType.add(resourceType);
                        }
                    }

                    if(randType.isEmpty()){
                        System.out.println("Le joueur que vous avez choisi ne possède pas de ressources !");

                        break;
                    }

                    int randResource = randType.get(game.getRand().nextInt(randType.size()));

                    board.getCases()[board.getIndexRobber()].getCaseIntersections()[rep].getPlayer().removeResource(randResource, 1);
                    collectResources(randResource, 1);
                    reponseValide2=true;
                }
                else
                    System.out.println("Cet index n'existe pas !");
            }
        }
    }
}
