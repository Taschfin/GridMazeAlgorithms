package GridMazeAlgorithm.Algorithms;

import GridMazeAlgorithm.Cell;
import GridMazeAlgorithm.Colorizer;
import GridMazeAlgorithm.GridMaze;
import javafx.scene.control.Button;
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
        target = g.grid[indexYtar][indexYtar];
    }


    public Cell aStarAlgorithm() {
        this.G.grid[indexY][indexX].setDistance(0);
        this.open.offer(this.G.grid[indexY][indexX]);

        Cell target = G.grid[indexYtar][indexXtar];

        double transToX = target.x + target.width / 2.0;
        double transToY = target.y + target.height / 2.0;


        while (!open.isEmpty()) {

            Cell temp = open.poll();

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
            }

            this.colorizer.drawHeuristic(heuristic,G,l,l2,Color.YELLOW, 5,30);

            closed.add(temp);
            expand(temp,heuristic);
            if (temp.isTarget()) {
                return temp;
            }
        }

        return null;
    }


    public void expand(Cell c,String heuristic) {
        this.colorizer.drawCell(c, Color.BLUE,Color.BLUE,10);
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
                f = dis + Metriken.manhatten(neighbour, target);
            } else if (heuristic == "Euclidean") {
                f = dis + Metriken.euclidean(neighbour, target);
            }
            else {
                f=0;
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
