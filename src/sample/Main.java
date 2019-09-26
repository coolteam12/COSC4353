package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // TODO: save!
    // TODO: no rightclicking in the code pane
    // TODO: height of tree will stay small so you can right click the individual items and gert omre options
    // TODO: extra space at bottom always for the pane
    // TODO: code viewer
    // TODO: right click on items
    // TODO: image viewer/pdf support
    // TODO: save the treeview
    // TODO: resize panel hasve to keep consistent size
    // TODO: new file tab
    // TODO: tab support
    // TODO: Save as

    // TODO: opening documents folder lags out, fix it


    public static final int WIDTH = 1440;
    public static final int HEIGHT = 900;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/resources/fontstyle.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
