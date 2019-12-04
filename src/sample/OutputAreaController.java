
package sample;

import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class OutputAreaController {

    private TextArea outputArea;

    public OutputAreaController(TextArea outputArea) {
        this.outputArea = outputArea;
        outputArea.setEditable(false);
        outputArea.toFront();
        outputArea.setViewOrder(Double.MIN_VALUE);
    }

    public void setOutput(ArrayList<String> lines) {
        outputArea.clear();

        for (int i = 0; i < lines.size(); i++) {
            outputArea.appendText(lines.get(i) + "\n");
//            System.out.println(lines.get(i));
        }

        outputArea.toFront();
        outputArea.setViewOrder(Double.MIN_VALUE);
//        System.out.println("SET VIEW ORDER");
    }

    public TextArea getOutputArea() {
        return outputArea;
    }

}
