package Ui;

import Game.*;

import javax.swing.*;
import java.awt.*;

public class CaseDisplay extends JPanel{
    private Case c;
    private int x, y;

    public CaseDisplay(Case c/*, int x, int y*/) throws Exception{
        this.c=c;
        //this.x=x;
        //this.y=y;

        setBackground(intToColor(c.getLand()));
        setLayout(new GridBagLayout());

        JLabel landTypeLabel =new JLabel(landTypeToString(c.getLand()));
        JLabel idLabel =new JLabel(Integer.toString(c.getId()));
        JLabel diceRollLabel =new JLabel(Integer.toString(c.getDiceRoll()));

        GridBagConstraints bc =new GridBagConstraints();

        bc.insets = new Insets(1,1,1,1);
        bc.gridx=0;
        bc.gridy=1;
        add(landTypeLabel, bc);
        bc.gridy=2;
        add(idLabel, bc);
        bc.gridy=3;
        add(diceRollLabel, bc);

        setVisible(true);
    }

    /*public void paintComponent(Graphics g){
        super.paintComponent(g);
        try {
            g.setColor(intToColor(c.getLand()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        g.drawRect(x, y, 100, 100);
        g.fillRect(x, y, 100, 100);
    }*/

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
}
