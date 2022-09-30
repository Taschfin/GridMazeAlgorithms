module com.example.gridmazealgorithm {
    requires javafx.controls;
    requires javafx.fxml;


    opens GridMazeAlgorithm to javafx.fxml;
    exports GridMazeAlgorithm;
    exports GridMazeAlgorithm.Algorithms;
    opens GridMazeAlgorithm.Algorithms to javafx.fxml;
}