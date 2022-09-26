package GridMazeAlgorithm;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GridMazeGuiController implements Initializable {


    @FXML
    private ChoiceBox<String> DropDown;

    private String[] Algorithms = {"DFS","Dijkstra"};

    @FXML
    private Button GenerateMaze;

    @FXML
    private Button StartAlgorithm;

    @FXML
    private SplitPane split;



    public void initialize(URL arg0, ResourceBundle arg1){
        GridMaze G = new GridMaze(30,30);



        //for(int i=0; i<200;)

        DropDown.getItems().addAll(Algorithms);
    }
}
