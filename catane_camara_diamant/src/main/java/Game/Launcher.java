package Game;

import java.awt.EventQueue;

public class Launcher {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                new Home();
                /*Player[] players = {
                    new Human("Alexandre", 0),
                    new Human("Ibrahime", 1),
                    new Human("Bob", 2),
                    new Human("Francis", 3)
                };
                try {
                    Game game = new Game(players);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        });
    }
}
