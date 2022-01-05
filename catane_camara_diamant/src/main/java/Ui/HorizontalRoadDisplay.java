package Ui;

import javax.swing.*;
import java.awt.*;

import Game.Road;

public class HorizontalRoadDisplay extends JPanel{
    private Road r;

    public HorizontalRoadDisplay(Road r) throws Exception{
        this.r=r;
        
        setSize(80, 5);
        
        setBackground(Color.BLACK);

        setVisible(true);
    }

    public void updateInfo() throws Exception{
        if(r.getPlayer()!=null)
            setBackground(intToColor(r.getPlayer().getColor()));
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

    public Road getR() {
        return r;
    }

    public void setR(Road r) {
        this.r = r;
    }
}
