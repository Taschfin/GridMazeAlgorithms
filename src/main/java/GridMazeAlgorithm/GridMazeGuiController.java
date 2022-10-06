package GridMazeAlgorithm;

import GridMazeAlgorithm.Algorithms.AStar;
import GridMazeAlgorithm.Algorithms.BreadthFirstSearch;
import GridMazeAlgorithm.Algorithms.Dijkstra;
import GridMazeAlgorithm.Algorithms.RandomDepthFirstSearch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class GridMazeGuiController{

    @FXML
    Label startLabel;

    @FXML
    Label targetLabel;

    @FXML
    Label requirement;

    @FXML
    Ellipse startPoint;
    @FXML
    Ellipse targetPoint;

    @FXML
    private Button generationButton;

    @FXML
    private Button startAlgo;

    @FXML
    private Button cancelBtn;

    @FXML
    private GridPane points;

    @FXML
    private Pane mazePane;

    @FXML
    private ComboBox combo;

    @FXML
    private ComboBox heuristicSelection;


    private int cols;
    private int rows;

    GridMaze grid;

    private String[] Algorithms = {"RDFS","BFS","Dijkstra","A*-Algorithm"};

    private String[] Heuristics = {"Manhattan","Euclidean","Chebyshev"};
    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean generatedMaze=false;

    private Colorizer colorizer;

    Cell startCell;
    Cell targetCell;
    boolean selectedStart;
    boolean selectedTarget;
    double radius;

    int startPointX;
    int startPointY;
    int targetPointX;
    int targetPointY;

    public void cancelPressed(){
        cancelBtn.setVisible(false);
        colorizer.executor.shutdownNow();
        colorizer = new Colorizer();
        grid.freePaths();
        grid.revizualize(false);
        combo.setDisable(false);
        generationButton.setDisable(false);
        heuristicSelection.setDisable(false);
        startAlgo.setDisable(false);
    }

    public void generateRandomMaze()  {
        points.setVisible(true);
        if(generatedMaze){
            mazePane.getChildren().remove(startPoint);
            mazePane.getChildren().remove(targetPoint);
            startPoint.setRadiusX(radius);
            startPoint.setRadiusY(radius);
            targetPoint.setRadiusX(radius);
            targetPoint.setRadiusY(radius);
            startPoint.setStrokeWidth(5);
            targetPoint.setStrokeWidth(5);
            points.getChildren().clear();
            if(!points.getChildren().contains(startPoint)) {
                points.add(startPoint, 0, 0);
            }
            if(!points.getChildren().contains(targetPoint)) {
                points.add(targetPoint, 1, 0);
            }

            Label startLabel = new Label("Start");
            Label targetLabel = new Label("Target");
            startLabel.setFont(Font.font ("Arial", 15));
            targetLabel.setFont(Font.font ("Arial", 15));
            GridPane.setHalignment(startLabel, HPos.CENTER);
            GridPane.setHalignment(targetLabel, HPos.CENTER);
            points.add(startLabel,0,1);
            points.add(targetLabel,1,1);
            mazePane.getChildren().clear();
            this.grid = new GridMaze(cols,rows,mazePane,false);
            RandomDepthFirstSearch.createMazeRDFS(grid,1,1);
            grid.freePaths();
            return;
        }

        if (combo.getSelectionModel().getSelectedItem() != null) {
            startAlgo.setDisable(true);
            if(heuristicSelection.getSelectionModel().getSelectedItem()==null && combo.getSelectionModel().getSelectedItem().toString()== "A*-Algorithm"){
                RandomDepthFirstSearch.createMazeRDFS(grid,1,1);
                grid.freePaths();
                generatedMaze= true;
                return;
            }
        }

        RandomDepthFirstSearch.createMazeRDFS(grid,1,1);

        grid.freePaths();
        generatedMaze= true;
        combo.setDisable(false);

        if (combo.getSelectionModel().getSelectedItem() == null) {
            startAlgo.setDisable(true);
            return;
        }

        startAlgo.setDisable(false);
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
        if(!(mazePane.getChildren().contains(startPoint)&&mazePane.getChildren().contains(targetPoint))){
            requirement.setVisible(true);
            return;
        }
        requirement.setVisible(false);
        startAlgo.setDisable(true);
        cancelBtn.setVisible(true);
        generationButton.setDisable(true);
        grid.freePaths();
        grid.revizualize(false);
        points.getChildren().clear();

        Cell targetFound;
        long startTime = System.nanoTime();
        long stopTime = 0;
        int visited = 0;
        if (combo.getValue()=="RDFS"){
            combo.setDisable(true);
            RandomDepthFirstSearch rdf = new RandomDepthFirstSearch(cancelBtn,this.colorizer);
            stopTime = System.nanoTime();
            rdf.rdfssolve(grid,startCell.indexY,startCell.indexX);
            visited = rdf.visitedCells;
            if(rdf.target!=null) {
                colorizer.drawPath(cancelBtn,grid,rdf.target.pathToRoot(), Color.RED,Color.RED,10);
            }
            }
        else if (combo.getValue()=="BFS"){
            combo.setDisable(true);
            BreadthFirstSearch bfs = new BreadthFirstSearch(this.colorizer);
            targetFound=bfs.bfs(grid,startCell.indexX,startCell.indexY);
            stopTime = System.nanoTime();
            visited = bfs.visitedCells;
            colorizer.drawPath(cancelBtn,grid,targetFound.pathToRoot(), Color.RED,Color.RED,10);
        }
        else if (combo.getValue()=="Dijkstra"){
            combo.setDisable(true);
            Dijkstra dij = new Dijkstra(this.colorizer,grid);
            targetFound=dij.dijkstraAlgorithm(startCell.indexX,startCell.indexY);
            stopTime = System.nanoTime();
            visited = dij.visitedCells;
            colorizer.drawPath(cancelBtn,grid,targetFound.pathToRoot(), Color.BLUE,Color.BLUE,10);
        }
        if (combo.getValue()=="A*-Algorithm"){
            String heuristic = this.heuristicSelection.getSelectionModel().getSelectedItem().toString();
            combo.setDisable(true);
            heuristicSelection.setDisable(true);
            AStar alg  =new AStar(heuristic,this.colorizer,grid,startCell.indexY,startCell.indexX, targetCell.indexY, targetCell.indexX);
            targetFound=alg.aStarAlgorithm();
            stopTime = System.nanoTime();
            colorizer.drawPath(cancelBtn,grid,targetFound.pathToRoot(), Color.RED,Color.RED,10);
            colorizer.uiManagemant(points,startLabel,targetLabel,alg.visitedCells, stopTime-startTime,generationButton,startAlgo,heuristicSelection, combo,cancelBtn,null);
        }
        else{
            colorizer.uiManagemant(points,startLabel,targetLabel,visited,stopTime-startTime,generationButton,startAlgo,null, combo,cancelBtn,null);
        }
    }

    public void initialize(Stage s,int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.stage = s;

        this.grid = new GridMaze(cols,rows,mazePane,false);

        this.colorizer = new Colorizer();
        this.combo.getItems().addAll(Algorithms);
        this.heuristicSelection.getItems().addAll(this.Heuristics);


        radius = startPoint.getRadiusX();
        points.getStyleClass().add("points");

        selection();
        selectionHeuristic();
        startPoint.setOnMousePressed(event->pressed(startPoint,true));
        targetPoint.setOnMousePressed(event -> pressed(targetPoint,false));

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

    public void pressed(Ellipse point,boolean isStartpoint){
        if(isStartpoint) {
            /*Color m = new Color(1,0,0,1);
            targetPoint.setFill(m);
            Color c = new Color(0.21,1,0,0.6);
            point.setFill(c);*/
            mazePane.setOnMousePressed(event -> pressedCanavas(event, point,true));
        }
        else {
            /*Color m = new Color(0.21,1,0,1);
            startPoint.setFill(m);
            Color c = new Color(1,0,0,0.6);
            point.setFill(c);*/
            mazePane.setOnMousePressed(event -> pressedCanavas(event, point,false));
        }
    }


    public void pressedCanavas(MouseEvent event,Ellipse point,boolean which) {
        int y = (int) (event.getY() / grid.heightOfOneCell);
        int x = (int) (event.getX() / grid.widthOfOneCell);
        Cell m=grid.grid[y][x];

        if(m.field== Cell.typeOfField.Wall || m.field== Cell.typeOfField.Unremovable || m==startCell || m ==targetCell){
            return;
        }


        if(which) {
            startPointX = x;
            startPointY = y;
            if(startCell!=null) {
                startCell.changeTypeOfField(Cell.typeOfField.FreeField);
            }
            startCell = grid.grid[y][x];
            mazePane.setOnMousePressed(null);
        }
        else {
            targetPointX = x;
            targetPointY = y;
            if(targetCell!=null) {
                targetCell.changeTypeOfField(Cell.typeOfField.FreeField);
            }
            targetCell = grid.grid[y][x];
            targetCell.changeTypeOfField(Cell.typeOfField.Target);
            mazePane.setOnMousePressed(null);
        }
        if(!mazePane.getChildren().contains(point)) {
            mazePane.getChildren().add(point);
        }
        point.setTranslateX(-0.5);
        point.setTranslateY(-0.5);
        point.setStrokeWidth(grid.widthOfOneCell*0.1);
        point.setLayoutX(m.x+grid.widthOfOneCell/2);
        point.setLayoutY(m.y+grid.heightOfOneCell/2);
        point.setRadiusX(grid.widthOfOneCell-grid.widthOfOneCell*0.6);
        point.setRadiusY(grid.heightOfOneCell-grid.heightOfOneCell*0.6);
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




