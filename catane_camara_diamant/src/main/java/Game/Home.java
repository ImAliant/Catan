package Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Home extends JFrame{

    private static JPanel panelTitle, panelCard, panelButton, panelOption;
    private static RadioButton human1, human2, human3, human4;
    private static RadioButton threePlayers, fourPlayers;

    public Home(){
        homeDisplay();
    }

    public void homeDisplay(){
        //Configurations de la fenetre
        setSize(800, 500);
        setTitle("Accueil");

        try {
            setIconImage(new ImageIcon(getClass().getResource("/Image/colons_catanes_carre.jpg")).getImage());
        } catch (Exception e) {
            System.out.println("Le chemin de l'icone n'existe pas !");
        }
        setContentPane(new PanelFond());
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        //Panel titre
        panelTitle =new JPanel();
        panelTitle.setOpaque(false);
        panelTitle.setBorder(BorderFactory.createEmptyBorder(50, 10, 0, 10)); 

        JLabel title =new JLabel("Colons de Catanes", SwingConstants.CENTER);
        title.setFont(new Font("Berlin Sans FB", Font.PLAIN, 30));

        panelTitle.add(title);

        //Panel button : card1
        JButton play =new Button("Jouer");
        JButton option =new Button("Options");
        JButton exit =new Button("Quitter");

        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panelButton =new JPanel();
        panelButton.setOpaque(false);
        panelButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelButton.setLayout(new GridBagLayout());

        GridBagConstraints c =new GridBagConstraints();

        c.insets = new Insets(10,10,10,10);
        c.gridx=0;
        c.gridy=1;
        panelButton.add(play, c);
        c.gridx=0;
        c.gridy=2;
        panelButton.add(option, c);
        c.gridx=0;
        c.gridy=3;
        panelButton.add(exit, c);

        //Panel option : card2
        panelOption =new JPanel();
        panelOption.setOpaque(false);
        panelOption.setLayout(new BorderLayout());

            //Plusieurs panels pour bien disposer les components a la bonne place
        JPanel numberPlayerChoice =new JPanel();
        numberPlayerChoice.setOpaque(false);

        JLabel labelNbPlayer =new JLabel("Nombre de joueurs :");
        labelNbPlayer.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        ButtonGroup numberPlayersChoice = new ButtonGroup();
        threePlayers =new RadioButton("3 joueurs", true);
        fourPlayers =new RadioButton("4 joueurs");
        
        numberPlayersChoice.add(threePlayers);
        numberPlayersChoice.add(fourPlayers);

        numberPlayerChoice.add(labelNbPlayer);
        numberPlayerChoice.add(threePlayers);
        numberPlayerChoice.add(fourPlayers);

        JPanel humanIA =new JPanel();

        humanIA.setLayout(new GridBagLayout());
        humanIA.setOpaque(false);

        JLabel labelJ1 =new JLabel("JOUEUR 1");
        labelJ1.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        ButtonGroup bgJ1 =new ButtonGroup();
        human1 =new RadioButton("Humain", true);
        RadioButton ia1 =new RadioButton("IA");
        bgJ1.add(human1);
        bgJ1.add(ia1);

        JLabel labelJ2 =new JLabel("JOUEUR 2");
        labelJ2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        ButtonGroup bgJ2 =new ButtonGroup();
        human2 =new RadioButton("Humain", true);
        RadioButton ia2 =new RadioButton("IA");
        bgJ2.add(human2);
        bgJ2.add(ia2);

        JLabel labelJ3 =new JLabel("JOUEUR 3");
        labelJ3.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        ButtonGroup bgJ3 =new ButtonGroup();
        human3 =new RadioButton("Humain", true);
        RadioButton ia3 =new RadioButton("IA");
        bgJ3.add(human3);
        bgJ3.add(ia3);

        JLabel labelJ4 =new JLabel("JOUEUR 4");
        labelJ4.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        ButtonGroup bgJ4 =new ButtonGroup();
        human4 =new RadioButton("Humain", true);
        RadioButton ia4 =new RadioButton("IA");
        bgJ4.add(human4);
        bgJ4.add(ia4);

        labelJ4.setVisible(false);
        human4.setVisible(false);
        ia4.setVisible(false);

        threePlayers.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                labelJ4.setVisible(false);
                human4.setVisible(false);
                ia4.setVisible(false);
            }
        });
        fourPlayers.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                labelJ4.setVisible(true);
                human4.setVisible(true);
                ia4.setVisible(true);
            }
        });

        c.insets =new Insets(10,10,10,10);
        c.gridx=0;
        c.gridy=1;
        humanIA.add(labelJ1, c);
        c.gridx=1;
        c.gridy=1;
        humanIA.add(human1, c);
        c.gridx=2;
        c.gridy=1;
        humanIA.add(ia1, c);
        c.gridx=0;
        c.gridy=2;
        humanIA.add(labelJ2, c);
        c.gridx=1;
        c.gridy=2;
        humanIA.add(human2, c);
        c.gridx=2;
        c.gridy=2;
        humanIA.add(ia2, c);
        c.gridx=0;
        c.gridy=3;
        humanIA.add(labelJ3, c);
        c.gridx=1;
        c.gridy=3;
        humanIA.add(human3, c);
        c.gridx=2;
        c.gridy=3;
        humanIA.add(ia3, c);
        c.gridx=0;
        c.gridy=4;
        humanIA.add(labelJ4, c);
        c.gridx=1;
        c.gridy=4;
        humanIA.add(human4, c);
        c.gridx=2;
        c.gridy=4;
        humanIA.add(ia4, c);

        JButton back =new Button("Retour");
        panelButton.add(back);

        panelOption.add(numberPlayerChoice, BorderLayout.NORTH);
        panelOption.add(humanIA, BorderLayout.CENTER);
        panelOption.add(back, BorderLayout.SOUTH);

        //Panel card
        panelCard =new JPanel();
        panelCard.setOpaque(false);

        CardLayout cl =new CardLayout();
        panelCard.setLayout(cl);

        panelCard.add(panelButton, "1");
        panelCard.add(panelOption, "2");
        cl.show(panelCard, "1");

        play.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(threePlayers.isSelected()){
                    Game game;
                    try {
                        game = new Game(threePlayersArrayInit());
                        dispose();
                        game.play();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                else{
                    Game game;
                    try {
                        game = new Game(fourPlayersArrayInit());
                        dispose();
                        game.play();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        option.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cl.show(panelCard, "2");
            }
        });
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cl.show(panelCard, "1");
            }
        });

        getContentPane().add(panelTitle, BorderLayout.NORTH);
        getContentPane().add(panelCard, BorderLayout.CENTER);
    }

    public class PanelFond extends JPanel{
        Image image;
        
        public PanelFond(){
            image=new ImageIcon(getClass().getResource("/Image/colons_catanes_paysage.jpg")).getImage();
        }
  
        public void paintComponent(Graphics g){
            super.paintComponent(g); 
            g.drawImage(image, 0, 0, 800, 500, null); 
        }
    }

    public class Button extends JButton {
        public Button(String text) {
            super(text);

            this.setBackground(Color.WHITE);
            this.setForeground(Color.BLACK);
            this.setFocusPainted(false);
            this.setRolloverEnabled(false);

            this.setPreferredSize(new Dimension(125,50));
            this.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));

            this.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    setBackground(new Color(161, 161, 161));
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(210, 210, 210));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(Color.WHITE);
                }
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}
            });
        }
    }

    public class RadioButton extends JRadioButton{
        public RadioButton(String text) {
            super(text);

            this.setOpaque(false);
            this.setFocusPainted(false);
            this.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        }
        public RadioButton(String text, boolean selected) {
            super(text, selected);

            this.setOpaque(false);
            this.setFocusPainted(false);
            this.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        }
    }

    public Player[] threePlayersArrayInit(){
        Player p1 =null;
        Player p2 =null;
        Player p3 =null;
        if(human1.isSelected())
            p1 =new Human("Joueur bleu", 0);
        else p1 =new IA("Joueur bleu", 0);
        if(human2.isSelected())
            p2 =new Human("Joueur rouge", 1);
        else p2 =new IA("Joueur rouge", 1);
        if(human3.isSelected())
            p3 =new Human("Joueur orange", 2);
        else p3 =new IA("Joueur orange", 2);
        Player[] players = {p1, p2, p3};

        return players;
    }

    public Player[] fourPlayersArrayInit(){
        Player p1=null;
        Player p2=null;
        Player p3=null;
        Player p4=null;
        if(human1.isSelected())
            p1 =new Human("Joueur bleu", 0);
        else p1 =new IA("Joueur bleu", 0);
        if(human2.isSelected())
            p2 =new Human("Joueur rouge", 1);
        else p2 =new IA("Joueur rouge", 1);
        if(human3.isSelected())
            p3 =new Human("Joueur orange", 2);
        else p3 =new IA("Joueur orange", 2);
        if(human4.isSelected())
            p4 =new Human("Joueur violet", 3);
        else p4 =new IA("Joueur violet", 3);
        Player[] players = {p1, p2, p3, p4};

        return players;
    }


    //Getters et Setters
    public static JPanel getPanelTitle() {return panelTitle;}
    public static void setPanelTitle(JPanel panel) {Home.panelTitle = panel;}
    public static JPanel getPanelButton() {return panelButton;}
    public static void setPanelButton(JPanel panelButton) {Home.panelButton = panelButton;}
    public static JPanel getPanelCard() {return panelCard;}
    public static void setPanelCard(JPanel panelCard) {Home.panelCard = panelCard;}
    public static JPanel getPanelOption() {return panelOption;}
    public static void setPanelOption(JPanel panelOption) {Home.panelOption = panelOption;}
    public static RadioButton getHuman1() {return human1;}
    public static void setHuman1(RadioButton human1) {Home.human1 = human1;}
    public static RadioButton getHuman2() {return human2;}
    public static void setHuman2(RadioButton human2) {Home.human2 = human2;}
    public static RadioButton getHuman3() {return human3;}
    public static void setHuman3(RadioButton human3) {Home.human3 = human3;}
    public static RadioButton getHuman4() {return human4;}
    public static void setHuman4(RadioButton human4) {Home.human4 = human4;}
}
