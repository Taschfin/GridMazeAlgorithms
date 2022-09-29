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

    public void generateRandomMaze()  {
        DropDown.setDisable(false);
        startAlgo.setDisable(false);
        RandomDepthFirstSearch.createMazeRDFS(grid,1,1);
        grid.freePaths();


    }

    public void solve() throws InterruptedException {
        Colorizer c = new Colorizer();
        if (DropDown.getValue()=="DFS"){
            grid.freePaths();
            grid.revizualize();
            RandomDepthFirstSearch rdf = new RandomDepthFirstSearch(c);
            rdf.rdfssolve(grid,1,1);
            }
        if (DropDown.getValue()=="BFS"){
            grid.freePaths();
            grid.revizualize();
            BreadthFirstSearch bfs = new BreadthFirstSearch(c);
            bfs.bfs(grid,1,1);
        }
        if (DropDown.getValue()=="Dijkstra"){
            grid.freePaths();
            grid.revizualize();
            Dijkstra dij = new Dijkstra(c,grid);
            dij.dijkstraAlgorithm(1,1);
            //dij.testDijkstra();
        }

    }

    public void initialize(int rows, int cols){
        this.rows = rows;
        this.cols = cols;

        grid = new GridMaze(cols,rows,mazePane);
        grid.gridClone = grid.grid.clone();

        grid.changeCellType(41,41, Cell.typeOfField.Target);


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




