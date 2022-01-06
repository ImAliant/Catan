package Ui;

import javax.swing.*;

import Game.Port;

public class PortDisplay extends JPanel{
    private JLabel portType;

    public PortDisplay(Port port){
        portType =new JLabel(port.toString());

        setSize(85, 25);

        setOpaque(false);

        add(portType);
    }
}
