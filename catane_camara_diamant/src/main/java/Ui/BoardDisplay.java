package Ui;

import java.util.ArrayList;

import javax.swing.JPanel;

import Game.Board;
import Game.Intersection;

public class BoardDisplay extends JPanel{
    private Board board;

    private ArrayList<CaseDisplay> cd;
    private ArrayList<IntersectionDisplay> interD;
    private ArrayList<HorizontalRoadDisplay> hrd;
    private ArrayList<VerticalRoadDisplay> vrd;

    public BoardDisplay(Board board) throws Exception{
        this.board=board;

        setLayout(null);

        initRoads();
        initIntersections();
        initCases();
        
        setVisible(true);
    }

    public void initCases() throws Exception{
        cd =new ArrayList<>();

        for(int i=0; i<board.getCases().length; i++){
            cd.add(new CaseDisplay(board.getCases()[i]));
        }

        int x =140;
        int y =40;
        for(CaseDisplay cases : cd){
            cases.setLocation(x, y);

            if((cases.getC().getId()+1)%4!=0)
                x+=100;
            else{
                x=140;
                y+=100;
            }

            add(cases);
        }
    }

    public void initIntersections() throws Exception{
        interD =new ArrayList<>();

        for(Intersection inter : board.getIntersections()){
            interD.add(new IntersectionDisplay(inter));
        }

        int x =130;
        int y =30;

        for(IntersectionDisplay inters : interD){
            inters.setLocation(x, y);

            if((inters.getInter().getId()+1)%5!=0)
                x+=100;
            else{
                x=130;
                y+=100;
            }

            add(inters);
        }
    }

    public void initRoads() throws Exception{
        hrd =new ArrayList<>();

        for(int i=0; i<20; i++){
            hrd.add(new HorizontalRoadDisplay(board.getRoads()[i]));
        }

        int x1=150;
        int y1=40;

        for(HorizontalRoadDisplay roads : hrd){
            roads.setLocation(x1, y1);

            if((roads.getR().getId()+1)%4!=0)
                x1+=100;
            else{
                x1=150;
                y1+=100;
            }

            add(roads);
        }

        vrd =new ArrayList<>();

        for(int i=20; i<40; i++){
            vrd.add(new VerticalRoadDisplay(board.getRoads()[i]));
        }

        int x2=140;
        int y2=50;

        for(VerticalRoadDisplay roads : vrd){
            roads.setLocation(x2, y2);

            if((roads.getR().getId()+1)%5!=0)
                x2+=100;
            else{
                x2=140;
                y2+=100;
            }

            add(roads);
        }
    }

    public void updateInfo() throws Exception{
        for(CaseDisplay cds : cd){
            cds.updateInfo();
        }
        for(IntersectionDisplay interd : interD){
            interd.updateInfo();
        }
        for(HorizontalRoadDisplay rDisplay : hrd){
            rDisplay.updateInfo();
        }
        for(VerticalRoadDisplay rDisplay : vrd){
            rDisplay.updateInfo();
        }
    }
}
