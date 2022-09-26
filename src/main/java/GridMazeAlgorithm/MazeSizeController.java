package GridMazeAlgorithm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class MazeSizeController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private TextField columns;

    @FXML
    private TextField rows;

    @FXML
    private Button btn;

    @FXML
    private Label text;



    public void switchToScene2(ActionEvent event) throws IOException {
        int amountOfRows = Integer.parseInt(columns.getText());
        int amountOfCols = Integer.parseInt(rows.getText());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GridMaze-view.fxml"));

        root = loader.load();


        GridMazeGuiController scene2Controller = loader.getController();
        scene2Controller.initialize(amountOfRows,amountOfCols);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
