package GridMazeAlgorithm;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;

public class Dijkstra {

    Colorizer colorizer;
    GridMaze G;

    int x;
    int y;

    LinkedList<Cell> Q;

    public Dijkstra(Colorizer colorizer, GridMaze g){
        this.colorizer = colorizer;
        this.G  = g;
    }

    public void initizialize(int x, int y){
        this.G.grid[y][x].setDistance(0);
        colorizer.drawCell(this.G.grid[y][x],Color.YELLOWGREEN,Color.YELLOWGREEN,5);
        //this.G.grid[y][x].changeTypeOfField(Cell.typeOfField.Root);
        this.Q = new LinkedList<Cell>();
        for (Cell[] m : G.grid) {
            for (Cell k: m){
                if(k.field == Cell.typeOfField.FreeField || k.field == Cell.typeOfField.Target){
                    //System.out.println("X : "+ k.indexX +" Y: "+ k.indexY);
                    colorizer.drawCell(k,Color.YELLOWGREEN,Color.YELLOWGREEN,3);
                    this.Q.add(k);
                }
            }
        }
    }

    public void dijkstraAlgorithm(int x, int y){
        initizialize(x,y);
        while (!this.Q.isEmpty()){
            Cell u = extractMin();
            for(Cell neighbour : G.freeAdjacentCells(u)){
                if (Q.contains(neighbour)){
                    updateDistance(u,neighbour);
                }
            }
        }
        LinkedList<int[]> p = pathFrom1to2(41,41);
        //GridMaze.printAdj(p);
        this.colorizer.drawPath(this.G,p,Color.RED,Color.RED,10);
    }

    public Cell extractMin(){
        Cell min = this.Q.get(0);
        for(Cell temp : this.Q){
            if (temp.distance < min.distance){
                min = temp;
            }
        }

        this.Q.remove(min);
        return min;
    }

    public void updateDistance(Cell c1, Cell c2){
        double dis = c1.distance +1;
        if(dis< c2.distance){
            c2.setDistance(dis);
            c2.changePrev(c1);
        }
    }

    public void testDijkstra(){
        for(Cell[] c: G.grid){
            for(Cell k : c){
                if(k.field == Cell.typeOfField.FreeField|| k.field == Cell.typeOfField.Target){
                    System.out.print(k.distance);
                }
                else {
                    System.out.print(" +");
                }
            }
            System.out.println();
        }
    }

    public LinkedList<int[]> pathFrom1to2(int s1,int s2){
        LinkedList<int[]> path =  new LinkedList<int[]>();


        int[] coordinates = {s1,s2};
        path.add(coordinates);
        Cell temp = G.grid[s1][s2].prev;

        while(temp.prev != null){
            System.out.println("X: " + temp.indexX + " Y: "+ temp.indexY);
            int[] tempCoordinates = {temp.indexY,temp.indexX};
            path.addFirst(tempCoordinates);
            temp = temp.prev;
            System.out.println(temp.prev);
        }

        return path;
    }
}
