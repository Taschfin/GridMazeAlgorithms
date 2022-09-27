package GridMazeAlgorithm;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.TimeUnit;


public class BreadthFirstSearch {
    public static void bfs(GridMaze G, int x, int y)  {
        LinkedList<int[]> queue = new LinkedList<int[]>();

        G.grid[y][x] = (byte) 1;
        (G.rectangles[y][x]).setFill(Color.GREEN);
        (G.rectangles[y][x]).setStroke(Color.GREEN);
        int[] k = {y,x};

        queue.add(k);

        while (!queue.isEmpty()){
            int[] m = queue.removeFirst();
            if (G.grid[m[0]][m[1]]== (byte)15){
                return;
            }
            for (int[] w : G.freeAdjacentCells(m[0],m[1],true)){
                if(G.grid[w[0]][w[1]]==0){
                    G.grid[w[0]][w[1]]=1;
                    int[] temp = {w[0],w[1]};
                    (G.rectangles[w[0]][w[1]]).setFill(Color.GREEN);
                    (G.rectangles[w[0]][w[1]]).setStroke(Color.GREEN);
                    queue.add(temp);
                }
            }
        }
    }

}
