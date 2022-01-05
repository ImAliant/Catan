package Ui;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import Game.Board;
import Game.Game;
import Game.Player;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameDisplayAlexandre extends JFrame implements MouseInputListener{
    private Game game;
    private Board board;
    private Player[] players;

    private BoardDisplay boardDisplay;
    private PlayersDisplay playersPanel;
    private PanelPlayer p1, p2, p3, p4;

    private ArrayList<PanelPlayer> panplay;
    
    public GameDisplayAlexandre(Game game) throws Exception{
        init(game);
    }

    public void init(Game game) throws Exception{
        this.board=game.getBoard();
        this.players=game.getPlayers();

        setTitle("Colons de Catanes");
        
        setSize(700, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    
        addMouseListener(this);
        
        try{
            setIconImage(new ImageIcon(getClass().getResource("/Image/colons_catanes_carre.jpg")).getImage());
        } catch(Exception e){
            System.out.println("Le chemin de l'icone n'existe pas !");
        }

        boardDisplay =new BoardDisplay(board);

        playersPanel =new PlayersDisplay(game, players);

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
            playersPanel.add(panelPlayer);
        }
        
        playersPanel.setVisible(true);

        add(boardDisplay, BorderLayout.CENTER);
        add(playersPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void update(){
        for(PanelPlayer panelPlayer : panplay){
            panelPlayer.updateInfo();
        }
    }

    

    //Getters et Setters
    public Player[] getPlayers() {return players;}
    public void setPlayers(Player[] players) {this.players = players;}
    public Board getBoard() {return board;}
    public void setBoard(Board board) {this.board = board;}
    public Game getGame() {return game;}
    public void setGame(Game game) {this.game = game;}
    public PlayersDisplay getPlayersPanel() {return playersPanel;}
    public void setPlayersPanel(PlayersDisplay playersPanel) {this.playersPanel = playersPanel;}
    public PanelPlayer getP1() {return p1;}
    public void setP1(PanelPlayer p1) {this.p1 = p1;}
    public PanelPlayer getP2() {return p2;}
    public void setP2(PanelPlayer p2) {this.p2 = p2;}
    public PanelPlayer getP3() {return p3;}
    public void setP3(PanelPlayer p3) {this.p3 = p3;}
    public PanelPlayer getP4() {return p4;}
    public void setP4(PanelPlayer p4) {this.p4 = p4;}
    public ArrayList<PanelPlayer> getPanplay() {return panplay;}
    public void setPanplay(ArrayList<PanelPlayer> panplay) {this.panplay = panplay;}

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX()+", "+e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
