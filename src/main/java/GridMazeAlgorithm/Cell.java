package GridMazeAlgorithm;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;

public class Cell {

    enum typeOfField {FreeField, Wall,Root,Target};

    typeOfField field;

    Rectangle rec;
    double distance;
    double x;
    double y;

    int indexX;
    int indexY;

    boolean visitable = false;
    Cell prev;
    Color fill;
    Color Stroke;

    public Cell(int indexX, int indexY, double x,double y, double width, double height, Color fill, Color stroke,Cell prev, typeOfField t){
        if(t == typeOfField.FreeField || t == typeOfField.Target){
            this.visitable = true;
        }

        this.field = t;
        this.distance = distance;
        this.x = x;
        this.y = y;
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
}
