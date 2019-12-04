
package sample;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileManager {

    private TreeView treeView;
    private TreeItem root;

    private TreeItem currentItem;
    private FolderMenu folderMenu;
    private FileMenu fileMenu;

    public FileManager(Stage stage, TreeView<FileItem> treeView, AppController controller) {
        this.treeView = treeView;

        this.folderMenu = new FolderMenu(stage, controller, this);
        this.fileMenu = new FileMenu.Builder()
                .withRenameOption()
                .withSeparator()
                .withShowInExplorer(this)
                .withRemoveFromViewer(this)
                .withDelete()
                .build(stage, controller, this);
//        this.fileMenu = new FileMenu(stage, controller, this);
//        treeView.getStylesheets().add(getClass().getResource("/resources/file-manager.css").toExternalForm());

        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node node = mouseEvent.getPickResult().getIntersectedNode();

                // Close out anywhere
//                if (mouseEvent.getButton() == MouseButton.PRIMARY)
                folderMenu.hide();
                fileMenu.hide();

                // TODO: only open text files for now
                if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                    TreeCell cell;
                    if (node instanceof Text)
                        cell = (TreeCell)(((Text)node).getParent());
                    else
                        cell = (TreeCell)(node);

                    currentItem = cell.getTreeItem();
                    FileItem item = (FileItem)currentItem.getValue();

                    if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2 && item.isFile()) {
                        controller.openCode(item.getPath());
                    } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        if (item.isDirectory()) {
                            folderMenu.show(treeView, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                        } else {
                            fileMenu.show(treeView, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                        }
                    }
                }
            }
        });

        this.root = new TreeItem();
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }

    // TODO: select the file itself if it's a file
    public void openExplorer() {
        try {
            FileItem item = (FileItem)currentItem.getValue();
            Desktop.getDesktop().open(new File(item.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFromViewer() {
        currentItem.getParent().getChildren().remove(currentItem);
    }

    // TODO: have no separate prompt, new tree item that you can type in
    public void createFile() {
        String fileName = JOptionPane.showInputDialog(null, "Name of file?");
        FileItem item = (FileItem)currentItem.getValue();


        try {
            String path = item.getPath() + "\\" + fileName;
            System.out.println(path);

            File file = new File(path);
            addFile(new FileItem(path), currentItem);

            file.createNewFile();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Cannot create file");
        }
    }

    public void createFolder() {
        String fileName = JOptionPane.showInputDialog(null, "Name of folder?");
        FileItem item = (FileItem)currentItem.getValue();

        try {
            String path = item.getPath() + "\\" + fileName;
            System.out.println(path);

            new File(path).mkdirs();
            addFile(new FileItem(path), currentItem);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Cannot create folder");
        }
    }

    public void addFile(FileItem fileItem) {
        addFile(fileItem, null);
    }

    public void addFile(FileItem fileItem, TreeItem parent) {
        TreeItem<FileItem> tmp = new TreeItem<FileItem>(fileItem);

        if (parent == null)
            root.getChildren().add(tmp);
        else
            parent.getChildren().add(tmp);

        if (fileItem.isDirectory()) {
            File arr[] = fileItem.listFiles();

            for (File i : arr)
                addFile(new FileItem(i), tmp);
        }
    }

    // TODO: hide this option
    public boolean currentItemHasNoParent() {
        if (currentItem == null)
            return true;

        return currentItem.getParent() == root;
    }

}
