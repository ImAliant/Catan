package Game;

public class DevCard {
    public static final int VICTORY_POINT=0;
    public static final int PROGRESS_BUILD=1;
    public static final int PROGRESS_DISCOVERY=2;
    public static final int PROGRESS_MONOPOLY=3;
    public static final int KNIGHT=4;

    private int card;
    
    public DevCard(int card) throws Exception{
        switch (card) {
            case VICTORY_POINT: case PROGRESS_BUILD: case PROGRESS_DISCOVERY: case PROGRESS_MONOPOLY: case KNIGHT:
                this.card=card;
                break;
            default:
                throw new Exception("Cette carte n'existe pas !");
        }
    }

    public String toString(){
        String s="";
        switch (card) {
            case VICTORY_POINT:
                s="Carte Victoire";
                break;
            case PROGRESS_BUILD:
                s="Carte Progrès : Construction";
                break;
            case PROGRESS_DISCOVERY:
                s="Carte Progrès : Découverte";
                break;
            case PROGRESS_MONOPOLY:
                s="Carte Progrès : Monopole";
                break;
            case KNIGHT:
                s="Carte Chevalier";
                break;
        }
        return s;
    }

    //Getters et Setters
    public int getCard() {return card;}
    public void setCard(int card) {this.card = card;}
}
