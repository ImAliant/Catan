package Game;

public class DevCard {
    public static final int VICTORY_POINT=0;
    public static final int PROGRESS=1;
    public static final int KNIGHT=2;
    public static final int LONGEST_ROAD=3;
    public static final int STRONGEST_KNIGHT=4;

    private int card;
    
    public DevCard(int card) throws Exception{
        switch (card) {
            case VICTORY_POINT: case PROGRESS: case KNIGHT:
            case LONGEST_ROAD: case STRONGEST_KNIGHT:
                this.card=card;
                break;
            default:
                throw new Exception("Cette carte n'existe pas !");
        }
    }

    //Getters et Setters
    public int getCard() {return card;}
    public void setCard(int card) {this.card = card;}
}
