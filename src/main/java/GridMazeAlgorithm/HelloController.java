package GridMazeAlgorithm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private TextField But;

    @FXML
    private Label welcomeText;

    @FXML
    void OnFischClick(ActionEvent event) {
        Stage mainwindow = (Stage) But.getScene().getWindow();
        String title = But.getText();
        mainwindow.setTitle(title);
    }

    @FXML
    void onHelloButtonClick(ActionEvent event) {

    }

}
