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

        ArrayList<String> possibleChoice =new ArrayList<String>();

        boolean choice0 = resourceForCity() || resourceForRoad() || resourceForSettlement();
        boolean choice1 = hasPort() || hasFourResources();
        boolean choice2 = resourceForDevCard();
        boolean choice3 = hasDevCard();

        if(choice0){
            possibleChoice.add("0");
        }
        if(choice1){
            if(hasType2Port()){
                possibleChoice.add("10");
            }
            if(hasType3Port() && hasThreeResources()){
                possibleChoice.add("11");
            }
            if(hasFourResources()){
                possibleChoice.add("12");
            }
        }
        if(choice2){
            possibleChoice.add("2");
        }
        if(choice3){
            possibleChoice.add("3");
        }
        if(choice0 || choice1 || choice2 || choice3){
            possibleChoice.add("100");
        }

        if(possibleChoice.isEmpty())
            System.out.println(getName()+" choisit de ne rien faire ce tour.\n");
        else{
            int tour=0;
            while(tour!=1){
                boolean reponseValide=false;
                while(!reponseValide){
                    String choice = possibleChoice.get(game.getRand().nextInt(possibleChoice.size()));
                    switch (choice) {
                        case "0":
                            if(game.buildAnswerIA(this)){
                                reponseValide=true;
                                tour++;
                            }
                            else
                                possibleChoice.remove("0");
                            break;
                        case "10":
                            if(game.trade2ResourcesIA(this)){
                                reponseValide=true;
                                tour++;
                            }
                            else
                                possibleChoice.remove("10");
                            break;
                        case "11":
                            if(game.trade3ResourceIA(this)){
                                reponseValide=true;
                                tour++;
                            }
                            else
                                possibleChoice.remove("11");
                            break;
                        case "12":
                            if(game.trade4ResourcesIA(this)){
                                reponseValide=true;
                                tour++;
                            }
                            else
                                possibleChoice.remove("12");
                            break;
                        case "2":
                            if(game.buyAnswerIA(this)){
                                reponseValide=true;
                                tour++;
                            }
                            else
                                possibleChoice.remove("2");
                            break;
                        case "3":
                            if(game.playCardAnswerIA(this)){ //MODIFIÉ LA MÉTHODE DANS GAME
                                reponseValide=true;
                                tour++;
                            }
                            else
                                possibleChoice.remove("3");
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
        if(board.getSpecificRoad(id1, id2)!=null){
            if(board.getSpecificRoad(id1, id2).upgradeRoad(this)){
                if(turn!=0)
                    removeResourceForRoad();
                getRoads().add(board.getSpecificRoad(id1, id2));
                System.out.println("Route construite en ("+id1+" "+id2+").\n");
            }
            else{
                ArrayList<Road> canBuilRoad = emptyRoadPlayerCanBuild(board, game);

                if(!canBuilRoad.isEmpty()){
                    Road roadChoice = canBuilRoad.get(game.getRand().nextInt(canBuilRoad.size()));
                        
                    int rep1=roadChoice.getId1();
                    int rep2=roadChoice.getId2();

                    if(game.idIsRoad(id1, id2, this) || game.idForRoadIsSettlementsOrCity(rep1, rep2, this))
                        buildRoad(rep1, rep2, board, turn, game);
                }
                else return;
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

    public ArrayList<Road> emptyRoadPlayerCanBuild(Board board, Game game){
        ArrayList<Road> ERPCB =new ArrayList<Road>();
        if(!board.getEmptyRoad().isEmpty()){
            for(Intersection inter : getSettlements()){
                for(Road road : board.getEmptyRoad()){
                    if((road.getId1()==inter.getId() || road.getId2()==inter.getId()) && road.getPlayer()==null)
                        ERPCB.add(road);
                }
            }
            for(Intersection inter : getCities()){
                for(Road road : board.getEmptyRoad()){
                    if((road.getId1()==inter.getId() || road.getId2()==inter.getId()) && road.getPlayer()==null)
                        ERPCB.add(road);
                }
            }
            for(Road road1 : getRoads()){
                for(Road road2 : board.getEmptyRoad()){
                    if((road1.getId1()==road2.getId1() || road1.getId2()==road2.getId2() || 
                        road1.getId1()==road2.getId2() || road1.getId2()==road2.getId1()) && road2.getPlayer()==null)
                        ERPCB.add(road2);
                }
            }
        }
        return ERPCB;
    }
}
