

import java.awt.EventQueue;

public class Launcher {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
               Player[] players = {new Player("a",1),new Player("b",2),new Player("c",3), new Player("d",4)};
                GameDisplay g = new GameDisplay(new Game(players));
             // new Home();
                g.setVisible(true);
            }
        });
    }

}
