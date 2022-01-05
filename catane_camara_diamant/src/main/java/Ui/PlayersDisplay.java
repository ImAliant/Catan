package Ui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import Game.Game;
import Game.Player;

public class PlayersDisplay extends JPanel{
    private Player[] players;
    private Game game;

    private ArrayList<PanelPlayer> panplay;
    private PanelPlayer p1, p2, p3, p4;

    public PlayersDisplay(Game game, Player[] players) throws Exception{
        this.players=players;

        if(players.length==3){
            setLayout(new GridLayout(1,3));
        }
        else{
            setLayout(new GridLayout(1,4));
        }

        panplay =new ArrayList<>();

        if(game.getPlayers().length==3){
            panplay.add(p1=new PanelPlayer(game, players[0]));
            panplay.add(p2=new PanelPlayer(game, players[1]));
            panplay.add(p3=new PanelPlayer(game, players[2]));
        }
        else{
            panplay.add(p1=new PanelPlayer(game, players[0]));
            panplay.add(p2=new PanelPlayer(game, players[1]));
            panplay.add(p3=new PanelPlayer(game, players[2]));
            panplay.add(p4=new PanelPlayer(game, players[3]));
        }

        for(PanelPlayer panelPlayer : panplay){
            add(panelPlayer);
        }
    }

    public void updateInfo(){
        for(PanelPlayer p : panplay){
            p.updateInfo();
        }
    }
}
