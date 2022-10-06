package GridMazeAlgorithm;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class GridMazeGui extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Font Lato = Font.loadFont("file:resources/fonts/Lato-Light.ttf", 100);
        FXMLLoader fxmlLoader = new FXMLLoader(GridMazeGui.class.getResource("MazeSize-view.fxml"));

        Image icon = new Image(getClass().getResource("/images/GMA-icon.png").toExternalForm());
        stage.getIcons().add(icon);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Grid Maze Algorithms");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}