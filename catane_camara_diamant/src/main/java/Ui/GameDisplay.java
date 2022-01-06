package Ui;

import javax.swing.*;

import Game.Board;
import Game.Game;
import Game.Player;

import java.awt.*;

public class GameDisplay extends JFrame {
    private Game game;
    private Board board;
    private Player[] players;

    private BoardDisplay boardDisplay;
    private PlayersDisplay playersPanel;

    public GameDisplay(Game game, Board board, Player[] players) throws Exception{
        init(game, board, players);
    }

    public void init(Game game, Board board, Player[] players) throws Exception{
        this.board=board;
        this.players=players;

        setTitle("Colons de Catanes");
        
        setSize(700, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        try{
            setIconImage(new ImageIcon(getClass().getResource("/Image/colons_catanes_carre.jpg")).getImage());
        } catch(Exception e){
            System.out.println("Le chemin de l'icone n'existe pas !");
        }

        boardDisplay =new BoardDisplay(board);

        playersPanel =new PlayersDisplay(players);
        
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
}
