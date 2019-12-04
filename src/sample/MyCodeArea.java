package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MyCodeArea extends CodeArea {

    private static final String KEYWORD_PATTERN = "\\b(if|else|for|while)\\b";
    private static final String OPERATOR_PATTERN = "(!=|==|!|-|/|\\+|\\||%%|\\|\\||&&|\\*|\\^|&|\\+=|-=|\\*=|/=)";
    private static final String STRING_PATTERN = "\"(.*?)\"";
    private static int WORD_COUNT = 0;
    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")" + "|(?<OPERATOR>" + OPERATOR_PATTERN + ")" + "|(?<STRING>" + STRING_PATTERN + ")"
    );

    public static final String BOILER_PLATE = String.join("\n", new String[] {
            "package com.example;",
            "",
            "import java.util.*;",
            "",
            "public class BoilerPlate{",
            "",
            "    public static void main(String[] args) {",
            "        System.out.println(\"HelloWorld\");",
            "        }",
            "    }",
            "",
            "}"
    });

    private String currentPath;
    boolean changesMade = false;
    ArrayList<String> contents;

    MyCodeArea() {
        contents = new ArrayList<String>();

    }

    public String getCurrentPath(){return this.currentPath;}

    void openFile(String path) {
        if (currentPath != null && currentPath.equals(path))
            return;
        this.currentPath = path;
        this.changesMade = false;
        clear();

        try {
            contents.clear();

            Scanner keyb = new Scanner(new File(path));
            while (keyb.hasNextLine())
                contents.add(keyb.nextLine() + "\n");

            for (String i : contents){
                appendText(i);
            }

        } catch (IOException e) {
            System.out.println("Could not find file at CodeArea");
            e.printStackTrace();
        }

    }

    void saveToFile() {

        try {
            Writer fileWriter = new FileWriter(currentPath, false);
            fileWriter.write(getText());

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass = null;
            if(matcher.group("KEYWORD") != null){
                styleClass = "keyword";
            }
            if (matcher.group("OPERATOR") != null) {
                styleClass = "operator";
            }
            if (matcher.group("STRING") != null) {
                styleClass = "string";
            }
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

}
