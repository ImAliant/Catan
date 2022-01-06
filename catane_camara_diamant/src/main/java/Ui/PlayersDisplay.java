package Ui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import Game.Player;

public class PlayersDisplay extends JPanel{
    private Player[] players;

    private ArrayList<PanelPlayer> panplay;
    private PanelPlayer p1, p2, p3, p4;

    public PlayersDisplay(Player[] players) throws Exception{
        this.players=players;

        if(players.length==3){
            setLayout(new GridLayout(1,3));
        }
        else{
            setLayout(new GridLayout(1,4));
        }

        panplay =new ArrayList<>();

        if(players.length==3){
            panplay.add(p1=new PanelPlayer(players.length, players[0]));
            panplay.add(p2=new PanelPlayer(players.length, players[1]));
            panplay.add(p3=new PanelPlayer(players.length, players[2]));
        }
        else{
            panplay.add(p1=new PanelPlayer(players.length, players[0]));
            panplay.add(p2=new PanelPlayer(players.length, players[1]));
            panplay.add(p3=new PanelPlayer(players.length, players[2]));
            panplay.add(p4=new PanelPlayer(players.length, players[3]));
        }

        for(PanelPlayer panelPlayer : panplay){
            add(panelPlayer);
        }

        setVisible(true);
    }

    public void updateInfo(){
        for(PanelPlayer p : panplay){
            p.updateInfo();
        }
    }

    public Player[] getPlayers() {return players;}
    public void setPlayers(Player[] players) {this.players = players;}
    public ArrayList<PanelPlayer> getPanplay() {return panplay;}
    public void setPanplay(ArrayList<PanelPlayer> panplay) {this.panplay = panplay;}
    public PanelPlayer getP1() {return p1;}
    public void setP1(PanelPlayer p1) {this.p1 = p1;}
    public PanelPlayer getP2() {return p2;}
    public void setP2(PanelPlayer p2) {this.p2 = p2;}
    public PanelPlayer getP3() {return p3;}
    public void setP3(PanelPlayer p3) {this.p3 = p3;}
    public PanelPlayer getP4() {return p4;}
    public void setP4(PanelPlayer p4) {this.p4 = p4;}
}
