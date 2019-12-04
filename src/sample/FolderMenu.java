
package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class FolderMenu extends ContextMenu {

    private Stage stage;
    private AppController controller;
    private FileManager fileManager;

    private MenuItem newFile;
    private MenuItem newFolder;
    private SeparatorMenuItem separator1;
    private MenuItem rename;
    private MenuItem showInExplorer;
    private SeparatorMenuItem separator2;
    private MenuItem removeFromViewer;
    private MenuItem delete;

    public FolderMenu(Stage stage, AppController controller, FileManager fileManager) {
        this.controller = controller;
        this.fileManager = fileManager;

        this.newFile = new MenuItem("New file");
        newFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileManager.createFile();
            }
        });

        this.newFolder = new MenuItem("New folder");
        newFolder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileManager.createFolder();
            }
        });

        this.separator1 = new SeparatorMenuItem();

        this.rename = new MenuItem("Rename");
        rename.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });

        this.showInExplorer = new MenuItem("Show in explorer");
        showInExplorer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileManager.openExplorer();
            }
        });

        this.separator2 = new SeparatorMenuItem();

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

        getItems().addAll(newFile, newFolder, separator1, rename, showInExplorer, separator2, removeFromViewer, delete);
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

            getItems().add(6, removeFromViewer);
        } else if (!fileManager.currentItemHasNoParent()) {
            getItems().remove(removeFromViewer);
            removeFromViewer = null;
        }

        super.show(treeView, x, y);
    }

}
