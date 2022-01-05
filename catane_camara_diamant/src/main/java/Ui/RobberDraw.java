package Ui;

import java.awt.*;

import javax.swing.*;

public class RobberDraw extends JPanel{
    private int x, y;

    public RobberDraw(int x, int y){
        this.x=x;
        this.y=y;

        setBackground(Color.BLACK);
        setSize(10, 10);

        setOpaque(false);

        setVisible(true);
    }
}
