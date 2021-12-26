package Game;

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

        System.out.println(getName()+" choisit de ne rien faire ce tour.\n");

        if(game.winner()){
            game.setWinner(game.getPlayerTurn());
        }
    }

    @Override
    public void moveRobber(Board board, Game game){
        boolean reponseValide=false;
        while(!reponseValide){
            int rep=0+game.getRand().nextInt(15+0);

            if(board.getIndexRobber()!=rep){
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
        }
    }
}
