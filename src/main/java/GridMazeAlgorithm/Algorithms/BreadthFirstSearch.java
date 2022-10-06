package GridMazeAlgorithm.Algorithms;

import GridMazeAlgorithm.Cell;
import GridMazeAlgorithm.Colorizer;
import GridMazeAlgorithm.GridMaze;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.TimeUnit;


public class BreadthFirstSearch {

    Colorizer colorizer;

    public int visitedCells;
    public BreadthFirstSearch( Colorizer colorizer){

        this.colorizer = colorizer;
    }
    public Cell bfs(GridMaze G, int x, int y)  {
        LinkedList<int[]> queue = new LinkedList<int[]>();

        G.grid[y][x].changeVisitable(false);

        this.colorizer.drawCell(G.grid[y][x],Color.LAWNGREEN,Color.LAWNGREEN,7);
        int[] k = {y,x};

        queue.add(k);

        while (!queue.isEmpty()){
            int[] m = queue.removeFirst();
            visitedCells++;
            if (G.grid[m[0]][m[1]].field == Cell.typeOfField.Target){
                return G.grid[m[0]][m[1]];
            }
            this.colorizer.drawCell(G.grid[m[0]][m[1]],Color.RED,Color.RED,1);

            for (int[] w : G.freeAdjacentCells(m[0],m[1],true)){
                if(G.grid[w[0]][w[1]].visitable==true){
                    G.grid[w[0]][w[1]].changePrev(G.grid[m[0]][m[1]]);
                    G.grid[w[0]][w[1]].changeVisitable(false);
                    int[] temp = {w[0],w[1]};
                    this.colorizer.drawCell(G.grid[w[0]][w[1]],Color.LAWNGREEN,Color.LAWNGREEN,7);
                    queue.add(temp);
                }
            }
            this.colorizer.drawCell(G.grid[m[0]][m[1]],Color.LAWNGREEN,Color.LAWNGREEN,1);
        }
        return null;
    }

}
