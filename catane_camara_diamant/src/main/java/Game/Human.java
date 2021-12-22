package Game;

import java.util.Random;
import java.util.Scanner;

public class Human extends Player{

    public Human(String name, int color) {
        super(name + "(Human)", color);
    }
    
    /*public void joue(Board b, Game g){
        Random rand =new Random();
        Scanner scan =new Scanner(System.in);

        //JOUEUR lance les dés pour savoir où il y a des revenus en matiere premiere
        int diceRolls = rand.nextInt(12-2+1)+1;

        System.out.println(this.getName() + " lance les dés !");
        System.out.println("Les dés donnent : "+  diceRolls);

        for(Case c : b.getCases()){
            if(c.getDiceRoll()==diceRolls){
                for(Player p : g.getPlayers()){
                    p.addResourcesInv(c.getResource().getResourceType());
                }
            }
        }

        System.out.println("Que voulez vous faire a présent ? (construire/developpement/port/resource)");

        String reponse1 = scan.nextLine();

        switch (reponse1) {
            case "construire":
                System.out.println("Que voulez vous construire ? (colonie/ville/route)");
                String reponse2 = scan.nextLine();
                
                break;
            case "developpement":

                break;
            case "port":

                break;
            case "resource":
            default:
                break;
        }

    }

    public void reponseQ2(String reponse2, Board b){
        switch (reponse2) {
            case "colonie":
                if(neededResources(reponse2, b)){
                    
                }
                break;
            case "ville":
                if(neededResources(reponse2, b)){
                    
                }
                break;
            case "route":
                if(neededResources(reponse2, b)){
                    
                }
            default:
                break;
        }
    }

    public boolean neededResources(String reponse2, Board b){
        if(reponse2=="colonie") 
            return (getPlayerResources()[Resource.BOIS]>=1 && getPlayerResources()[Resource.ARGILE]>=1 
            && getPlayerResources()[Resource.BLE]>=1 && getPlayerResources()[Resource.MOUTON]>=1); 
        else if(reponse2 =="ville")
            return (getPlayerResources()[Resource.BLE]>=2 && getPlayerResources()[Resource.PIERRE]>=1);
        else if(reponse2 == "route")
            return (getPlayerResources()[Resource.BOIS]>=1 && getPlayerResources()[Resource.ARGILE]>=1);
        return false;
    }*/
}
