package Ui;

import java.util.ArrayList;

import java.awt.*;
import javax.swing.JPanel;

import Game.Board;

public class BoardDisplay extends JPanel{
    private Board board;

    public BoardDisplay(Board board) throws Exception{
        this.board=board;

        setLayout(new GridLayout(4, 4, 0, 0));

        initCases();

        setVisible(true);
    }

    public void initCases() throws Exception{
        ArrayList<CaseDisplay> cd =new ArrayList<>();

        for(int i=0; i<board.getCases().length; i++){
            cd.add(new CaseDisplay(board.getCases()[i]));
        }

        for(CaseDisplay cases : cd){
            add(cases);
        }
    }
}
