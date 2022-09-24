package GridMazeAlgorithm;

import java.util.Arrays;
import java.util.LinkedList;

public class GridMaze {

    byte [][] grid;
    int gridHeight;
    int gridWidth;

    public GridMaze(int width, int height){
        gridHeight = height*2+1;
        gridWidth = width*2+1;
        grid = new byte[gridHeight][gridWidth];

        // Builds Rows and Cols for the Maze (loops splitet for efficiency)

        for (int i = 0; i<=grid.length;i+=2){
            for (int j = 0; j< grid[i].length; j++) {
                grid[i][j] = 7;
            }
        }

        for (int i = 1; i<=grid.length-1;i+=2){
            for (int j = 0; j<= grid[i].length; j+=2){
                grid[i][j]=3;
            }

        }
    }

    public LinkedList<int[]> freeAdjacentCells(int y, int x){
        LinkedList<int[]> adj = new LinkedList<int[]>();

        int indexY = y*2+1;
        int indexX = x*2+1;

        if(!(0<=indexX && indexX<gridWidth && 0<= indexY && indexY < gridHeight)){
            throw new RuntimeException("X or Y is not within the boundaries");
        }

        if(1<=indexY-2){
            int[] up = {indexY-2,indexX};
            if (freeCell(up)){
                adj.add(up);
            }
        }

        if(indexY+2<=gridHeight-1){
            int[] down = {indexY+2,indexX};
            if (freeCell(down)){
                adj.add(down);
            }
        }

        if(0<=indexX-2){
            int[] left = {indexY,indexX-2};
            if (freeCell(left)){
                adj.add(left);
            }
        }

        if(indexX+2 <= gridWidth-1){
            int[] right = {indexY,indexX+2};
            if (freeCell(right)){
                adj.add(right);
            }
        }

        return adj;
    }

    public boolean freeCell(int[] pos){
        return grid[pos[0]][pos[1]] == 0;
    }

    public void setCell(int indexY, int indexX, byte token){
        if(!(0<=indexX && indexX<gridWidth && 0<= indexY && indexY < gridHeight)){
            throw new RuntimeException("X or Y is not within the boundaries");
        }

        grid[indexY][indexX] = token;
    }

    public void printMaze(){
        for(byte[] k : grid){
            for(byte m : k){
                if(m==7){
                    System.out.print(" -");
                }
                else if (m == 3) {
                    System.out.print(" |");
                }
                else if (m == 9) {
                    System.out.print("x");
                }
                else{
                    System.out.print(" " +m);
                }
            }
            System.out.print("\n");
        }
    }

    public static void printAdj(LinkedList<int[]> adj){
        for(int[] cell: adj){
            System.out.print("Y: " + cell[0] + " X: " + cell[1] + "   ");
        }
    }

    public static void main(String[] args) {
        GridMaze G = new GridMaze(5,3);

        //printAdj(G.adjacentCells(0,1));
        System.out.println("");
        G.setCell(5,9, (byte)1);

        G.printMaze();
        printAdj(G.freeAdjacentCells(2,3));
    }

}
