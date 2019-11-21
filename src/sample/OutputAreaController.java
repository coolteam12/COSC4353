
package sample;

import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class OutputAreaController {

    private TextArea outputArea;

    public OutputAreaController(TextArea outputArea) {
        this.outputArea = outputArea;
        outputArea.setEditable(false);
    }

    public void setOutput(ArrayList<String> lines) {
        outputArea.clear();

        for (int i = 0; i < lines.size(); i++)
            outputArea.appendText(lines.get(i) + "\n");
    }

}
