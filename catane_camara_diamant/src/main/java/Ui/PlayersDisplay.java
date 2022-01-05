package Ui;

import java.awt.*;
import javax.swing.*;

import Game.Game;
import Game.Player;

public class PlayersDisplay extends JPanel{
    private Player[] players;
    private Game game;

    public PlayersDisplay(Game game, Player[] players) throws Exception{
        this.players=players;

        if(players.length==3){
            setLayout(new GridLayout(1,3));
        }
        else{
            setLayout(new GridLayout(1,4));
        }
    }
}
