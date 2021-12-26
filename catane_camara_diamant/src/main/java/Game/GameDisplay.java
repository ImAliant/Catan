package Game;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.applet.Applet;

public class GameDisplay extends JFrame{
  private Home home;
  private JPanel content;
  private Board board;

    public GameDisplay(){
        createDisplay();
    }


    public class Carte extends JPanel{ // pour creer une grille par rapport a Board


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

                this.home = new Home();
                this.board = new Board();
                this.content = new JPanel();
                JPanel panelJoueurs = new JPanel();
                panelJoueurs.setPreferredSize(new Dimension(600,200));
                JPanel panelCarteFond = new JPanel();
                panelCarteFond.setPreferredSize(new Dimension(600,400));
                panelCarteFond.setBackground(Color.CYAN);
                this.content.setLayout(new GridLayout(2,1));
                this.content.add(panelCarteFond,BorderLayout.NORTH);
                this.content.add(panelJoueurs,BorderLayout.SOUTH);


                if(this.home.getPlayer().getSelectedIndex() == 0){
                  panelJoueurs.setLayout(new GridLayout(1,3));

                  Player[] players  = this.home.threePlayersArrayInit() ;

                  JPanel j1 = new JPanel();
                  JPanel j2 = new JPanel();
                  JPanel j3 = new JPanel();

                  panelJoueurs.add(j1);
                  panelJoueurs.add(j2);
                  panelJoueurs.add(j3);

                  JLabel nom1 = new JLabel();
                  JLabel nom2 = new JLabel();
                  JLabel nom3 = new JLabel();

                  j1.setBackground(Color.BLUE);
                  j1.add(nom1);
                  nom1.setText(players[0].getName());
                  j1.setLayout(new BorderLayout());
                  j1.add(nom1,BorderLayout.NORTH);
            //      nom1.setHorizontalAligment(80);

                  j2.setBackground(Color.RED);
                  j2.add(nom2);
                  nom2.setText(players[1].getName());
                  j2.setLayout(new BorderLayout());
                  j2.add(nom2,BorderLayout.NORTH);
              //    nom2.setHorizontalAligment(80);

                  j3.setBackground(Color.ORANGE);
                  j3.add(nom3);
                  nom3.setText(players[2].getName());
                  j3.setLayout(new BorderLayout());
                  j3.add(nom3,BorderLayout.NORTH);
              //    nom3.setHorizontalAligment(80);


                }else{
                  panelJoueurs.setLayout(new GridLayout(1,4));

                  Player[] players  = this.home.fourPlayersArrayInit() ;

                  JPanel j1 = new JPanel();
                  JPanel j2 = new JPanel();
                  JPanel j3 = new JPanel();
                  JPanel j4 = new JPanel();

                  panelJoueurs.add(j1);
                  panelJoueurs.add(j2);
                  panelJoueurs.add(j3);
                  panelJoueurs.add(j4);

                  JLabel nom1 = new JLabel();
                  JLabel nom2 = new JLabel();
                  JLabel nom3 = new JLabel();
                  JLabel nom4 = new JLabel();

                  j1.setBackground(Color.BLUE);
                  j1.setLayout(new BorderLayout());
                  j1.add(nom1,BorderLayout.NORTH);
                  nom1.setText(players[0].getName());
            //      nom1.setHorizontalAligment(80);

                  j2.setBackground(Color.RED);
                  j2.setLayout(new BorderLayout());
                  j2.add(nom2,BorderLayout.NORTH);
                  nom2.setText(players[1].getName());
              //    nom2.setHorizontalAligment(80);
                        
                  j3.setBackground(Color.ORANGE);
                  j3.setLayout(new BorderLayout());
                  j3.add(nom3,BorderLayout.NORTH);
                  nom3.setText(players[2].getName());
              //    nom3.setHorizontalAligment(80);


                  j1.setBackground(new Color(128,0,128));
                  j4.setLayout(new BorderLayout());
                  j4.add(nom4,BorderLayout.NORTH);
                  nom4.setText(players[3].getName());
              //    nom4.setHorizontalAligment(80);
                }

    /*    Carte panelCarte = new Carte();
          panelCarte.setPreferredSize(new Dimension(350,350));
        panelCarteFond.add(panelCarte);  */
    }
}
