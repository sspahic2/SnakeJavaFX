package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerStart implements Initializable {
    private final Stage primaryStage;

    public ControllerStart(Stage stage) {
        primaryStage = stage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void startAction(ActionEvent action) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/snake.fxml"));
        loader.setController(new ControllerSnake(primaryStage));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.close();
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.setTitle("Snake");
        primaryStage.show();
    }

    public void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) primaryStage.getScene().getWindow();
        stage.close();
    }
}
