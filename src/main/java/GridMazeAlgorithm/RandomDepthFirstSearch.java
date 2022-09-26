package GridMazeAlgorithm;

import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class RandomDepthFirstSearch {


    public RandomDepthFirstSearch(GridMaze G){
    }


    public static void createMazeRDFS(GridMaze G, int y, int x) throws InterruptedException {
        LinkedList<int[]> neighbours = G.freeAdjacentCells(y,x);
        Collections.shuffle(neighbours);


        G.grid[y][x]= (byte) 1;


        (G.rectangles[y][x]).setFill(Color.BLUE);
        (G.rectangles[y][x]).setStroke(Color.WHITE);




        for (int[] neighbour: neighbours){
           if(G.freeCell(neighbour)){
               if(neighbour[0]< y){
                   System.out.println("1");
                   (G.rectangles[y-1][x]).setFill(Color.BLUE);
                   (G.rectangles[y-1][x]).setStroke(Color.WHITE);
                   G.grid[y-1][x] = (byte)1;
               }
               else if (neighbour[0]> y) {
                   System.out.println("2");
                   (G.rectangles[y+1][x]).setFill(Color.BLUE);
                   (G.rectangles[y+1][x]).setStroke(Color.WHITE);
                   G.grid[y+1][x] = (byte)1;
               }
               else if (neighbour[1]> x) {
                   (G.rectangles[y][x+1]).setFill(Color.BLUE);
                   (G.rectangles[y][x+1]).setStroke(Color.WHITE);
                   G.grid[y][x+1] = (byte)1;
               }
               else {
                   (G.rectangles[y][x-1]).setFill(Color.BLUE);
                   (G.rectangles[y][x-1]).setStroke(Color.WHITE);
                   G.grid[y][x-1] = (byte)1;
               }
           }



           createMazeRDFS(G,neighbour[0],neighbour[1]);


        }
    }

    public static void main(String[] args) {

    }
}
