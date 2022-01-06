package Ui;

import java.awt.*;
import javax.swing.*;

import Game.Player;

public class PanelPlayer extends JPanel{
    private Player player;
    private JLabel nameLabel, resourceLabel, victoryPointLabel, victoryLabel;

    public PanelPlayer(int size, Player player) throws Exception{
        this.player=player;

        setLayout(new GridBagLayout());
        if(size==3){
            setSize(170, 80);
            setPreferredSize(new Dimension(170, 80));
        }
        else{
            setSize(125, 80);
            setPreferredSize(new Dimension(125, 80));
        }
        
        setBackground(intToColor(player.getColor()));

        nameLabel =new JLabel(player.getName());
        resourceLabel =new JLabel(player.resourceOfPlayerToStringWithoutResourceType());
        victoryPointLabel =new JLabel(Integer.toString(player.getVictoryPoint()));
        victoryLabel =new JLabel("WINNER !");

        victoryLabel.setVisible(false);

        GridBagConstraints c =new GridBagConstraints();

        c.insets = new Insets(1,1,1,1);
        c.gridx=0;
        c.gridy=1;
        add(nameLabel, c);
        c.gridy=2;
        add(resourceLabel, c);
        c.gridy=3;
        add(victoryPointLabel, c);
        c.gridy=4;
        add(victoryLabel, c);
    }

    public void updateInfo(){
        resourceLabel.setText(player.resourceOfPlayerToStringWithoutResourceType());
        victoryPointLabel.setText(Integer.toString(player.getVictoryPoint()));
        
        if(player.isWinner()){
            victoryLabel.setVisible(true);
        }
    }
    
    public Color intToColor(int colorPlayer) throws Exception{
        switch (colorPlayer) {
            case 0:
                return new Color(8, 74, 255);
            case 1:
                return new Color(255, 48, 48);
            case 2:
                return new Color(255, 110, 48);
            case 3:
                return new Color(204, 74, 255);
            default:
                throw new Exception();
        }
    }
}
