
package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Popup;
import javafx.scene.control.Label;
import javafx.scene.text.Text;



import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.reactfx.Subscription;

import javax.tools.ToolProvider;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Button btnCompile;
    @FXML
    private Button btnRun;
    @FXML
    private Button btnWordCount;

    @FXML
    private Label lblTest;

    @FXML
    public void initialize() {
        // TODO: Resize lock
        fileViewer.minWidthProperty().bind(mainPane.widthProperty().multiply(0.2));
        fileViewer.maxWidthProperty().bind(mainPane.widthProperty().multiply(0.2));

        this.toggleFileViewer = true;
        // TODO: force cursor to default, not resize


        this.openFileMenu = new OpenFileMenu(stage, this);

        this.fileManager = new FileManager(stage, filesList, this);

        lblTest.setText("Word Count: 0");
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

//            String toCount = area.getText();
//            System.out.println(toCount);
//            String[] wordArr = toCount.split(" ");
//            System.out.println(wordArr.length);

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

    //When the button "Word Count" is pressed, this function is called to do the calculations
    @FXML
    private void getWordCount(ActionEvent event){

        //As long as the text area is filled, it will run correctly
        if(area!= null){

            String text = area.getText();
            int forCount = 0, whileCount = 0, ifCount = 0, elseCount = 0;
            Pattern forPattern = Pattern.compile("for");
            Pattern whilePattern = Pattern.compile("while");
            Pattern ifPattern = Pattern.compile("if");
            Pattern elsePattern = Pattern.compile("else");
            Matcher forMatcher = forPattern.matcher(text);
            Matcher whileMatcher = whilePattern.matcher(text);
            Matcher ifMatcher = ifPattern.matcher(text);
            Matcher elseMatcher = elsePattern.matcher(text);

            //Each line of the code is read as an array
            while(forMatcher.find()){forCount++;}
            while(whileMatcher.find()){whileCount++;}
            while(ifMatcher.find()){ifCount++;}
            while(elseMatcher.find()){elseCount++;}

            String message = ("Amount of for: " + forCount + " Amount of while: " + whileCount + " Amount of if: " + ifCount + " Amount of else: " + elseCount);
            lblTest.setText(message);
            //Displays the word count as a PopupBox
            //btnWordCount.setOnAction(e-> PopupBox.display("Word Counter", message));
        }else {
            //btnWordCount.setOnAction(e -> PopupBox.display("Word Counter", "Please open up a file to use the Word Counter."));
        }
    }
    @FXML
    public void runProgram(ActionEvent event) {

    }
    @FXML
    public void compileProgram(ActionEvent event) throws Exception {
        String text = area.getText();
        String[] lineArray = text.split("\n");
        String className="";
        for(int i = 0; i < lineArray.length-1;i++) {
            String[] words = lineArray[i].split("\\s+");
            for (int j = 0; j < words.length; j++) {
                if (words[j].equals("class")) {
                    className = words[j + 1];
                    break;
                }
            }
            if (!className.isEmpty()) {
                break;
            }
        }

        //Call the JavaCompiler Code here
        CompilingClassLoader hello = new CompilingClassLoader();
        lblTest.setText("Compiled Unsuccessfully");
        hello.compileClass(className, text);
        lblTest.setText("Compiled Successfully");
}


}



