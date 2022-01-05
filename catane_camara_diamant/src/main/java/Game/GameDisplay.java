

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class GameDisplay extends JFrame{
 JPanel content;
  //int spacing = 4 ;

    public GameDisplay(Game game){
      this.content = new JPanel();
      createDisplay(game);

    }


    public void createDisplay(Game game){
        setTitle("Colons de Catanes");
        setSize(500, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            setIconImage(new ImageIcon(getClass().getResource("Image/colons_catanes_carre.jpg")).getImage());
        } catch (Exception e) {
            System.out.println("Le chemin de l'icone n'existe pas !");
        }
          
                Joueurs j= new Joueurs(game.getPlayers());
                JPanel panelCarteFond = new JPanel();
                Plateau p = new Plateau(game.getBoard());

                panelCarteFond.setPreferredSize(new Dimension(500,500));
                panelCarteFond.setBackground(Color.CYAN);
                panelCarteFond.add(p);

                this.content.setLayout(new BoxLayout(this.content, BoxLayout.Y_AXIS));
                this.content.add(panelCarteFond,BorderLayout.NORTH);
                this.content.add(j,BorderLayout.SOUTH);

                this.setContentPane(this.content);
                this.pack();
                this.content.setVisible(true);


      

    }

    public class Plateau extends JPanel{
      Board board;
      public Plateau(Board board){
        this.board = board;
        createBoard();
      }

      public void createBoard(){
      this.setPreferredSize(new Dimension(385,385));
      this.setLayout(new GridLayout(4,4));
        for(int i = 0; i<board.getCases().length; i++){
          Tuile a = new Tuile(board.getCases()[i]);
          this.add(a);
        }

        this.setVisible(true);
       // this.setOpaque(true);
      }

      

      
    }

  /*  public class Infrastructures extends JPanel{
      Board board;

      public Infrastructures(Board board){
        this.board = board;
        createInfra();
      }
      
      public void createInfra(){
        this
      }
    


    } */
    



    public class Joueurs extends JPanel{
      Player[] players;

      public Joueurs(Player[] players){
        this.players = players;
        createJoueurs();
      }

      public void createJoueurs(){
        this.setPreferredSize(new Dimension(500,300));
      

            this.setLayout(new GridLayout(1,4));

            JPanel j1 = new JPanel();
            JPanel j2 = new JPanel();
            JPanel j3 = new JPanel();
            JPanel j4 = new JPanel();

            this.add(j1);
            this.add(j2);
            this.add(j3);
            this.add(j4);

            JLabel nom1 = new JLabel();
            JLabel nom2 = new JLabel();
            JLabel nom3 = new JLabel();
            JLabel nom4 = new JLabel();

            j1.setBackground(Color.blue);
            j1.setLayout(new BorderLayout());
            j1.add(nom1);
            nom1.setText(players[0].getName());
            nom1.setAlignmentX(250);
            nom1.setAlignmentY(0);
           
            j2.setBackground(Color.RED);
            j2.setLayout(new BorderLayout());
            j2.add(nom2);
            nom2.setText(players[1].getName());
            nom2.setAlignmentX(250);
            nom2.setAlignmentY(0);

            j3.setBackground(Color.ORANGE);
            j3.setLayout(new BorderLayout());
            j3.add(nom3);
            nom3.setText(players[2].getName());
            nom3.setAlignmentX(250);
            nom3.setAlignmentY(0);

          
            j4.setBackground(new Color(128,0,128)); // pour donner la couleur violet 
            j4.setLayout(new BorderLayout());
            j4.add(nom4);
            nom4.setText(players[3].getName());
            nom4.setAlignmentX(250);
            nom4.setAlignmentY(0);

           
       
        this.setVisible(true);    
    }

  }

  public class Joueur extends JPanel {
    Player player;
    JTextArea infos;
    JLabel nom, playerRessources, roads, settlements, cities, cards, longestRoad, strongestKnight, victoryPoint;
    
  

     public Joueur(Player player){
      this.player = player;
      createJoueur();
    }

    public void createJoueur(){
      this.infos = new JTextArea();
      setPreferredSize(new Dimension(125,300));
      infos.setPreferredSize(new Dimension(100,300));

      switch(player.getColor()){
        case 0:
        setBackground(Color.blue);
        break;
        case 1:
        setBackground(Color.RED);
        break;
        case 2:
        setBackground(Color.orange);
        break;
        case 3:
        setBackground(new Color(128,0,128));
      }

      this.nom = new JLabel();
      this.playerRessources = new JLabel();
      this.roads = new JLabel();
      this.settlements = new JLabel();
      cities = new JLabel();
      cards = new JLabel();
      longestRoad = new JLabel();
      strongestKnight = new JLabel();

      nom.setBounds(63, 0, 95, 45);
      roads.setBounds(7,35,65,30);
      settlements.setBounds(7,70,65,30);
      cities.setBounds(7,105,65,30);
      cards.setBounds(7, 140, 65, 30);

    }
  }

    


      public class Tuile extends JPanel {
        private final Color[] COLOR_ARRAY = {Color.decode("#FFFFE0"), Color.decode("#014421"),  Color.decode("	#8C92AC"),Color.decode("#FFDF00"), Color.decode("#dbe9f4 "), Color.decode("#BB6528")   };  
        // [0] : jaune clair (desert) , [1] = vert (foret) , [2] = gris (Montagne), [3] = Jaune (champs), [4] = blanc (Pre) , [5] = Marron (Colline)
         Case c ;
        public Tuile(Case c){
          this.c = c;
         createTuile();
      } 

      public void createTuile(){
        this.setSize(100,100);
        this.setVisible(true);    
        JLabel diceroll = new JLabel();
        if(this.c.isRobber() == false){
        diceroll.setText(String.valueOf(this.c.getDiceRoll()) );
        }else{
          diceroll.setText(String.valueOf(this.c.getDiceRoll()) +"(Voleur)");
        }
        this.add(diceroll);
        diceroll.setAlignmentX(CENTER_ALIGNMENT);
        diceroll.setAlignmentY(CENTER_ALIGNMENT);

        switch(this.c.getLand()){
           case -1 : 
           setBackground(COLOR_ARRAY[0]);
           break;

           case 0 : 
           setBackground(COLOR_ARRAY[1]);
           break;

           case 1 : 
           setBackground(COLOR_ARRAY[2]);
           break;

           case 2 : 
           setBackground(COLOR_ARRAY[3]);
           break;
           case 3 : 
           setBackground(COLOR_ARRAY[4]);
           break;
           case 4 : 
           setBackground(COLOR_ARRAY[5]);
           break;
        } 
      }
      
     }

     public class Ports extends JPanel {
       Port port;
       JLabel type;
      

      public Ports(Port port){
        this.port = port;
        this.type = new JLabel();
        createPort();
      }

      public void createPort(){
        
      
        this.setBackground(Color.BLUE.darker());
        if(this.port.getPortType() == 0){
              type.setText("3:1");
            }else{
              switch(this.port.getResource().getResourceType()){
                case 0 : 
                type.setText("BOIS");
                break;
                case 1 : 
                type.setText("PIERRE");
                break;
                case 2 : 
                type.setText("BLE");
                break;
                case 3 : 
                type.setText("MOUTON");
                break;
                case 4 : 
                type.setText("ARGILE");
                break;
                }
            }
            this.add(type);
            this.setVisible(true);
      } 
    }

      public class Buildings extends JPanel {
        private Building building;
        JLabel type;
        Player player;

        public Buildings(Building building, Player p){
          this.building = building;
           type = new JLabel();
           player = p;
           createBuilding();
        }

        public void createBuilding(){ 
          if(this.player == null || this.building.getBuildingType() == 0){
            this.setBackground(Color.black);
      }else{
        switch(this.player.getColor()){
          case 0:
          this.setBackground(Color.blue);
          break;
          case 1:
          this.setBackground(Color.red);
          break;
          case 2:
          this.setBackground(Color.orange);
          break;
          case 3:
          this.setBackground(new Color(128,0,128));
          break;
          default:
          this.setBackground(Color.black);
          break;
        }
       if(this.building.getBuildingType() == 1){

        type.setText("C");   
      }else{
        type.setText("V");
      }
        
     }
     this.add(type);
     this.setVisible(true);
   }

  }

     

     public class Arret extends JPanel {
     Intersection intersection;
     Buildings b; 
     Ports p;

      public Arret(Intersection intersection){
        this.intersection = intersection;
        this.b = new Buildings(this.intersection.getBuilding(), null);
        if(this.intersection.getPort()==null){
          this.p = null;
        }else{
         this.p = new Ports(this.intersection.getPort());
        }
        createArret();
      }

      public void createArret(){
        setSize(35,35);
        if(this.p == null){
          this.add(this.b);
        }else{  
          if(this.intersection.getId() == 1 || this.intersection.getId() == 2 || this.intersection.getId() == 3 || this.intersection.getId() == 4){
          this.setLayout(new GridLayout(2,1));
          this.add(this.p);
          this.add(this.b);
          }else if( this.intersection.getId() == 0 || this.intersection.getId() == 5 || this.intersection.getId() == 10 || this.intersection.getId() == 15){
            this.setLayout(new GridLayout(1,2));
            this.add(this.p);
            this.add(this.b);
          }else if( this.intersection.getId() == 20 || this.intersection.getId() == 21 || this.intersection.getId() == 22 || this.intersection.getId() == 23){
            this.setLayout(new GridLayout(2,1));
            this.add(this.b);
            this.add(this.p);
        }else{
          this.setLayout(new GridLayout(1,2));
          this.add(this.b);
          this.add(this.p);
        }
      }

      this.setVisible(true);
     } 
      
    }

    public class Route extends JPanel {
      Road route;
      Player player;
      public Route(Road route, Player p){
        this.route = route;
        this.player = p;
        createRoute();
      }

      public void createRoute(){
        if(this.route.getId2() == this.route.getId1() +1 || this.route.getId2() == this.route.getId1()-1){
          this.setPreferredSize(new Dimension(70,20));
        }
        if(this.route.getId2() == this.route.getId1() +5 || this.route.getId2() == this.route.getId1() -5  ){
          this.setPreferredSize(new Dimension(20,70));
        }

        if(this.player == null){
          this.setBackground(Color.black);
    }else{
      switch(this.player.getColor()){
        case 0:
        this.setBackground(Color.blue);
        break;
        case 1:
        this.setBackground(Color.red);
        break;
        case 2:
        this.setBackground(Color.orange);
        break;
        case 3:
        this.setBackground(new Color(128,0,128));
        break;
        default:
        this.setBackground(Color.black);
        break;    
      }
    }
    this.setVisible(true);
  }
}

        /*
        /public class Carte extends JPanel{ // pour creer une grille par rapport a Board
       /************************* *
         private Board board;

          private Object game;

          private static final long serialVersionUID =1L;

          static final int cols = 4; // nb de colonne
          static final int rows = 4; // nb de ligne

          static final int originX = 0; // position d'origine x (en haut a droite)
          static final int originY = 0;// position d'origine y (en haut a droite)
          static final int cellSide = 72; // taille d'un coté (vu que c'est un carré on a besoin que de ça)

         /* public Carte(Game game){
            this.board = this.game.getBoard();
          } 

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
          }  ********************/

          // ESSAYEZ DE LE RENDRE INTERACTIF

           /*********************************
             public static final int LENGTH = 4;
             private final Color[] COLOR_ARRAY = {Color.decode("#FFFFE0"), Color.decode("#014421"),  Color.decode("	#8C92AC"),Color.decode("#FFDF00"), Color.decode("#dbe9f4 "), Color.decode("#BB6528")   };  
             // [0] : jaune clair (desert) , [1] = vert (foret) , [2] = gris (Montagne), [3] = Jaune (champs), [4] = blanc (Pre) , [5] = Marron (Colline)

            public Carte(Game game){
              setLayout(new GridLayout(LENGTH, LENGTH));
              this.setBackground(Color.black);
              int a = -4;
              for (int i = 0; i < LENGTH; i++)
              {
                a+=4;
                  for (int j = 0; j < LENGTH; j++)
                  {
                    int pos = a+j;
                    if(game.getBoard().getCases()[pos].getLand()== Board.DESERT){
                      add(new TileView(COLOR_ARRAY[0]));
                    } else if(game.getBoard().getCases()[pos].getLand()== Board.FORET){
                      add(new TileView(COLOR_ARRAY[1]));
                    } else if(game.getBoard().getCases()[pos].getLand()== Board.MONTAGNE){
                      add(new TileView(COLOR_ARRAY[2]));
                    }  else if(game.getBoard().getCases()[pos].getLand()== Board.CHAMPS){
                      add(new TileView(COLOR_ARRAY[3]));
                    }  else if(game.getBoard().getCases()[pos].getLand()== Board.PRE){
                      add(new TileView(COLOR_ARRAY[4]));
                    }else{
                      add(new TileView(COLOR_ARRAY[5]));
                    }
                      
              }
            }


        }
        
      
     

      }
      class TileView extends JLabel {
        public static final int SIZE = 100;
        TileView(Color color) {
            setPreferredSize(new Dimension(SIZE,SIZE));
            setOpaque(true);
            setBackground(color);
        }
  }

  class PiecesPanel extends JPanel {

    public static final int LENGTH = 6;
   public PiecesPanel(){

        setOpaque(false); //make it transparent
        setLayout(new GridLayout(LENGTH, LENGTH));
        JComponent player = new Player();
        add(player);
    }
}

class Piece extends JLabel{

 public Piece() {
       // pont rouge = https://2.bp.blogspot.com/-7LV269UX7Gs/T5_MCvDGaJI/AAAAAAAAANg/nRIj9KOw-qE/s1600/old+bridge.png
      URL url = null;
      try {
        
          url = new URL("https://dl1.cbsistatic.com/i/r/2017/08/15/9b37ca73-de21-4998-ae7a-07d2915a551e/thumbnail/64x64/0cd91f1c045919af6bdafab3a6f07f99/imgingest-6339051052035379444.png");
      } catch (MalformedURLException ex) { ex.printStackTrace();  }
      setIcon(new ImageIcon(url));
      setVerticalTextPosition(SwingConstants.BOTTOM);
      setHorizontalTextPosition(SwingConstants.CENTER);
  } ***********************/

  /*********************************
  public static final int SIZE =4;
  
  public void paintComponent(Graphics g){
    
      g.setColor(Color.DARK_GRAY);
      g.fillRect(0, 0,600 , 600);
      g.setColor(Color.black);
      for(int i = 0; i< SIZE;i++){
        for(int j = 0; j<SIZE;j++){
          g.fillRect(spacing+i*80, spacing +j*80+80, 80-2*spacing, 80-2*spacing);
        }
      }
  }   ***********************/

}

 // }  
  //}

  


  
    

