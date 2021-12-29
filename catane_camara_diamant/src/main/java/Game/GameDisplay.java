

import java.awt.*;
import javax.swing.*;


public class GameDisplay extends JFrame{
 // private JPanel content;

    public GameDisplay(Game game){
        createDisplay(game);
    }


    public void createDisplay(Game game){
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

               // this.content = new JPanel();
                JPanel panelJoueurs = new JPanel();
                panelJoueurs.setPreferredSize(new Dimension(600,200));
                JPanel panelCarteFond = new JPanel();
                panelCarteFond.setPreferredSize(new Dimension(400,600));
                panelCarteFond.setBackground(Color.CYAN);
                this.setLayout(new GridLayout(2,1));
                this.add(panelCarteFond,BorderLayout.NORTH);
                this.add(panelJoueurs,BorderLayout.SOUTH);
               // this.getContentPane().


                  Player[] players  = game.getPlayers() ;

              if(players.length == 3){
                panelJoueurs.setLayout(new GridLayout(1,3));

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


                }else{
                  panelJoueurs.setLayout(new GridLayout(1,4));

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

                  j1.setBackground(Color.blue);
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


                  j4.setBackground(new Color(128,0,128)); // pour donner la couleur violet 
                  j4.setLayout(new BorderLayout());
                  j4.add(nom4,BorderLayout.NORTH);
                  nom4.setText(players[3].getName());
              //    nom4.setHorizontalAligment(80);
                }
            Graphics g = this.getGraphics();
        Carte panelCarte = new Carte(g);
          panelCarte.setPreferredSize(new Dimension(350,350));
        panelCarteFond.add(panelCarte);  
    }


        public class Carte extends JPanel{ // pour creer une grille par rapport a Board
       //   private Board board;

       //   private Object game;

          private static final long serialVersionUID =1L;

          static final int cols = 4; // nb de colonne
          static final int rows = 7; // nb de ligne

          static final int originX = 1; // position d'origine x (en haut a droite)
          static final int originY = 1;// position d'origine y (en haut a droite)
          static final int cellSide = 72; // taille d'un coté (vu que c'est un carré on a besoin que de ça)

         /* public Carte(Game game){
            this.board = this.game.getBoard();
          } */

          public Carte(Graphics g){
            paintComponent(g);
               this.setBackground(Color.black);
          }

          @Override
          protected void paintComponent(Graphics g){
            super.paintComponent(g);
      //  g.drawLine(x1,y1,x2,y2) x1 =  point x de depart , y1 = point y de depart , x2 point x d'arrive, y2 point y d'arrive
            for(int i = 0; i <rows + 1; i++){
                g.drawLine(originX, originY + i * cellSide, originX + cols * cellSide,originY+ i * cellSide);
                //y1,y2 = taille d'un cote * le nombre qu'il fera cette taille par rapport a originY (pour que les ligne se fasse en plusieurs exemplaires avec un ecart proportionel)
               //x2 = taille de la ligne, la ou aura lieu l'intersection avec les colonnes

            }

            for(int i = 0; i< cols +1; i++){
                g.drawLine(originX + i + cellSide, originY, originX + i *cellSide, originY + rows *cellSide);
                //x1,x2 = meme delire qu'avec rows
                // y2 = meme delire q'avec row
            }
          }

          // ESSAYEZ DE LE RENDRE INTERACTIF


        }

}
