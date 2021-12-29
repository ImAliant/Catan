package Game;

import java.awt.EventQueue;
import java.util.ArrayList;

public class Launcher {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                System.out.println("\033[H\033[2J");
                new Home();
            }
        });
    }
}
