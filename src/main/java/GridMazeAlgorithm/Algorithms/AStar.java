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
    Button start;


    public AStar(Colorizer c, GridMaze g, int indexY, int indexX, int indexYtar, int indexXtar) {
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

        double transToX = target.x + target.width/2.0;
        double transToY = target.x + target.height/2.0;


        while (!open.isEmpty()) {

            Cell temp = open.poll();

            double transFromX = temp.x + temp.width/2.0;
            double transFromY = temp.y + temp.height/2.0;

            Line l = new Line(transFromX,transFromY,transFromX,transToY);
            Line l2 = new Line(transFromX,transToY,transToX,transToY);

            this.colorizer.drawHeuristic(G,l,l2,Color.YELLOW, 5,20);



            closed.add(temp);
            expand(temp);
            if (temp.isTarget()) {
                return temp;
            }
            this.colorizer.removeHeuristic(G,l,l2,5);
        }

        return null;
    }

    public void expand(Cell c) {
        this.colorizer.drawCell(c, Color.BLUE,Color.BLUE,10);
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

            double f = dis + Metriken.manhatten(neighbour, target);

            if (!open.contains(neighbour)) {
                open.add(neighbour);
            }
            else {
                open.remove(neighbour);
                open.add(neighbour);
            }

        }
    }

    public void test() {
        for (Cell[] c : G.grid) {
            for (Cell k : c) {
                if (k.field == Cell.typeOfField.FreeField || k.field == Cell.typeOfField.Target) {
                    System.out.print(k.distance);
                } else {
                    System.out.print(" +");
                }
            }
            System.out.println();
        }
    }

    public void printQueue(){
        System.out.println(open.size());
        for(Cell c: open){
            System.out.println(c.distance);
        }
    }
}
