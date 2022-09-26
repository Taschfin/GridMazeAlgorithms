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

public class GridMazeGuiController{


    @FXML
    private ChoiceBox<String> DropDown;

    @FXML
    private Button backButton;

    @FXML
    private Pane mazePane;

    private final float mazePaneWidth = 500;
    private final float mazePaneHeight = 500;

    private GridMaze gridArray;
    private String[] Algorithms = {"DFS","Dijkstra"};
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ArrayList<ArrayList> recs;

    public void createMazeView(int rows, int cols){
        recs = new  ArrayList<ArrayList>();


        for(int i = 0; i< rows;i++){
            ArrayList rectanglesInRow = new ArrayList<>();
            for(int j = 0; j<cols;j++){
                Rectangle r = new Rectangle(j*(mazePaneWidth/(float)cols),i*(mazePaneWidth/(float)rows),(mazePaneWidth/(float)cols),(mazePaneWidth/(float)rows));
                rectanglesInRow.add(r);
                rectanglesInRow.add(true);
                r.setStroke();
                r.setFill(Color.BLUE);
                Line ln = new Line((j)*(mazePaneWidth/(float)cols),i*(mazePaneWidth/(float)rows),(j)*(mazePaneWidth/(float)cols),(i+1)*(mazePaneWidth/(float)rows));
                Line ln2 = new Line(j*(mazePaneWidth/(float)cols),(i)*(mazePaneWidth/(float)rows),(j+1)*(mazePaneWidth/(float)cols),(i)*(mazePaneWidth/(float)rows));


                mazePane.getChildren().add(ln);
                mazePane.getChildren().add(ln2);
                mazePane.getChildren().add(r);
            }
            recs.add(rectanglesInRow);
        }

    }


    public void generateRandomMaze(){

    }

    public void initialize(int rows, int cols){

        createMazeView(rows,cols);

        GridMazeRectnagles k = new GridMazeRectnagles(recs);



        DropDown.getItems().addAll(Algorithms);
    }


    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MazeSize-view.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}




