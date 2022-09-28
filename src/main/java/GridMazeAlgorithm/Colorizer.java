package GridMazeAlgorithm;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Colorizer {

    private final Executor executor;

    public Colorizer(){
        this.executor = Executors.newSingleThreadExecutor();
    }


    public void drawTile(Rectangle r, Color fill,Color stroke,long sleep)
    {
        this.executor.execute(()->
        {

           r.setFill(fill);
           r.setStroke(stroke);

            try
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException ex)
            {

            }
        });
    }

}
