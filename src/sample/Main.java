package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int WIDTH = 1440;
    public static final int HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/resources/fontstyle.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/resources/stylesheets/tool-bar.css").toExternalForm());


        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
