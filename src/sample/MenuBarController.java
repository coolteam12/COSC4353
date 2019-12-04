package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuBarController {

    private MyCodeArea area;
    private Label lblTest;
    private OutputAreaController outputArea;

    public MenuBarController(MyCodeArea area, Label lblTest, OutputAreaController outputArea) {
        this.area = area;
        this.lblTest = lblTest;
        this.lblTest.setText("Word Count: 0");
        this.outputArea = outputArea;
    }

    public void compileProgram(ActionEvent event) throws Exception {
        String opSystem = System.getProperty("os.name").toLowerCase();
        // mac will be "mac os x"
        String myPath = "\"";
        for (int i = 0; i < area.getCurrentPath().length(); i++) {

            if (area.getCurrentPath().charAt(i) == 's' && area.getCurrentPath().charAt(i + 1) == 'r' && area.getCurrentPath().charAt(i + 2) == 'c') {
                myPath = myPath + "src\"";
                break;
            }
            myPath = myPath + area.getCurrentPath().charAt(i);
        }
        String javaFile = "\\*.java";
        try {
            String command = ("javac \"" + area.getCurrentPath() + "\"");
            String maccommand = "";
            if (opSystem.indexOf("mac os x")>=0){
                command = ("javac " + area.getCurrentPath());
                for(int i = 0; i < command.length(); i++){
                    if(command.charAt(i)=='\\'){
                        maccommand = maccommand + ".";
                    }else{
                        maccommand = maccommand + command.charAt(i);
                    }
                }
                System.out.println("The command we are running is: " + command);
                CompilerUtils.C_UTILS.runProcess(maccommand);
//                CompilerUtils.runProcess(maccommand);
            }else {
                CompilerUtils.C_UTILS.runProcess(command);
//                CompilerUtils.runProcess(command);
            }
            lblTest.setText("Compiled Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void runProgram(ActionEvent event, AnchorPane codePane) {
        String opSystem = System.getProperty("os.name").toLowerCase();
        if(lblTest.getText() == "Compiled Successfully"){
            lblTest.setText("Running...");

            try{
                String myPath = "\"";
                for(int i = 0; i < area.getCurrentPath().length(); i++) {

                    if(area.getCurrentPath().charAt(i) == '.' && area.getCurrentPath().charAt(i+1) == 'j' && area.getCurrentPath().charAt(i+2) == 'a'){
                        break;
                    }
                    myPath = myPath + area.getCurrentPath().charAt(i);
                }

//                String command = ("java \""+area.getCurrentPath() + "\"");
                String command = ("java -verbose:class \"" +area.getCurrentPath() + "\"");
                String maccommand = "";

                if (opSystem.indexOf("mac os x")>=0){
                    command = ("java -verbose:class " + area.getCurrentPath()); // -verbose flag prints classes loaded into the JVM
                    for(int i = 0; i < command.length(); i++){
                        if(command.charAt(i)=='\\'){
                            maccommand = maccommand + ".";
                        }else{
                            maccommand = maccommand + command.charAt(i);
                        }
                    }
                    System.out.println("The command we are running is: " + maccommand);
//                    CompilerUtils.runProcess(maccommand);
                    CompilerUtils.C_UTILS.setLines(maccommand, outputArea);
//                    CompilerUtils.setLines(maccommand, outputArea);
//                    codePane.getChildren().add(outputArea.getOutputArea());

                }else {
//                    CompilerUtils.runProcess(command);

                    try {
                        CompilerUtils.C_UTILS.setLines(command, outputArea);
//                        CompilerUtils.setLines(command, outputArea);
//                        codePane.getChildren().add(outputArea.getOutputArea());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            lblTest.setText("Please successfully compile first.");
        }
    }

    public void getWordCount(ActionEvent event){

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

}
