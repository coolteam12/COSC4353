package sample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class CompilerUtils extends ClassLoader{

    //Helps print what comes out of the programs when ran/compiled
    private static void printLines(String cmd, InputStream ins) throws Exception{
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while((line = in.readLine()) != null){
            System.out.println(cmd+ " " + line);
        }
    }
    //Runs the commands needed to compile/run
    static void runProcess(String command) throws Exception{
        Process pro = Runtime.getRuntime().exec(command);

//        Class<?> cls = Class.forName("");
//        System.out.println("Class Name: " + cls.getName());
//        System.out.println("Package Name: " + cls.getPackage());
//        Method[] methods = cls.getDeclaredMethods();
//        for(Method method : methods)  {
//            System.out.println(method.getName());
//        }
        printLines("stdout:",pro.getInputStream());
        printLines("stderr:", pro.getErrorStream());
        pro.waitFor();
        System.out.println("exitValue() " + pro.exitValue());
    }

}
