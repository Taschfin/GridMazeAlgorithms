package GridMazeAlgorithm;

import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;

public class GridMazeRectnagles {
    public ArrayList<ArrayList> gridGraph;
    private int gridWidth;
    private int gridHeight;

    public GridMazeRectnagles(ArrayList<ArrayList> recs) {
        this.gridGraph = recs;
        this.gridHeight = recs.size();
        this.gridWidth = recs.get(0).size()/2;
    }


    public LinkedList<Rectangle> freeAdjacentCells(int y, int x){
        LinkedList<Rectangle> adj = new LinkedList<Rectangle>();



        if(!(0<=x && x<gridWidth && 0<= y && y < gridHeight)){
            throw new RuntimeException("X or Y is not within the boundaries");
        }

        x=2*x;

        if(0<=y-1){
            if (freeCell(y-1,x)){
                adj.add((Rectangle) this.gridGraph.get(y-1).get(x));
            }
        }

        if(y+1<=gridHeight-1){
            if (freeCell(y+1,x)){
                adj.add((Rectangle) this.gridGraph.get(y+1).get(x));
            }
        }

        if(0<=x-1){
            if (freeCell(y,x-2)){
                adj.add((Rectangle) this.gridGraph.get(y).get(x-2));
            }
        }

        if(x+2 <= gridWidth-1){
            if (freeCell(y,x+2)){
                adj.add((Rectangle) this.gridGraph.get(y).get(x+2));
            }
        }

        return adj;
    }

    public boolean freeCell(int y, int x){
        return (Boolean) this.gridGraph.get(y).get(x+1);
    }


}
