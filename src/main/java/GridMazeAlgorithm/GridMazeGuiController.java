package GridMazeAlgorithm;

import GridMazeAlgorithm.Algorithms.AStar;
import GridMazeAlgorithm.Algorithms.BreadthFirstSearch;
import GridMazeAlgorithm.Algorithms.Dijkstra;
import GridMazeAlgorithm.Algorithms.RandomDepthFirstSearch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class GridMazeGuiController{

    @FXML
    private Button backButton;

    @FXML
    private Button startAlgo;

    @FXML
    private Pane mazePane;

    @FXML
    private ComboBox combo;

    private double mazePaneWidth;
    private double mazePaneHeight;

    private int cols;
    private int rows;

    GridMaze grid;

    private GridMaze gridArray;
    private String[] Algorithms = {"DFS","BFS","Dijkstra","A-Star"};
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Colorizer colorizer;

    public void generateRandomMaze()  {
        combo.setDisable(false);
        startAlgo.setDisable(false);
        RandomDepthFirstSearch.createMazeRDFS(grid,1,1);
        grid.freePaths();



    }

    public void solve() {
        startAlgo.setDisable(true);
        Cell targetFound;


        if (combo.getValue()=="DFS"){
            grid.freePaths();
            grid.revizualize();
            RandomDepthFirstSearch rdf = new RandomDepthFirstSearch(this.colorizer,startAlgo);
            rdf.rdfssolve(grid,1,1);
            }
        if (combo.getValue()=="BFS"){
            grid.freePaths();
            grid.revizualize();
            BreadthFirstSearch bfs = new BreadthFirstSearch(this.colorizer);
            targetFound=bfs.bfs(grid,1,1);
            colorizer.drawPath(startAlgo,grid,targetFound.pathToRoot(), Color.RED,Color.RED,10);
        }
        if (combo.getValue()=="Dijkstra"){
            grid.freePaths();
            grid.revizualize();
            Dijkstra dij = new Dijkstra(this.colorizer,grid);
            targetFound=dij.dijkstraAlgorithm(1,1,21,21);
            //dij.testDijkstra();
            colorizer.drawPath(startAlgo,grid,targetFound.pathToRoot(), Color.BLUE,Color.BLUE,10);
        }
        if (combo.getValue()=="A-Star"){
            grid.freePaths();
            grid.revizualize();
            AStar k  =new AStar(this.colorizer,grid,1,1,21,21);
            targetFound=k.aStarAlgorithm();
            colorizer.drawPath(startAlgo,grid,targetFound.pathToRoot(), Color.RED,Color.RED,10);
        }
    }

    public void initialize(Stage s,int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.stage = s;

        mazePaneWidth = mazePane.getWidth();
        mazePaneHeight = mazePane.getHeight();


        grid = new GridMaze(cols,rows,mazePane);
        grid.gridClone = grid.grid.clone();


        grid.changeCellType(21,21, Cell.typeOfField.Target);
        this.colorizer = new Colorizer();
        combo.getItems().addAll(Algorithms);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent e){
                try {
                    closeWindow();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MazeSize-view.fxml"));

        grid = null;
        this.colorizer.executor.shutdownNow();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void closeWindow() throws IOException{
        this.colorizer.executor.shutdownNow();
        stage.close();
    }

}




