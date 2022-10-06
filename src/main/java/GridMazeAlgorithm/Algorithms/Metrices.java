package GridMazeAlgorithm.Algorithms;

import GridMazeAlgorithm.Cell;

public class Metrices {
    public static double manhatten(Cell c1, Cell c2){
        return Math.abs(c1.indexX- c2.indexX)+Math.abs(c1.indexY- c2.indexY);
    }

    public static double euclidean(Cell c1, Cell c2){
        return Math.sqrt(Math.pow((c1.indexX- c2.indexX),2) + Math.pow(c1.indexY- c2.indexY,2));
    }

    public static double chebyshev(Cell c1, Cell c2) {
        return Math.max(Math.abs(c1.indexX - c2.indexX), Math.abs(c1.indexY - c2.indexY));
    }
}
