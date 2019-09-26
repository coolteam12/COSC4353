
package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.reactfx.Subscription;

import java.time.Duration;

public class SplitPaneController {

    //TODO: when the pane is hidden, expand the size of the textarea to full
    // TODO: find a way to pass stage through
    private Stage stage;

    @FXML
    private SplitPane mainPane;

    @FXML
    private AnchorPane fileViewer;
    private final KeyCombination toggleCombination = new KeyCodeCombination(KeyCode.TAB, KeyCombination.SHIFT_DOWN);
    private boolean toggleFileViewer;

    private OpenFileMenu openFileMenu;


    private FileManager fileManager;
    @FXML
    private TreeView filesList;

    @FXML
    private AnchorPane codePane;
    private final KeyCombination saveCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
    // TODO: implement this better
    private MyCodeArea area;

    @FXML
    public void initialize() {
        // TODO: Resize lock
        fileViewer.minWidthProperty().bind(mainPane.widthProperty().multiply(0.2));
        fileViewer.maxWidthProperty().bind(mainPane.widthProperty().multiply(0.2));

        this.toggleFileViewer = true;
        // TODO: force cursor to default, not resize


        this.openFileMenu = new OpenFileMenu(stage, this);

        this.fileManager = new FileManager(stage, filesList, this);

//        filesList.getStylesheets().add(getClass().getResource("stylesheets/files-list.css").toExternalForm());
        codePane.setFocusTraversable(true);
        fileViewer.setFocusTraversable(true);
    }

    @FXML
    private void openFileMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY)
            openFileMenu.show(fileViewer, event.getScreenX(), event.getScreenY());
        else if (event.getButton() == MouseButton.PRIMARY)
            openFileMenu.hide();
    }

    @FXML
    public void toggleFileViewer(KeyEvent event) {
        if (toggleCombination.match(event)) {
            toggleFileViewer = !toggleFileViewer;

            // TODO: hide fileViewer
            if (!toggleFileViewer) {
                mainPane.getItems().remove(fileViewer);
            } else {
                mainPane.getItems().add(0, fileViewer);
                mainPane.setDividerPosition(1, 0.2);
            }
        }
    }

    // TODO: tab 4 spaces
    public void openCode(String path) {
        if (area == null) {
            area = new MyCodeArea();
            area.replaceText(0, 0, MyCodeArea.BOILER_PLATE);
            area.setParagraphGraphicFactory(LineNumberFactory.get(area));
            area.setWrapText(true);
            area.setPrefWidth(codePane.getWidth());
            area.setPrefHeight(codePane.getHeight());
            Subscription cleanupWhenNoLongerNeedIt = area
                    .multiPlainChanges()
                    .successionEnds(Duration.ofMillis(100))
                    .subscribe(ignore -> area.setStyleSpans(0, area.computeHighlighting(area.getText())));
            // TODO: add support for other extensions
            if(path.endsWith(".java")) {
                area.replaceText(0, 0, MyCodeArea.BOILER_PLATE);
            }
            codePane.getStylesheets().add(getClass().getResource("/resources/stylesheets/syntax-highlighting.css").toExternalForm());
            codePane.getChildren().add(area);

            area.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (saveCombination.match(event))
                        area.saveToFile();
                }
            });
        }
        area.openFile(path);
    }

    public FileManager getFileManager() {
        return fileManager;
    }

}
