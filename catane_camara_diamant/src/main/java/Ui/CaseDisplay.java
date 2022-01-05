package Ui;

import Game.*;

import javax.swing.*;
import java.awt.*;

public class CaseDisplay extends JPanel{
    private Case c;
    private RobberDraw robber;

    public CaseDisplay(Case c) throws Exception{
        this.c=c;

        setSize(100, 100);
        setPreferredSize(new Dimension(100, 100));

        setBackground(intToColor(c.getLand()));
        setLayout(new GridBagLayout());

        JLabel landTypeLabel =new JLabel(landTypeToString(c.getLand()));
        JLabel idLabel =new JLabel(Integer.toString(c.getId()));
        JLabel diceRollLabel =new JLabel(Integer.toString(c.getDiceRoll()));

        robber =new RobberDraw(4, 80);

        if(c.isRobber())
            robber.setOpaque(true);

        GridBagConstraints bc =new GridBagConstraints();

        bc.insets = new Insets(1,1,1,1);
        bc.gridx=0;
        bc.gridy=1;
        add(landTypeLabel, bc);
        bc.gridy=2;
        add(idLabel, bc);
        bc.gridy=3;
        add(diceRollLabel, bc);
        bc.gridy=4;
        add(robber, bc);

        setVisible(true);
    }

    public void updateInfo(){
        if(c.isRobber())
            robber.setOpaque(true);
        else
            robber.setOpaque(false);
    }

    public String landTypeToString(int landType) throws Exception{
        switch (landType) {
            case Board.FORET:
                return "FORET";
            case Board.MONTAGNE:
                return "MONTAGNE";
            case Board.CHAMPS:
                return "CHAMPS";
            case Board.PRE:
                return "PRE";
            case Board.COLLINE:
                return "COLLINE";
            case Board.DESERT:
                return "DESERT";
            default:
                throw new Exception();
        }
    }

    public Color intToColor(int landType) throws Exception{
        switch (landType) {
            case Board.FORET:
                return new Color(31, 172, 0);
            case Board.MONTAGNE:
                return new Color(201, 201, 201);
            case Board.CHAMPS:
                return new Color(255, 232, 0);
            case Board.PRE:
                return new Color(187, 255, 76);
            case Board.COLLINE:
                return new Color(192, 121, 0);
            case Board.DESERT:
                return new Color(255, 255, 255);
            default:
                throw new Exception();
        }
    }

    public Case getC() {return c;}
    public void setC(Case c) {this.c = c;}
}
