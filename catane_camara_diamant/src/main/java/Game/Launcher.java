package Game;

import java.awt.EventQueue;

/**
 * <b>Lanceur est la classe qui lance le programme</b>
 * 
 * @author CAMARA Ibrahime, DIAMANT Alexandre
 */
public class Launcher {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                new Home();
            }
        });
    }
}
