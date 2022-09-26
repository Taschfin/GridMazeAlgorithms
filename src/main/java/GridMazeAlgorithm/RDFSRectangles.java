package GridMazeAlgorithm;

import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javafx.scene.shape.Rectangle;


public class RDFSRectangles
{

    public void RDFSRectangles(){

    }

    public static void createMazeRDFS(GridMazeRectnagles G, int y, int x){
        LinkedList<Rectangle> neighbours = G.freeAdjacentCells(y,x);
        Collections.shuffle(neighbours);

        Rectangle curr = (Rectangle) G.gridGraph.get(y).get(x*2);

        curr.setStyle("-fx-background-color: black, white;-fx-background-insets: 0, 5 5 5 5; ");


        /*for (int[] neighbour: neighbours){
            if(G.freeCell(neighbour)){
                if(neighbour[0]< y){
                    G.grid[y-1][x] = (byte)1;
                }
                else if (neighbour[0]> y) {
                    G.grid[y+1][x] = (byte)1;
                }
                else if (neighbour[1]> x) {
                    G.grid[y][x+1] = (byte)1;
                }
                else {
                    G.grid[y][x-1] = (byte)1;
                }
            }

            createMazeRDFS(G,neighbour[0],neighbour[1]);
        }   */

    }

}
