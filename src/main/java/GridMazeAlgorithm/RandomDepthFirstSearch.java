package GridMazeAlgorithm;

import java.util.Collections;
import java.util.LinkedList;

public class RandomDepthFirstSearch {

    public RandomDepthFirstSearch(){

    }


    public static void createMazeRDFS(GridMaze G, int y, int x){
        LinkedList<int[]> neighbours = G.freeAdjacentCells(y,x);
        Collections.shuffle(neighbours);

        G.grid[y][x]= (byte) 1;

        G.printMaze();
        System.out.println("");



        for (int[] neighbour: neighbours){
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
        }
    }

    public static void main(String[] args) {
        GridMaze G = new GridMaze(2,3);
        createMazeRDFS(G,3,3);
    }
}
