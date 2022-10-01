package GridMazeAlgorithm;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Colorizer{

    private final Executor executor;
    public boolean exit=false;

    public Colorizer(){
        this.executor = Executors.newSingleThreadExecutor();
    }


    public void drawCell(Cell c, Color fill,Color stroke,long sleep)
    {
          this.executor.execute(() ->
            {

                c.changeColor(fill, stroke);

                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {

                }
            });
    }


    public  void drawPath(Button startAlgo,GridMaze G, LinkedList<int[]> path, Color fill, Color stroke, int sleep){
            this.executor.execute(
                    () ->
                    {
                        for (int[] c : path) {
                            G.grid[c[0]][c[1]].changeColor(fill, stroke);
                            try {
                                Thread.sleep(sleep);
                            } catch (InterruptedException ex) {
                            }
                        }
                        Platform.runLater(() -> {
                            startAlgo.setDisable(false);
                        });
                    });


    }

    public  void drawHeuristic(GridMaze G,Line l, Line l2,Color stroke, double strokeWidth,int sleep){
            this.executor.execute(
                    () -> {

                        Platform.runLater(() -> {
                            l.setStrokeWidth(strokeWidth);
                            l2.setStrokeWidth(strokeWidth);
                            l.setStroke(stroke);
                            l2.setStroke(stroke);
                            G.canvas.getChildren().add(l);
                            G.canvas.getChildren().add(l2);
                        });


                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException ex) {

                        }


                    }
            );
    }

    public  void removeHeuristic(GridMaze G,Line l, Line l2,int sleep){
            this.executor.execute(
                    () -> {

                        Platform.runLater(() -> {
                            G.canvas.getChildren().remove(l);
                            G.canvas.getChildren().remove(l2);
                        });


                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException ex) {

                        }

                    }
            );
    }

}
