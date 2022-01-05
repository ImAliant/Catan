package Ui;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import Game.Board;
import Game.Game;
import Game.Player;

import java.awt.*;
import java.awt.event.*;

public class GameDisplay extends JFrame implements MouseInputListener{
    private Game game;
    private Board board;
    private Player[] players;

    private BoardDisplay boardDisplay;
    private PlayersDisplay playersPanel;

    public GameDisplay(Game game) throws Exception{
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
        
        playersPanel.setVisible(true);

        add(boardDisplay, BorderLayout.CENTER);
        add(playersPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void update() throws Exception{
        boardDisplay.updateInfo();

        playersPanel.updateInfo();
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
