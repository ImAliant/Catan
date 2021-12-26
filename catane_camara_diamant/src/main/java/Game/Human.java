package Game;

public class Human extends Player{

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
            game.moveRobber();
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
                        System.out.println("Vous choisissez de ne rien faire ce tour.\n");
                        reponseValide=true;
                        turnCompleted=true;
                        break;
                    default:
                        System.out.println("Ce choix n'existe pas!\n");
                        break;
                }
            }

            if(game.winner()){
                game.setWinner(game.getPlayerTurn());
            }
        }
    }
}
