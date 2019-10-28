package sample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CompilerUtils {

    //Helps print what comes out of the programs when ran/compiled
    public static void printLines (String cmd, InputStream ins) throws Exception{
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while((line = in.readLine()) != null){
            System.out.println(cmd+ " " + line);
        }
    }
    //Runs the commands needed to compile/run
    public static void runProcess(String command) throws Exception{
        Process pro = Runtime.getRuntime().exec(command);
        printLines("stdout:",pro.getInputStream());
        printLines("stderr:", pro.getErrorStream());
        pro.waitFor();
        System.out.println("exitValue() " + pro.exitValue());
    }

}
