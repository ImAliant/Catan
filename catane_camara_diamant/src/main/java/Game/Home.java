

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Home extends JFrame{

    private static JPanel panel, panelButton, panelChoixCB;
    private static JLabel l1, l2;
    private static JCheckBox choixJ1, choixJ2, choixJ3, choixJ4;
    private static JComboBox<String> player;

    public Home(){
        homeDisplay();
    }

    public void homeDisplay(){
        //Configurations de la fenetre
        setSize(350, 225);
        setTitle("Accueil");

        try {
            setIconImage(new ImageIcon(getClass().getResource("Image/colons_catanes_carre.jpg")).getImage());
        } catch (Exception e) {
            System.out.println("Le chemin de l'icone n'existe pas !");
        }
        
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        //Etiquettes
        JLabel titleLabel =new JLabel("Colons de Catanes", JLabel.CENTER);
        Font font =new Font(titleLabel.getFont().getFontName(), Font.BOLD, 18);
        titleLabel.setFont(font);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        l1 =new JLabel("Choissisez le nombre de joueurs : ", JLabel.CENTER);
        l1.setAlignmentX(CENTER_ALIGNMENT);

        //ComboBox et action de la comboBox
        String[] choice ={"3 joueurs", "4 joueurs"};
        player =new JComboBox<String>(choice);

        //Choix humain ou ordinateur
          //Si coché -> humain, si pas coché -> IA
        choixJ1 =new JCheckBox("Joueur 1", true);
        choixJ2 =new JCheckBox("Joueur 2", true);
        choixJ3 =new JCheckBox("Joueur 3", true);
        choixJ4 =new JCheckBox("Joueur 4", true);

        //Boutons et action des boutons
        JButton exit =new JButton("QUITTER");
        JButton launch =new JButton("LANCER LE JEU"); 

        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        launch.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(player.getSelectedIndex()==0){
                    Game game = new Game(threePlayersArrayInit());
                    dispose();
                    game.gameCatane();
                }
                else{
                    Game game = new Game(fourPlayersArrayInit());
                    dispose();
                    game.gameCatane();
                }
            }
        });
        
        //Panels et ajouts de components dans les panels 
        panel =new JPanel();
        panel.add(titleLabel);
        panel.add(l1);
        panel.add(player);
        
        panelChoixCB =new JPanel();
        panelChoixCB.setLayout(new BoxLayout(panelChoixCB, BoxLayout.PAGE_AXIS));
        panelChoixCB.add(choixJ1);
        panelChoixCB.add(choixJ2);
        panelChoixCB.add(choixJ3);
        panelChoixCB.add(choixJ4);

        panelButton =new JPanel();
        panelButton.add(exit);
        panelButton.add(launch);

        panel.add(panelChoixCB);
        panel.add(panelButton);

        //Ajout du panel principal dans le contentPane
        getContentPane().add(panel);
    }

    public Player[] threePlayersArrayInit(){
        Player p1 =null;
        Player p2 =null;
        Player p3 =null;
        if(choixJ1.isSelected())
            p1 =new Human("Joueur bleu", 0);
        else p1 =new IA("Joueur bleu", 0);
        if(choixJ2.isSelected())
            p2 =new Human("Joueur rouge", 1);
        else p2 =new IA("Joueur rouge", 1);
        if(choixJ3.isSelected())
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
        if(choixJ1.isSelected())
            p1 =new Human("Joueur bleu", 0);
        else p1 =new IA("Joueur bleu", 0);
        if(choixJ2.isSelected())
            p2 =new Human("Joueur rouge", 1);
        else p2 =new IA("Joueur rouge", 1);
        if(choixJ3.isSelected())
            p3 =new Human("Joueur orange", 2);
        else p3 =new IA("Joueur orange", 2);
        if(choixJ4.isSelected())
            p4 =new Human("Joueur violet", 3);
        else p4 =new IA("Joueur violet", 3);
        Player[] players = {p1, p2, p3, p4};

        return players;
    }


    //Getters et Setters
    public static JPanel getPanel() {return panel;}
    public static void setPanel(JPanel panel) {Home.panel = panel;}
    public static JPanel getPanelButton() {return panelButton;}
    public static void setPanelButton(JPanel panelButton) {Home.panelButton = panelButton;}
    public static JLabel getL1() {return l1;}
    public static void setL1(JLabel l1) {Home.l1 = l1;}
    public static JLabel getL2() {return l2;}
    public static void setL2(JLabel l2) {Home.l2 = l2;}
    public static JComboBox<String> getPlayer() {return player;}
    public static void setPlayer(JComboBox<String> player) {Home.player = player;}
}
