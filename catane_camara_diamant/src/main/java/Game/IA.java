package Game;

import java.util.ArrayList;

public class IA extends Player {

    public IA(String name, int color) {
        super(name + " (IA)", color);
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
            }
        }

        game.resourceAnswer(this);

        ArrayList<String> possibleChoice =new ArrayList<String>();

        boolean choice0 = resourceForCity() || resourceForRoad() || resourceForSettlement();
        boolean choice1 = hasPort() || hasFourResources();
        boolean choice2 = resourceForDevCard();
        boolean choice3 = hasDevCard();

        if(choice0){
            possibleChoice.add("0");
            System.out.println("ajout 0");
        }
        if(choice1){
            if(hasType2Port()){
                possibleChoice.add("10");
                System.out.println("ajout 10");
            }
            if(hasType3Port() && hasThreeResources()){
                possibleChoice.add("11");
                System.out.println("ajout 11");
            }
            if(hasFourResources()){
                possibleChoice.add("12");
                System.out.println("ajout 12");
            }
        }
        if(choice2){
            possibleChoice.add("2");
            System.out.println("ajout 2");
        }
        if(choice3){
            possibleChoice.add("3");
            System.out.println("ajout 3");
        }
        if(choice0 || choice1 || choice2 || choice3){
            possibleChoice.add("100");
            System.out.println("ajout 100");
        }

        if(possibleChoice.isEmpty())
            System.out.println(getName()+" choisit de ne rien faire ce tour.\n");
        else{
            int tour=0;
            while(tour!=1){
                System.out.println("etape while(tour)");
                boolean reponseValide=false;
                while(!reponseValide){
                    System.out.println("etape while(reponseValide)");
                    String choice = possibleChoice.get(game.getRand().nextInt(possibleChoice.size()));
                    switch (choice) {
                        case "0":
                            if(game.buildAnswerIA(this)){
                                reponseValide=true;
                                tour++;
                            }
                            else{
                                possibleChoice.remove("0");
                                System.out.println("suppr 0");
                            }
                            break;
                        case "10":
                            if(game.trade2ResourcesIA(this)){
                                reponseValide=true;
                                tour++;
                            }
                            else{
                                possibleChoice.remove("10");
                                System.out.println("suppr 10");
                            }
                            break;
                        case "11":
                            if(game.trade3ResourceIA(this)){
                                reponseValide=true;
                                tour++;
                            }
                            else{
                                possibleChoice.remove("11");
                                System.out.println("suppr 11");
                            }
                            break;
                        case "12":
                            if(game.trade4ResourcesIA(this)){
                                reponseValide=true;
                                tour++;
                            }
                            else{
                                possibleChoice.remove("12");
                                System.out.println("suppr 12");
                            }
                            break;
                        case "2":
                            if(game.buyAnswerIA(this)){ //MODIFIÉ LA MÉTHODE DANS GAME
                                reponseValide=true;
                                tour++;
                            }
                            else{
                                possibleChoice.remove("2");
                                System.out.println("suppr 2");
                            }
                            break;
                        case "3":
                            if(game.playCardAnswerIA(this)){ //MODIFIÉ LA MÉTHODE DANS GAME
                                reponseValide=true;
                                tour++;
                            }
                            else{
                                possibleChoice.remove("3");
                                System.out.println("suppr 3");
                            }
                            break;
                        case "100":
                            System.out.println(getName()+" choisit de ne rien faire ce tour.\n");
                            reponseValide=true;
                            tour++;
                            break;
                    }
                }
            }
        }

        game.strongestKnight();
        game.longestRoad();
        if(game.winner()){
            game.setWinner(game.getPlayerTurn());
        }
    }

    @Override
    public void buildSettlement(int id, Board board, int turn){
        System.out.println("Test buildSettlement");
        if(board.getIntersections()[id].getBuilding().upgradeToSettlements()){
            getSettlements().add(board.getIntersections()[id]);
            board.getIntersections()[id].setPlayer(this);
            if(turn!=0){
                removeResourceForSettlements();
            }
            System.out.println("Colonie construite en ("+id+").");
            addVictoryPoint(1);
        }
    }

    @Override
    public void buildCity(int id, Board board){
        System.out.println("Test buildCity");
        if(board.getIntersections()[id].getBuilding().upgradeToCity()){
            getCities().add(board.getIntersections()[id]);
            getSettlements().remove(board.getIntersections()[id]);
            removeResourceForCities();
            addVictoryPoint(2);
            System.out.println("Ville construite en ("+id+").");
        }
    }

    @Override
    public void buildRoad(int id1, int id2, Board board, int turn, Game game){
        System.out.println("Test buildRoad");
        if(board.getSpecificRoad(id1, id2)!=null){
            if(board.getSpecificRoad(id1, id2).upgradeRoad(this)){
                if(turn!=0)
                    removeResourceForRoad();
                getRoads().add(board.getSpecificRoad(id1, id2));
                System.out.println("Route construite en ("+id1+" "+id2+").\n");
            }
            else{
                int rep1=getSettlements().get(game.getRand().nextInt(getSettlements().size())).getId();

                ArrayList<Integer> canBuilRoad = game.canBuildRoadIA(rep1);
                        
                int rep2=canBuilRoad.get(game.getRand().nextInt(canBuilRoad.size()));

                if(game.idForRoadHasSettlementsOrCity(rep1, rep2, this))
                    buildRoad(rep1, rep2, board, turn, game);
            }
        }
    }

    @Override
    public void moveRobber(Board board, Game game){
        ArrayList<Case> isNotRobber = board.caseWithoutRobber(); 
        Case rep=isNotRobber.get(game.getRand().nextInt(isNotRobber.size()));

            
        for(Case c : board.getCases()){
            if(c.isRobber()){
                c.setRobber(false);
                break;
            }
        }
        rep.setRobber(true);
        board.setIndexRobber(rep.getId());
        System.out.println("Le voleur à été déplacé sur la case "+board.getIndexRobber());
    }
}
