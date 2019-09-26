
package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileMenu extends ContextMenu {

    // TODO: pass this through and make sure it's not null
    private Stage stage;
    private SplitPaneController controller;
    private FileManager fileManager;

    private MenuItem rename;
    private SeparatorMenuItem separator;
    private MenuItem showInExplorer;
    private MenuItem removeFromViewer;
    private MenuItem delete;

    public FileMenu(Stage stage, SplitPaneController controller, FileManager fileManager) {
        this.controller = controller;
        this.fileManager = fileManager;

        this.rename = new MenuItem("Rename");
        rename.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });

        this.separator = new SeparatorMenuItem();

        this.showInExplorer = new MenuItem("Show in explorer");
        showInExplorer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileManager.openExplorer();
            }
        });

        this.removeFromViewer = new MenuItem("Remove from viewer");
        removeFromViewer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileManager.removeFromViewer();
            }
        });

        this.delete = new MenuItem("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });

        getItems().addAll(rename, showInExplorer, separator, removeFromViewer, delete);
    }

    public void show(TreeView treeView, double x, double y) {
        if (fileManager.currentItemHasNoParent() && removeFromViewer == null) {
            removeFromViewer = new MenuItem("Remove from viewer");
            removeFromViewer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    fileManager.removeFromViewer();
                }
            });

            // TODO: make this not constant
            getItems().add(3, removeFromViewer);
        } else if (!fileManager.currentItemHasNoParent()) {
            getItems().remove(removeFromViewer);
            removeFromViewer = null;
        }

        super.show(treeView, x, y);
    }

}
