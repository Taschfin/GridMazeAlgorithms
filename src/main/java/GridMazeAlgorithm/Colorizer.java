package GridMazeAlgorithm;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Colorizer{

    public ExecutorService executor;
    Line l;
    Line l2;

    public Colorizer(){
        this.executor = Executors.newSingleThreadExecutor();
    }


    public void drawCell(Cell c, Color fill,Color stroke,long sleep)
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                c.changeColor(fill, stroke);

                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {

                }
            }
        };
        this.executor.execute(runnable);
    }


    public  void drawPath(Button cancel, GridMaze G, LinkedList<int[]> path, Color fill, Color stroke, int sleep){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cancel.setVisible(false);
                for (int[] c : path) {
                    G.grid[c[0]][c[1]].changeColor(fill, stroke);
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };

        this.executor.execute(runnable);
    }

    public  void uiManagemant(Button generate,Button startAlgo, ComboBox heuri, ComboBox algor,Button cancel){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    algor.setDisable(false);
                    startAlgo.setDisable(false);
                    cancel.setVisible(false);
                    generate.setDisable(false);
                    if (heuri!=null) {
                        heuri.setDisable(false);
                    }
                });
            }
        };

        this.executor.execute(runnable);
    }

    public  void drawHeuristic(String heuristic,GridMaze G,Line l, Line l2,Color stroke, double strokeWidth,int sleep){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (heuristic == "Manhattan") {
                        l.setStrokeWidth(strokeWidth);
                        l.setStroke(stroke);
                        l2.setStrokeWidth(strokeWidth);
                        l2.setStroke(stroke);
                        G.canvas.getChildren().add(l2);
                    } else if (heuristic == "Euclidean") {
                        l.setStrokeWidth(strokeWidth);
                        l.setStroke(stroke);
                    }
                    G.canvas.getChildren().add(l);

                });


                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {

                }

                Platform.runLater(() -> {
                    G.canvas.getChildren().remove(l);
                    if(heuristic == "Manhattan") {
                        G.canvas.getChildren().remove(l2);
                    }
                });
            }


        };
        this.executor.execute(runnable);
    }

    public  void removeHeuristic(String heuristic,GridMaze G,Line l, Line l2,int sleep){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    G.canvas.getChildren().remove(l);
                    if(heuristic == "Manhattan") {
                        G.canvas.getChildren().remove(l2);
                    }
                });


                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {
                }
            }
        };
        this.executor.execute(runnable);
    }

}