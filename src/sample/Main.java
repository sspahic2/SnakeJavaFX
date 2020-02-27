package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
        loader.setController(new ControllerStart(primaryStage));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.setTitle("Start");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
