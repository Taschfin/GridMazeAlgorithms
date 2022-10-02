package GridMazeAlgorithm;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;

public class Cell implements Comparable<Cell>{



    public enum typeOfField {FreeField, Wall,Root,Target};

    public typeOfField field;

    public Rectangle rec;
    public double distance;
    public double x;
    public double y;

    public double width;
    public double height;

    public int indexX;
    public int indexY;

    public boolean visitable = false;

    public Color stroke;
    public typeOfField t;

    public double fScore;
    public Cell prev;
    Color fill;
    Color Stroke;

    public Cell(int indexX, int indexY, double x,double y, double width, double height, Color fill, Color stroke,Cell prev, typeOfField t){
        if(t == typeOfField.FreeField || t == typeOfField.Target){
            this.visitable = true;
        }
        this.t =t;
        this.stroke = stroke;
        this.field = t;
        this.distance = Double.POSITIVE_INFINITY;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.indexX = indexX;
        this.indexY = indexY;
        this.prev = prev;
        Rectangle r = new Rectangle(x,y,width,height);
        r.setFill(fill);
        r.setStroke(stroke);
        this.rec=r;
    }

    public void changeVisitable(boolean visitable){
        this.visitable = visitable;
    }

    public void changeColor(Color fill, Color stroke){
        this.rec.setFill(fill);
        this.rec.setStroke(stroke);
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    public void updateDistance(){
        this.distance = prev.distance+1;
    }

    public void changeTypeOfField(typeOfField t){
        this.field = t;
    }

    public void changePrev(Cell previous){
        this.prev = previous;
    }

    public boolean isTarget(){
       return this.field == typeOfField.Target;
    }

    public void setFScore(double fScore){
        this.fScore = fScore;
    }


    public LinkedList<int[]> pathToRoot(){
        LinkedList<int[]> path =  new LinkedList<int[]>();

        int[] coordinates = {indexX,indexY};
        path.add(coordinates);

        Cell temp = this.prev;
        while(temp != null){
            int[] tempCoordinates = {temp.indexY,temp.indexX};
             path.addFirst(tempCoordinates);
             temp = temp.prev;
        }

        return path;
    }

    public Cell cloneCell(){
        Cell c = new Cell(indexX,indexY,x,y,width, height, fill,stroke,prev, t);
        return c;
    }


    @Override
    public int compareTo(Cell o) {
        if(this.fScore>o.fScore){
            return 1;
        }
        return -1;
    }
}
