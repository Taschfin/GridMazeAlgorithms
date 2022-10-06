package GridMazeAlgorithm;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.LinkedList;

public class GridMaze{

    int gridHeight;
    int gridWidth;

    public Cell[][] grid;

    double pixelSizeX = 500;
    double pixelSizeY = 500;

    Pane canvas;

    public double widthOfOneCell;
    public double heightOfOneCell;


    public GridMaze(int width, int height, Pane mazePane, boolean own){
        if(!own) {
            gridHeight = height * 2 + 1;
            gridWidth = width * 2 + 1;
        }
        else{
            gridHeight = height + 2;
            gridWidth = width + 2;
        }
        grid = new Cell[gridHeight][gridWidth];
        this.canvas = mazePane;

        this.widthOfOneCell = pixelSizeX/(double) gridWidth;
        this.heightOfOneCell = pixelSizeY/(double) gridHeight;

        if(!own) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    Color fill;
                    Color stroke;
                    Cell.typeOfField k;
                    if (i % 2 == 0) {
                        fill = Color.BLACK;
                        stroke = Color.BLACK;
                        k = Cell.typeOfField.Wall;
                    } else if (j % 2 == 0) {
                        fill = Color.BLACK;
                        stroke = Color.BLACK;
                        k = Cell.typeOfField.Wall;
                    } else {
                        fill = Color.WHITE;
                        stroke = Color.WHITE;
                        k = Cell.typeOfField.FreeField;
                    }
                    Cell c = new Cell(j, i, j * widthOfOneCell, i * heightOfOneCell, widthOfOneCell, heightOfOneCell, fill, stroke, null, k);

                    grid[i][j] = c;
                    mazePane.getChildren().add(c.rec);
                }
            }
        }
        else{
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    Color fill;
                    Color stroke;
                    Cell.typeOfField k;
                    if (i == 0 || i == gridHeight - 1 || j == 0 || j == gridWidth - 1) {
                        fill = Color.BLACK;
                        stroke = Color.BLACK;
                        k = Cell.typeOfField.Unremovable;
                    } else {
                        fill = Color.WHITE;
                        stroke = Color.BLACK;
                        k = Cell.typeOfField.FreeField;
                    }
                    Cell c = new Cell(j, i, j * widthOfOneCell, i * heightOfOneCell, widthOfOneCell, heightOfOneCell, fill, stroke, null, k);

                    grid[i][j] = c;
                    mazePane.getChildren().add(c.rec);
                }
            }
        }
    }

    public void freePaths () {
        for (int i = 0; i< gridHeight;i++) {
            for (int j = 0; j< gridWidth;j++) {
                if (grid[i][j].field == Cell.typeOfField.FreeField || grid[i][j].field == Cell.typeOfField.Target) {
                    grid[i][j].changeVisitable(true);
                    grid[i][j].changePrev(null);
                    grid[i][j].setDistance(Double.POSITIVE_INFINITY);
                    grid[i][j].fScore = 0;
                }
            }
        }
    }

    public void freePathsOwn () {
        for (int i = 0; i< gridHeight;i++) {
            for (int j = 0; j< gridWidth;j++) {
                if (grid[i][j].field == Cell.typeOfField.FreeField || grid[i][j].field == Cell.typeOfField.Target||grid[i][j].field == Cell.typeOfField.Wall) {
                    grid[i][j].changeVisitable(true);
                    grid[i][j].changePrev(null);
                    grid[i][j].setDistance(Double.POSITIVE_INFINITY);
                    grid[i][j].fScore = 0;
                    grid[i][j].changeTypeOfField(Cell.typeOfField.FreeField);
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

    public void revizualize(boolean own){
        for(Cell[] m : grid){
            for(Cell k : m){
                if(k.field == Cell.typeOfField.FreeField || k.field == Cell.typeOfField.Target ){
                    if(own) {
                        k.changeColor(Color.WHITE, Color.BLACK);
                    }
                    else{
                        k.changeColor(Color.WHITE, Color.WHITE);
                    }
                }
                else {
                    k.changeColor(Color.BLACK,Color.BLACK);
                }
            }
        }
    }



}
