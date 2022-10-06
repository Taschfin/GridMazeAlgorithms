package GridMazeAlgorithm.Algorithms;

import GridMazeAlgorithm.Cell;
import GridMazeAlgorithm.Colorizer;
import GridMazeAlgorithm.GridMaze;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;

public class Dijkstra {

    Colorizer colorizer;
    GridMaze G;
    LinkedList<Cell> Q;

    public int visitedCells=0;

    public Dijkstra(Colorizer colorizer, GridMaze g){
        this.colorizer = colorizer;
        this.G  = g;
    }

    public void initizialize(int x, int y){
        this.G.grid[y][x].setDistance(0);

        this.Q = new LinkedList<Cell>();
        for (Cell[] m : G.grid) {
            for (Cell k: m){
                if(k.field == Cell.typeOfField.FreeField || k.field == Cell.typeOfField.Target){
                    this.Q.add(k);
                }
            }
        }
    }

    public Cell dijkstraAlgorithm(int x, int y){
        RandomDepthFirstSearch checkIfPath = new RandomDepthFirstSearch(null,null);
        checkIfPath.rdfsSolveForDijkstar(G,y,x);
        G.freePaths();
        if(checkIfPath.target==null){
            return null;
        }
        initizialize(x,y);
        double k=0.99;
        double abziehen = 1/((double)Math.min(G.getGridHeight(), G.getGridWidth())*25);
        while (!this.Q.isEmpty()){
            Cell u = extractMin();
            Color c;
            if(k > 0.2){
                c = new Color(k, (1.0-k), 0.0,k);
            }
            else {
                c = new Color(0.2, 0.8, 0.0,0.2);
            }

            colorizer.drawCell(u,c,c,7);
            visitedCells++;
            if(u.field == Cell.typeOfField.Target){
                return u;
            }
            for(Cell neighbour : G.freeAdjacentCells(u)){
                if (Q.contains(neighbour)){
                    updateDistance(u,neighbour);
                }
            }
            k-=abziehen;
        }
        return null;
    }

    public Cell extractMin(){
        Cell min = this.Q.get(0);
        for(Cell temp : this.Q){
            if (temp.distance < min.distance){
                min = temp;
            }
        }

        this.Q.remove(min);
        return min;
    }

    public void updateDistance(Cell c1, Cell c2){
        double dis = c1.distance +1;
        if(dis< c2.distance){
            c2.setDistance(dis);
            c2.changePrev(c1);
        }
    }
}
