package Game;

import javax.swing.*;

public class GameDisplay extends JFrame{

    public GameDisplay(){
        createDisplay();
    }

    public void createDisplay(){
        setTitle("Colons de Catanes");
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            setIconImage(new ImageIcon(getClass().getResource("Image/colons_catanes_carre.jpg")).getImage());
        } catch (Exception e) {
            System.out.println("Le chemin de l'icone n'existe pas !");
        }

    }
}
