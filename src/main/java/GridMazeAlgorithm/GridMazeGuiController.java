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
    private Button generationButton;

    @FXML
    private Button startAlgo;

    @FXML
    private Button cancelBtn;
    @FXML
    private Pane mazePane;

    @FXML
    private ComboBox combo;

    @FXML
    private ComboBox heuristicSelection;

    private double mazePaneWidth;
    private double mazePaneHeight;

    private int cols;
    private int rows;

    GridMaze grid;

    private GridMaze gridArray;
    private String[] Algorithms = {"DFS","BFS","Dijkstra","A*-Algorithm"};

    private String[] Heuristics = {"Manhattan","Euclidean","Fortnite"};
    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean generatedMaze=false;

    private Colorizer colorizer;


    public void cancelPressed(){
        cancelBtn.setVisible(false);
        colorizer.executor.shutdownNow();
        colorizer = new Colorizer();
        grid.freePaths();
        grid.revizualize();
        combo.setDisable(false);
        heuristicSelection.setDisable(false);
        startAlgo.setDisable(false);
    }

    public void generateRandomMaze()  {
        if(generatedMaze){
            mazePane.getChildren().clear();
            this.grid = new GridMaze(cols,rows,mazePane);
            RandomDepthFirstSearch.createMazeRDFS(grid,1,1);
            grid.freePaths();
            this.grid.changeCellType(11,11, Cell.typeOfField.Target);
            return;
        }

        if (combo.getSelectionModel().getSelectedItem() != null) {
            if(heuristicSelection.getSelectionModel().getSelectedItem()==null && combo.getSelectionModel().getSelectedItem().toString()== "A*-Algorithm"){
                RandomDepthFirstSearch.createMazeRDFS(grid,1,1);
                grid.freePaths();
                generatedMaze= true;
                return;
            }
        }
        combo.setDisable(false);
        startAlgo.setDisable(false);
        RandomDepthFirstSearch.createMazeRDFS(grid,1,1);
        grid.freePaths();
        generatedMaze= true;
    }

    public  void selection(){
        combo.setOnAction(event -> {
            String selected = this.combo.getSelectionModel().getSelectedItem().toString();
            if (selected == "A*-Algorithm"){
                heuristicSelection.setVisible(true);
                if(heuristicSelection.getSelectionModel().getSelectedItem()==null){
                    startAlgo.setDisable(true);
                } else if (!generatedMaze) {
                    startAlgo.setDisable(true);
                } else {
                    startAlgo.setDisable(false);
                }
            }
            else {
                generationButton.setDisable(false);
                if(generatedMaze) {
                    startAlgo.setDisable(false);
                    heuristicSelection.setVisible(false);
                }
                else {
                    heuristicSelection.setVisible(false);
                }
            }
        });
    }

    public  void selectionHeuristic(){
        heuristicSelection.setOnAction(event -> {
            if(generatedMaze) {
                startAlgo.setDisable(false);
            }
        });
    }

    public void solve() {
        startAlgo.setDisable(true);
        cancelBtn.setVisible(true);
        generationButton.setDisable(true);
        grid.freePaths();
        grid.revizualize();

        Cell targetFound;
        if (combo.getValue()=="DFS"){
            combo.setDisable(true);
            RandomDepthFirstSearch rdf = new RandomDepthFirstSearch(cancelBtn,this.colorizer);
            rdf.rdfssolve(grid,1,1);
            }
        else if (combo.getValue()=="BFS"){
            combo.setDisable(true);
            BreadthFirstSearch bfs = new BreadthFirstSearch(this.colorizer);
            targetFound=bfs.bfs(grid,1,1);
            colorizer.drawPath(cancelBtn,grid,targetFound.pathToRoot(), Color.RED,Color.RED,10);
        }
        else if (combo.getValue()=="Dijkstra"){
            combo.setDisable(true);
            Dijkstra dij = new Dijkstra(this.colorizer,grid);
            targetFound=dij.dijkstraAlgorithm(1,1);
            //dij.testDijkstra();
            colorizer.drawPath(cancelBtn,grid,targetFound.pathToRoot(), Color.BLUE,Color.BLUE,10);
        }
        if (combo.getValue()=="A*-Algorithm"){
            combo.setDisable(true);
            heuristicSelection.setDisable(true);
            AStar alg  =new AStar(this.heuristicSelection.getSelectionModel().getSelectedItem().toString(),this.colorizer,grid,1,1,11,11);
            targetFound=alg.aStarAlgorithm();
            colorizer.drawPath(cancelBtn,grid,targetFound.pathToRoot(), Color.RED,Color.RED,10);
            colorizer.uiManagemant(generationButton,startAlgo,heuristicSelection, combo,cancelBtn);
        }
        else{
            colorizer.uiManagemant(generationButton,startAlgo,null, combo,cancelBtn);
        }

    }

    public void initialize(Stage s,int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.stage = s;

        mazePaneWidth = mazePane.getWidth();
        mazePaneHeight = mazePane.getHeight();


        this.grid = new GridMaze(cols,rows,mazePane);


        this.grid.changeCellType(11,11, Cell.typeOfField.Target);
        this.colorizer = new Colorizer();
        this.combo.getItems().addAll(Algorithms);
        this.heuristicSelection.getItems().addAll(this.Heuristics);

        selection();
        selectionHeuristic();

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
        System.exit(0);
    }

}




