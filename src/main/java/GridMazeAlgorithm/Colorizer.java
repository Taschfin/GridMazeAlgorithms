package GridMazeAlgorithm;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Colorizer{

    public ExecutorService executor;

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

    public  void uiManagemant(GridPane panel, Label start, Label target,int visited, long timeLabel, Button generate, Button startAlgo, ComboBox heuri, ComboBox algor, Button cancel,GridPane drawPanel){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(panel.getChildren().size()>2){
                        panel.getChildren().clear();
                        panel.getChildren().clear();
                    }
                    if(drawPanel!=null){
                        drawPanel.setDisable(false);
                    }
                    Label l = new Label("Execution Time:");
                    Label l2 = new Label("Visited Cells:");
                    Label runningtime = new Label(timeLabel/1000000.0 +" MS");
                    Label visitedCells = new Label(""+visited);
                    l.setFont(Font.font ("Arial", 20));
                    l2.setFont(Font.font ("Arial", 20));
                    runningtime.setFont(Font.font ("Arial", 15));
                    visitedCells.setFont(Font.font ("Arial", 15));
                    GridPane.setHalignment(l, HPos.CENTER);
                    GridPane.setHalignment(l2, HPos.CENTER);
                    GridPane.setHalignment(runningtime, HPos.CENTER);
                    GridPane.setHalignment(visitedCells, HPos.CENTER);
                    panel.add(l,0,0);
                    panel.add(runningtime,0,1);
                    panel.add(l2,1,0);
                    panel.add(visitedCells,1,1);
                    start.setVisible(false);
                    target.setVisible(false);
                    algor.setDisable(false);
                    startAlgo.setDisable(false);
                    cancel.setVisible(false);
                    if(generate!=null) {
                        generate.setDisable(false);
                    }
                    if (heuri!=null) {
                        heuri.setDisable(false);
                    }
                });
            }
        };

        this.executor.execute(runnable);
    }

    public  void drawHeuristic(String heuristic,GridMaze G,Line l, Line l2,Color stroke, double strokeWidth,int sleep, boolean xBigger) {

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
                    } else {
                        if (xBigger) {
                            l2.setStrokeWidth(strokeWidth);
                            l2.setStroke(Color.RED);
                            l.setStrokeWidth(strokeWidth);
                            l.setStroke(Color.YELLOW);
                        } else {
                            l.setStrokeWidth(strokeWidth);
                            l.setStroke(Color.RED);
                            l2.setStrokeWidth(strokeWidth);
                            l2.setStroke(Color.YELLOW);
                        }
                        G.canvas.getChildren().add(l2);
                    }
                    G.canvas.getChildren().add(l);

                });


                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {

                }

                Platform.runLater(() -> {
                    G.canvas.getChildren().remove(l);
                    if (heuristic != "Euclidean") {
                        G.canvas.getChildren().remove(l2);
                    }
                });
            }


        };
        this.executor.execute(runnable);
    }
}

    /*public  void removeHeuristic(String heuristic,GridMaze G,Line l, Line l2,int sleep){
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
    }*/

