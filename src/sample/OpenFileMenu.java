
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

    // TODO: pass this through and make sure it's not null
    private Stage stage;
    private SplitPaneController controller;

    public OpenFileMenu(Stage stage, SplitPaneController controller) {
        this.controller = controller;

        MenuItem openFile = new MenuItem("Open file");
        openFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();

                // TODO: change from null to actual stage
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

        // TODO: implement this
        MenuItem refresh = new MenuItem("Refresh all");
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        getItems().addAll(openFile, openFolder, separator, refresh);
    }

}
