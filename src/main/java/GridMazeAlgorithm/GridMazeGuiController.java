package GridMazeAlgorithm;

import GridMazeAlgorithm.Algorithms.AStar;
import GridMazeAlgorithm.Algorithms.BreadthFirstSearch;
import GridMazeAlgorithm.Algorithms.Dijkstra;
import GridMazeAlgorithm.Algorithms.RandomDepthFirstSearch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
    private String[] Algorithms = {"DFS","BFS","Dijkstra","A-Star"};
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
        startAlgo.setDisable(true);
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
            dij.dijkstraAlgorithm(19,19,41,41);
            //dij.testDijkstra();
        }
        if (DropDown.getValue()=="A-Star"){
            grid.freePaths();
            grid.revizualize();
            AStar k  =new AStar(c,grid,1,1,41,41);
            k.aStarAlgorithm();
        }
        startAlgo.setDisable(false);
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




