
package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class OpenFileMenu extends ContextMenu {

    private Stage stage;
    private AppController controller;

    public OpenFileMenu(Stage stage, AppController controller) {
        this.controller = controller;

        MenuItem openFile = new MenuItem("Open file");
        openFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();

                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                    FileItem fileItem = new FileItem(selectedFile.getPath());
                    controller.getFileManager().addFile(fileItem);
                }
            }
        });

        MenuItem openFolder = new MenuItem("Open folder");
        openFolder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DirectoryChooser dirChooser = new DirectoryChooser();

                File selectedFile = dirChooser.showDialog(null);
                if (selectedFile != null) {
                    FileItem fileItem = new FileItem(selectedFile.getPath());
                    controller.getFileManager().addFile(fileItem);
                }
            }
        });

        SeparatorMenuItem separator = new SeparatorMenuItem();

        MenuItem refresh = new MenuItem("Refresh all");
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        getItems().addAll(openFile, openFolder, separator, refresh);
    }

}
