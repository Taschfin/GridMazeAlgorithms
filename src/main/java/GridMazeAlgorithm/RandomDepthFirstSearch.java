package GridMazeAlgorithm;

import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class RandomDepthFirstSearch {


    public RandomDepthFirstSearch(GridMaze G){
    }


    public static void createMazeRDFS(GridMaze G, int y, int x)  {
        LinkedList<int[]> neighbours = G.freeAdjacentCells(y,x,false);
        Collections.shuffle(neighbours);


        G.grid[y][x]= (byte) 1;


        (G.rectangles[y][x]).setFill(Color.WHITE);
        (G.rectangles[y][x]).setStroke(Color.WHITE);




        for (int[] neighbour: neighbours){
           if(G.freeCell(neighbour)){
               if(neighbour[0]< y){
                   (G.rectangles[y-1][x]).setFill(Color.WHITE);
                   (G.rectangles[y-1][x]).setStroke(Color.WHITE);
                   G.grid[y-1][x] = (byte)1;
               }
               else if (neighbour[0]> y) {
                   (G.rectangles[y+1][x]).setFill(Color.WHITE);
                   (G.rectangles[y+1][x]).setStroke(Color.WHITE);
                   G.grid[y+1][x] = (byte)1;
               }
               else if (neighbour[1]> x) {
                   (G.rectangles[y][x+1]).setFill(Color.WHITE);
                   (G.rectangles[y][x+1]).setStroke(Color.WHITE);
                   G.grid[y][x+1] = (byte)1;
               }
               else {
                   (G.rectangles[y][x-1]).setFill(Color.WHITE);
                   (G.rectangles[y][x-1]).setStroke(Color.WHITE);
                   G.grid[y][x-1] = (byte)1;
               }
           }
           createMazeRDFS(G,neighbour[0],neighbour[1]);
        }
    }

    public static void rdfssolve(GridMaze G, int y, int x) throws InterruptedException {
        LinkedList<int[]> neighbours = G.freeAdjacentCells(y,x,true);
        Collections.shuffle(neighbours);


        G.grid[y][x]= (byte) 1;

        //Transition trans = new FillTransition(Duration.millis(10000),G.rectangles[y][x],Color.WHITE,Color.GREEN);
        //trans.play();


        (G.rectangles[y][x]).setStroke(Color.GREEN);



        if (G.grid[y][x] == (byte) 15) {
            System.out.println(x+" " +y);
            return;
        }


        for (int[] neighbour: neighbours){
            rdfssolve(G,neighbour[0],neighbour[1]);
        }
    }

}
