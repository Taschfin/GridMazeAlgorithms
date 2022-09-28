package GridMazeAlgorithm;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.TimeUnit;


public class BreadthFirstSearch {

    Colorizer colorizer;
    public BreadthFirstSearch(Colorizer colorizer){
        this.colorizer = colorizer;
    }
    public void bfs(GridMaze G, int x, int y)  {
        LinkedList<int[]> queue = new LinkedList<int[]>();

        G.grid[y][x] = (byte) 1;

        this.colorizer.drawTile(G.rectangles[y][x],Color.LAWNGREEN,Color.LAWNGREEN,10);
        int[] k = {y,x};

        queue.add(k);

        while (!queue.isEmpty()){
            int[] m = queue.removeFirst();

            if (G.grid[m[0]][m[1]]== (byte)15){
                System.out.println(G.grid[m[0]][m[1]]);
                this.colorizer.drawTile(G.rectangles[y][x],Color.RED,Color.RED,10);
                return;
            }
            this.colorizer.drawTile(G.rectangles[m[0]][m[1]],Color.RED,Color.RED,1);

            for (int[] w : G.freeAdjacentCells(m[0],m[1],true)){
                GridMaze.printAdj(G.freeAdjacentCells(m[0],m[1],true));
                if(G.grid[w[0]][w[1]]==0){
                    G.grid[w[0]][w[1]]=1;
                    int[] temp = {w[0],w[1]};
                    this.colorizer.drawTile(G.rectangles[w[0]][w[1]],Color.LAWNGREEN,Color.LAWNGREEN,10);
                    queue.add(temp);
                }
            }
            this.colorizer.drawTile(G.rectangles[m[0]][m[1]],Color.LAWNGREEN,Color.LAWNGREEN,1);
        }
    }

}
