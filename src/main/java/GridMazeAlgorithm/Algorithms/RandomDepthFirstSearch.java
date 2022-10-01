package GridMazeAlgorithm.Algorithms;

import GridMazeAlgorithm.Cell;
import GridMazeAlgorithm.Colorizer;
import GridMazeAlgorithm.GridMaze;
import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class RandomDepthFirstSearch {

    Colorizer c;
    boolean found = false;

    Button start;


    public RandomDepthFirstSearch(Colorizer c,Button start){
        this.c = c;
        this.start = start;
    }


    public static void createMazeRDFS(GridMaze G, int y, int x)  {
        LinkedList<int[]> neighbours = G.freeAdjacentCells(y,x,false);
        Collections.shuffle(neighbours);


        G.grid[y][x].changeVisitable(false);


        for (int[] neighbour: neighbours){
            if(G.freeCell(neighbour)){
                if(neighbour[0]< y){
                    G.grid[y-1][x].changeColor(Color.WHITE,Color.WHITE);
                    G.grid[y-1][x].changeTypeOfField(Cell.typeOfField.FreeField);
                    G.grid[y-1][x].changeVisitable(false);
                }
                else if (neighbour[0]> y) {
                    G.grid[y+1][x].changeColor(Color.WHITE,Color.WHITE);
                    G.grid[y+1][x].changeTypeOfField(Cell.typeOfField.FreeField);
                    G.grid[y+1][x].changeVisitable(false);
                }
                else if (neighbour[1]> x) {
                    G.grid[y][x+1].changeColor(Color.WHITE,Color.WHITE);
                    G.grid[y][x+1].changeTypeOfField(Cell.typeOfField.FreeField);
                    G.grid[y][x+1].changeVisitable(false);
                }
                else {
                    G.grid[y][x-1].changeColor(Color.WHITE,Color.WHITE);
                    G.grid[y][x-1].changeTypeOfField(Cell.typeOfField.FreeField);
                    G.grid[y][x-1].changeVisitable(false);
                }
            }
            createMazeRDFS(G,neighbour[0],neighbour[1]);
        }
    }

    public void rdfssolve(GridMaze G, int y, int x){
        LinkedList<int[]> neighbours = G.freeAdjacentCells(y,x,true);
        Collections.shuffle(neighbours);


        G.grid[y][x].changeVisitable(false);

        if(!found) {
            c.drawCell(G.grid[y][x], Color.MAGENTA, Color.MAGENTA, 10);
        }

        if (G.grid[y][x].field == Cell.typeOfField.Target) {
            c.drawCell(G.grid[y][x],Color.RED,Color.RED,10);
            this.c.drawPath(this.start,G,G.grid[y][x].pathToRoot(),Color.RED,Color.RED,10);
            found = true;
        }

        for (int[] neighbour: neighbours){
            G.grid[neighbour[0]][neighbour[1]].changePrev(G.grid[y][x]);
            rdfssolve(G,neighbour[0],neighbour[1]);
        }

    }

}