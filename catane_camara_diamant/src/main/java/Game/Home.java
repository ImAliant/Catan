package Game;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Home extends JFrame{

    private static JPanel panel, panelButton;
    private static JLabel l1, l2;
    private static JComboBox<String> player;

    public Home(){
        homeDisplay();
    }

    public void homeDisplay(){
        //Configurations de la fenetre
        setSize(300, 175);
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
        JLabel titleLabel =new JLabel("Colons de Catanes");
        Font font =new Font(titleLabel.getFont().getFontName(), Font.BOLD, 18);
        titleLabel.setFont(font);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        l1 =new JLabel("Choissisez le nombre de joueurs : ");
        l1.setAlignmentX(CENTER_ALIGNMENT);

        l2 =new JLabel("[3 joueurs]");
        l2.setForeground(new Color(255, 100, 0));

        //ComboBox et action de la comboBox
        String[] choice ={"3 joueurs", "4 joueurs"};
        player =new JComboBox<String>(choice);
        player.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                l2.setText(" ["+player.getSelectedItem()+"]"); 
            }
        });

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
                    Game game = new Game(new Player[]{new Player("Joueur bleu", 0), 
                                          new Player("Joueur rouge", 1), 
                                          new Player("Joueur orange", 2)});
                    game.start();
                    dispose();
                }
                else{
                    Game game = new Game(new Player[]{new Player("Joueur bleu", 0), 
                                          new Player("Joueur rouge", 1), 
                                          new Player("Joueur orange", 2),
                                          new Player("Joueur violet", 3)});
                    game.start();
                    dispose();
                }
            }
        });
        
        //Panels et ajouts de components dans les panels 
        panel =new JPanel();

        panelButton =new JPanel();
        panelButton.add(exit);
        panelButton.add(launch);

        panel.add(titleLabel);
        panel.add(l1);
        panel.add(player);
        panel.add(l2);
        panel.add(panelButton, BorderLayout.PAGE_END);

        //Ajout du panel principal dans le contentPane
        getContentPane().add(panel);
    }
}
