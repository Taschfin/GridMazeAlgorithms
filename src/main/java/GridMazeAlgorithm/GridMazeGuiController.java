package GridMazeAlgorithm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GridMazeGuiController{


    @FXML
    private ChoiceBox<String> DropDown;

    @FXML
    private Button backButton;

    @FXML
    private Button startAlgo;

    @FXML
    private Pane mazePane;

    private final float mazePaneWidth = 500;
    private final float mazePaneHeight = 500;

    private int cols;
    private int rows;

    GridMaze grid;

    private GridMaze gridArray;
    private String[] Algorithms = {"DFS","BFS","Dijkstra"};
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Rectangle[][] recs;

    public void createMazeView(int rows, int cols){
        int w = cols*2+1;
        int h = rows*2+1;

        recs = new Rectangle[h][w];

        for(int i = 0; i < h;i++){
            for(int j = 0; j< w;j++){
                Rectangle wall = new Rectangle(j*(mazePaneWidth/(float)w),(i)*(mazePaneWidth/(float)h),(mazePaneWidth/(float)w),(mazePaneWidth/(float)h));
                if (i%2==0){
                    wall.setFill(Color.BLACK);
                } else if(j%2==0) {
                    wall.setFill(Color.BLACK);
                }
                else {
                    wall.setFill(Color.WHITE);
                }

                wall.setStroke(Color.BLACK);

                //rectanglesInRow.add(wall);

                mazePane.getChildren().add(wall);
                recs[i][j] = wall;
            }

        }


    }


    public void generateRandomMaze()  {
        DropDown.setDisable(false);
        startAlgo.setDisable(false);
        RandomDepthFirstSearch.createMazeRDFS(grid,1,1);
        grid.freePaths();
        grid.setCell(15,15, (byte) 15);
    }

    public void solve() throws InterruptedException {
        Colorizer c = new Colorizer();
        if (DropDown.getValue()=="DFS"){
            RandomDepthFirstSearch rdf = new RandomDepthFirstSearch(c);
            rdf.rdfssolve(grid,1,1);
            }
        if (DropDown.getValue()=="BFS"){
            BreadthFirstSearch bfs = new BreadthFirstSearch(c);
            bfs.bfs(grid,1,1);
        }
    }

    public void initialize(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        createMazeView(rows,cols);

        grid = new GridMaze(cols,rows,recs);


        DropDown.getItems().addAll(Algorithms);
    }


    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MazeSize-view.fxml"));

        grid = null;

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}




