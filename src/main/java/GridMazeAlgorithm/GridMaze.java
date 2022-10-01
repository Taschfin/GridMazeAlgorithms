package GridMazeAlgorithm;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.LinkedList;

public class GridMaze {

    int gridHeight;
    int gridWidth;

    public Cell[][] gridClone;

    public Cell[][] grid;

    double pixelSizeX = 500;
    double pixelSizeY = 500;

    public Line l;
    public Line l2;

    Pane canvas;


    public GridMaze(int width, int height, Pane mazePane){
        gridHeight = height*2+1;
        gridWidth = width*2+1;
        grid = new Cell[gridHeight][gridWidth];
        this.canvas = mazePane;

        double widthOfCell = pixelSizeX/(double) gridWidth;
        double heightOfCell = pixelSizeY/(double) gridHeight;


        for (int i = 0; i<grid.length;i++){
            for (int j = 0; j< grid[i].length; j++) {
                Color fill;
                Color stroke;
                Cell.typeOfField k;
                if (i%2==0){
                    fill = Color.BLACK;
                    stroke = Color.BLACK;
                    k = Cell.typeOfField.Wall;
                } else if(j%2==0) {
                    fill = Color.BLACK;
                    stroke = Color.BLACK;
                    k = Cell.typeOfField.Wall;
                }
                else {
                    fill = Color.WHITE;
                    stroke = Color.WHITE;
                    k = Cell.typeOfField.FreeField;
                }
                Cell c  = new Cell(j,i,j*widthOfCell,i*heightOfCell,widthOfCell,heightOfCell,fill,stroke,null, k);
                grid[i][j]=c;
                mazePane.getChildren().add(c.rec);
            }
        }


    }

    public void freePaths () {
        for (int i = 0; i< gridHeight;i++) {
            for (int j = 0; j< gridWidth;j++) {
                if (grid[i][j].field == Cell.typeOfField.FreeField || grid[i][j].field == Cell.typeOfField.Target) {
                    grid[i][j].changeVisitable(true);
                }
            }
        }
    }

    public LinkedList<int[]> freeAdjacentCells(int y, int x, boolean built){
        LinkedList<int[]> adj = new LinkedList<int[]>();

        if(!(0<x && x<gridWidth && 0< y && y < gridHeight)){
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
            if (grid[y-1][x].field == Cell.typeOfField.FreeField || grid[y-1][x].field ==Cell.typeOfField.Target) {
                int[] up = {y - 1, x};
                if (freeCell(up)) {
                    adj.add(up);
                }
            }

            if (grid[y+1][x].field == Cell.typeOfField.FreeField || grid[y+1][x].field ==Cell.typeOfField.Target) {
                int[] down = {y + 1, x};
                if (freeCell(down)) {
                    adj.add(down);
                }
            }

            if (grid[y][x-1].field == Cell.typeOfField.FreeField || grid[y][x-1].field ==Cell.typeOfField.Target) {
                int[] left = {y, x-1};
                if (freeCell(left)) {
                    adj.add(left);
                }
            }

            if (grid[y][x+1].field == Cell.typeOfField.FreeField || grid[y][x+1].field ==Cell.typeOfField.Target) {
                int[] right = {y, x + 1};
                if (freeCell(right)) {
                    adj.add(right);
                }
            }
        }

        return adj;
    }


    public LinkedList<Cell> freeAdjacentCells(Cell c){
        LinkedList<Cell> adj = new LinkedList<Cell>();

        int x = c.indexX;
        int y =  c.indexY;

        if(!(0<x && x<gridWidth && 0< y&& y < gridHeight)){
            throw new RuntimeException("X or Y is not within the boundaries");
        }


        if (grid[y-1][x].field == Cell.typeOfField.FreeField || grid[y-1][x].field ==Cell.typeOfField.Target) {
            if (grid[y-1][x].visitable) {
                adj.add(grid[y-1][x]);
            }
        }

        if (grid[y+1][x].field == Cell.typeOfField.FreeField || grid[y+1][x].field ==Cell.typeOfField.Target) {
            if (grid[y+1][x].visitable) {
                adj.add(grid[y+1][x]);
            }
        }

        if (grid[y][x-1].field == Cell.typeOfField.FreeField || grid[y][x-1].field ==Cell.typeOfField.Target) {
            if (grid[y][x-1].visitable) {
                adj.add(grid[y][x-1]);
            }
        }

        if (grid[y][x+1].field == Cell.typeOfField.FreeField || grid[y][x+1].field ==Cell.typeOfField.Target) {
            if (grid[y][x+1].visitable) {
                adj.add(grid[y][x+1]);
            }
        }

        return adj;
    }

    public boolean freeCell(int[] pos){
        return grid[pos[0]][pos[1]].visitable;
    }

    public void drawLine(Line l, Line l2){
        this.l = l;
        this.l2 =l2;
        this.canvas.getChildren().add(l);
        this.canvas.getChildren().add(l2);
    }

    public void removeLine(){
        this.canvas.getChildren().remove(this.l);
        this.canvas.getChildren().remove(this.l2);
    }

    public void printMaze(){
        for(Cell[] k : grid){
            for(Cell m : k){
                if(Cell.typeOfField.FreeField == m.field){
                    System.out.print("0");
                }
                else {
                    System.out.print("7");
                }
            }
            System.out.println("");
        }
    }

    public void changeCellType(int indexY, int indexX, Cell.typeOfField token){
        if(!(0<=indexX && indexX<gridWidth && 0<= indexY && indexY < gridHeight)){
            throw new RuntimeException("X: " + indexX + " or Y: " +indexY+" is not within the boundaries");
        }

        grid[indexY][indexX].field = token;
    }

    /*public void printMaze(){
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
    }*/

    public static void printAdj(LinkedList<int[]> adj){
        for(int[] cell: adj){
            System.out.println("Neighbours Y: " + cell[0] + " X: " + cell[1] + "   ");
        }
    }

    public void revizualize(){
        for(Cell[] m : gridClone){
            for(Cell k : m){
                if(k.field == Cell.typeOfField.FreeField){
                    k.changeColor(Color.WHITE,Color.WHITE);
                }
            }
        }
    }

   /* public void copy_reset (){
        byte[][] m = grid.clone();
        Rectangle[][] n = rectangles.clone();

        this.grid = m;
        this.rectangles =n;

        }*/


}
