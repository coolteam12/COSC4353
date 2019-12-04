
package sample;

import javafx.scene.control.SplitPane;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

public class FileExplorerController {

    private OpenFileMenu openFileMenu;
    private AnchorPane fileViewer;
    private SplitPane mainPane;
    private FileManager fileManager;

    private boolean toggleFileViewer;

    public FileExplorerController(OpenFileMenu openFileMenu, AnchorPane fileViewer, SplitPane mainPane, FileManager fileManager) {
        this.openFileMenu = openFileMenu;
        this.fileViewer = fileViewer;
        this.mainPane = mainPane;
        this.fileManager = fileManager;

        this.toggleFileViewer = true;

        fileViewer.setFocusTraversable(true);
    }

    private final KeyCombination toggleCombination = new KeyCodeCombination(KeyCode.TAB, KeyCombination.SHIFT_DOWN);

    public void openFileMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY)
            openFileMenu.show(fileViewer, event.getScreenX(), event.getScreenY());
        else if (event.getButton() == MouseButton.PRIMARY)
            openFileMenu.hide();
    }

    public void toggleFileViewer(KeyEvent event) {
        if (toggleCombination.match(event)) {
            toggleFileViewer = !toggleFileViewer;

            if (!toggleFileViewer) {
                mainPane.getItems().remove(fileViewer);
            } else {
                mainPane.getItems().add(0, fileViewer);
                mainPane.setDividerPosition(1, 0.2);
            }
        }
    }

}
