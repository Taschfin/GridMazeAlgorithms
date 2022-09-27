package GridMazeAlgorithm;

import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.LinkedList;

public class GridMaze {

    public byte [][] grid;
    int gridHeight;
    int gridWidth;

    public Rectangle[][] rectangles;


    public GridMaze(int width, int height, Rectangle[][] rectangles){
        gridHeight = height*2+1;
        gridWidth = width*2+1;
        grid = new byte[gridHeight][gridWidth];
        this.rectangles =rectangles;

        copy_reset();

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

    public void freePaths () {
        for (int i = 0; i< gridHeight;i++) {
            for (int j = 0; j< gridWidth;j++) {
                if (grid[i][j] == (byte)1) {
                    grid[i][j] = (byte)0;
                }
            }
        }
    }

    public LinkedList<int[]> freeAdjacentCells(int y, int x, boolean built){
        LinkedList<int[]> adj = new LinkedList<int[]>();


        if(!(0<=x && x<gridWidth && 0<= y && y < gridHeight)){
            throw new RuntimeException("X or Y is not within the boundaries");
        }

        if(!built) {
            if (1 <= y - 2) {
                int[] up = {y - 2, x};
                if (freeCell(up)) {
                    adj.add(up);
                }
            }

            if (y + 2 <= gridHeight - 1) {
                int[] down = {y + 2, x};
                if (freeCell(down)) {
                    adj.add(down);
                }
            }

            if (0 <= x - 2) {
                int[] left = {y, x - 2};
                if (freeCell(left)) {
                    adj.add(left);
                }
            }

            if (x + 2 <= gridWidth - 1) {
                int[] right = {y, x + 2};
                if (freeCell(right)) {
                    adj.add(right);
                }
            }
        }
        else{
            if (grid[y-1][x]!=3 && grid[y-1][x]!=7) {
                int[] up = {y - 1, x};
                if (freeCell(up)) {
                    adj.add(up);
                }
            }

            if (grid[y+1][x]!=3 && grid[y+1][x]!=7) {
                int[] down = {y + 1, x};
                if (freeCell(down)) {
                    adj.add(down);
                }
            }

            if (grid[y][x-1]!=3 && grid[y][x-1]!=7) {
                int[] left = {y, x-1};
                if (freeCell(left)) {
                    adj.add(left);
                }
            }

            if (grid[y][x+1]!=3 || grid[y][x+1]!=7) {
                int[] right = {y, x + 1};
                if (freeCell(right)) {
                    adj.add(right);
                }
            }
        }

        return adj;
    }

    public boolean freeCell(int[] pos){
        return grid[pos[0]][pos[1]] == 0;
    }

    public void setCell(int indexY, int indexX, byte token){
        if(!(0<=indexX && indexX<gridWidth && 0<= indexY && indexY < gridHeight)){
            throw new RuntimeException("X: " + indexX + " or Y: " +indexY+" is not within the boundaries");
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
            System.out.println("Y: " + cell[0] + " X: " + cell[1] + "   ");
        }
    }

    public void copy_reset (){
        byte[][] m = grid.clone();
        Rectangle[][] n = rectangles.clone();

        this.grid = m;
        this.rectangles =n;

        }


}
