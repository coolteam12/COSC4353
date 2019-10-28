
package sample;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.fxmisc.richtext.LineNumberFactory;
import org.reactfx.Subscription;

import java.time.Duration;

public class CodeAreaController {

    private AnchorPane codePane;
    private MyCodeArea area;

    private final KeyCombination saveCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

    public CodeAreaController(AnchorPane codePane) {
        this.codePane = codePane;

        codePane.setFocusTraversable(true);
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

    public MyCodeArea getArea() {
        return area;
    }

}
