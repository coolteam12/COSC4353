
package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class AppController {

    //TODO: when the pane is hidden, expand the size of the textarea to full
    // TODO: find a way to pass stage through
    private Stage stage;

    private FileExplorerController feController;
    private CodeAreaController caController;
    @FXML
    private SplitPane mainPane;
    @FXML
    private AnchorPane fileViewer;
    @FXML
    private TreeView filesList;
    @FXML
    private AnchorPane codePane;

    private FileManager fileManager;

    private MenuBarController mbController;
    @FXML
    private Label lblTest;
    @FXML
    private Button btnCompile;
    @FXML
    private Button btnRun;
    @FXML
    private Button btnWordCount;

    private final KeyCombination saveCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

    private OutputAreaController outController;
    @FXML
    private TextArea outputArea;

    @FXML
    public void initialize() {
        // TODO: Resize lock
        // TODO: force cursor to default, not resize
//        fileViewer.minWidthProperty().bind(mainPane.widthProperty().multiply(0.2));
//        fileViewer.maxWidthProperty().bind(mainPane.widthProperty().multiply(0.2));

        MyCodeArea area = new MyCodeArea();
        this.caController = new CodeAreaController(codePane, area);
        this.outController = new OutputAreaController(outputArea);
        this.mbController = new MenuBarController(caController.getArea(), lblTest, outController);

        OpenFileMenu openFileMenu = new OpenFileMenu(stage, this);
        this.fileManager = new FileManager(stage, filesList, this);
        this.feController = new FileExplorerController(openFileMenu, fileViewer, mainPane, fileManager);
    }

    @FXML
    private void openFileMenu(MouseEvent event) {
        feController.openFileMenu(event);
    }

    @FXML
    public void toggleFileViewer(KeyEvent event) {
        feController.toggleFileViewer(event);
    }

    // TODO: tab 4 spaces
    public void openCode(String path) {
        this.caController.openCode(path, this.caController.getArea());
    }

    //When the button "Word Count" is pressed, this function is called to do the calculations
    @FXML
    private void getWordCount(ActionEvent event){
        mbController.getWordCount(event);
    }

    @FXML
    public void runProgram(ActionEvent event) {
        mbController.runProgram(event, codePane);
    }

    @FXML
    public void compileProgram(ActionEvent event) throws Exception {
        mbController.compileProgram(event);
    }

    public FileManager getFileManager() {
        return fileManager;
    }

}



