
package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class FileMenu extends ContextMenu {

    private Stage stage;
    private AppController controller;
    private FileManager fileManager;

    private MenuItem rename;
    private SeparatorMenuItem separator;
    private MenuItem showInExplorer;
    private MenuItem removeFromViewer;
    private MenuItem delete;

    public static class Builder {

        private MenuItem rename;
        private SeparatorMenuItem separator;
        private MenuItem showInExplorer;
        private MenuItem removeFromViewer;
        private MenuItem delete;

        public Builder withRenameOption() {
            this.rename = new MenuItem("Rename");
            rename.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                }
            });

            return this;
        }

        public Builder withSeparator() {
            this.separator = new SeparatorMenuItem();

            return this;
        }

        public Builder withShowInExplorer(FileManager fileManager) {
            this.showInExplorer = new MenuItem("Show in explorer");
            showInExplorer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    fileManager.openExplorer();
                }
            });

            return this;
        }

        public Builder withRemoveFromViewer(FileManager fileManager) {
            this.removeFromViewer = new MenuItem("Remove from viewer");
            removeFromViewer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    fileManager.removeFromViewer();
                }
            });

            return this;
        }

        public Builder withDelete() {
            this.delete = new MenuItem("Delete");
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                }
            });

            return this;
        }

        public FileMenu build(Stage stage, AppController controller, FileManager fileManager) {
            FileMenu fileMenu = new FileMenu(stage, controller, fileManager);
            fileMenu.rename = this.rename;
            fileMenu.separator = this.separator;
            fileMenu.showInExplorer = this.showInExplorer;
            fileMenu.removeFromViewer = this.removeFromViewer;
            fileMenu.delete = this.delete;

            fileMenu.getItems().addAll(rename, showInExplorer, separator, removeFromViewer, delete);

            return fileMenu;
        }

    }

    public FileMenu(Stage stage, AppController controller, FileManager fileManager) {
        this.controller = controller;
        this.fileManager = fileManager;

//        this.rename = new MenuItem("Rename");
//        rename.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//            }
//        });
//
//        this.separator = new SeparatorMenuItem();
//
//        this.showInExplorer = new MenuItem("Show in explorer");
//        showInExplorer.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                fileManager.openExplorer();
//            }
//        });
//
//        this.removeFromViewer = new MenuItem("Remove from viewer");
//        removeFromViewer.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                fileManager.removeFromViewer();
//            }
//        });
//
//        this.delete = new MenuItem("Delete");
//        delete.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//            }
//        });

//        System.out.println(rename + " " + showInExplorer + " " + separator + " " + removeFromViewer + " " + delete);
//        getItems().addAll(rename, showInExplorer, separator, removeFromViewer, delete);
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

            getItems().add(3, removeFromViewer);
        } else if (!fileManager.currentItemHasNoParent()) {
            getItems().remove(removeFromViewer);
            removeFromViewer = null;
        }

        super.show(treeView, x, y);
    }

}
