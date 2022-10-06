package GridMazeAlgorithm.Algorithms;

import GridMazeAlgorithm.Cell;
import GridMazeAlgorithm.Colorizer;
import GridMazeAlgorithm.GridMaze;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class AStar {
    GridMaze G;
    int indexX;
    int indexY;
    int indexXtar;
    int indexYtar;

    PriorityQueue<Cell> open;
    LinkedList<Cell> closed;

    Cell target;
    Colorizer colorizer;
    String heuristic;

    public int visitedCells;

    public AStar(String heuristic,Colorizer c, GridMaze g, int indexY, int indexX, int indexYtar, int indexXtar) {
        visitedCells = 0;
        this.heuristic = heuristic;
        this.G = g;
        this.colorizer = c;
        this.indexX = indexX;
        this.indexY = indexY;
        this.indexXtar = indexXtar;
        this.indexYtar = indexYtar;
        this.open = new PriorityQueue<Cell>();
        this.closed = new LinkedList<Cell>();
        target = g.grid[indexYtar][indexXtar];
    }


    public Cell aStarAlgorithm() {
        this.G.grid[indexY][indexX].setDistance(0);
        this.open.offer(this.G.grid[indexY][indexX]);

        Cell target = G.grid[indexYtar][indexXtar];

        double transToX = target.x + target.width / 2.0;
        double transToY = target.y + target.height / 2.0;


        while (!open.isEmpty()) {

            Cell temp = open.poll();

            boolean xBigger = false;

            double transFromX = temp.x + temp.width / 2.0;
            double transFromY = temp.y + temp.height / 2.0;

            Line l;
            Line l2 = null;
            if (heuristic == "Manhattan"){
                l = new Line(transFromX, transFromY, transFromX, transToY);
                l2= new Line(transFromX,transToY,transToX,transToY);
            } else if (heuristic == "Euclidean") {
                l= new Line(transFromX,transFromY,transToX,transToY);
            }
            else {
                l = new Line(transFromX, transFromY, transFromX, transToY);
                l2= new Line(transFromX,transToY,transToX,transToY);
                if (Math.abs(temp.indexX- target.indexX)>Math.abs(temp.indexY- target.indexY)){
                    xBigger = true;
                }
                else {
                    xBigger = false;
                }
            }

            this.colorizer.drawHeuristic(heuristic,G,l,l2,Color.YELLOW, 3,30,xBigger);

            closed.add(temp);
            expand(temp,heuristic);
            if (temp.isTarget()) {
                return temp;
            }
        }

        return null;
    }


    public void expand(Cell c,String heuristic) {
        this.colorizer.drawCell(c, Color.BLUE,Color.BLUE,7);
        visitedCells++;
        for (Cell neighbour : this.G.freeAdjacentCells(c)) {
            if (closed.contains(neighbour)) {
                continue;
            }

            double dis = c.distance + 1;

            if (open.contains(neighbour) && dis >= neighbour.distance) {
                continue;
            }

            neighbour.prev = c;
            neighbour.distance = dis;

            double f;
            if (heuristic == "Manhattan"){
                f = dis + Metrices.manhatten(neighbour, target);
            } else if (heuristic == "Euclidean") {
                f = dis + Metrices.euclidean(neighbour, target);
            }
            else {
                f= dis + Metrices.chebyshev(neighbour,target);
            }
            

            neighbour.fScore = f;

            if (!open.contains(neighbour)) {
                open.add(neighbour);
            }
            else {
                open.remove(neighbour);
                open.add(neighbour);
            }

        }
    }



}
