package GridMazeAlgorithm;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Colorizer {

    private final Executor executor;

    public Colorizer(){
        this.executor = Executors.newSingleThreadExecutor();
    }


    public void drawCell(Cell c, Color fill,Color stroke,long sleep)
    {
        this.executor.execute(()->
        {

           c.changeColor(fill,stroke);

            try
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException ex)
            {

            }
        });
    }


    public  void drawPath(GridMaze G,LinkedList<int[]> path, Color fill,Color stroke,int sleep){
        this.executor.execute(
                () ->
                {
                    for(int [] c: path)
                    {
                        G.grid[c[0]][c[1]].changeColor(fill,stroke);
                        try
                        {
                            Thread.sleep(sleep);
                        }
                        catch (InterruptedException ex)
                        {
                        }
                    }
                });
    }

}
