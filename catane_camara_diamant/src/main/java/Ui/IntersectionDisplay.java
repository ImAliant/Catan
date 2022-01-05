package Ui;

import java.awt.*;
import javax.swing.*;

import Game.Intersection;

public class IntersectionDisplay extends JPanel{
    private Intersection inter;

    public IntersectionDisplay(Intersection inter) throws Exception{
        this.inter=inter;

        setSize(20, 20);

        setBorder(BorderFactory.createLineBorder(Color.black, 2));

        JLabel interID =new JLabel(Integer.toString(inter.getId()));

        setBackground(Color.WHITE);

        add(interID, BorderLayout.CENTER);

        setVisible(true);
    }

    public void updateInfo() throws Exception{
        if(inter.getPlayer()!=null)
            setBackground(intToColor(inter.getPlayer().getColor()));
    }

    public Color intToColor(int color) throws Exception{
        switch (color) {
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

    public Intersection getInter() {
        return inter;
    }

    public void setInter(Intersection inter) {
        this.inter = inter;
    }

    
}
