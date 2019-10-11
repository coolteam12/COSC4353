// $Id$
package sample;

import javax.lang.model.SourceVersion;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.Set;

/*
A CompilingClassLoader compiles your Java source on-the-fly. It
checks for nonexistent .class files, or .class files that are older
than their corresponding source code.
*/
public class CompilingClassLoader implements JavaCompiler {
    static boolean createTestClass(String TESTFILE) throws IOException {
        FileOutputStream fos = new FileOutputStream(TESTFILE + ".java");
        PrintStream ps = new PrintStream(fos);
        ps.println("public class " + TESTFILE + "{");
        ps.println("public static void main(String[] args) {\n");
        ps.println("System.out.println(System.getProperty(\"sun.boot.library.path\"));\n");
        ps.println("}}\n");
        ps.close();
        fos.close();
        javax.tools.JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        String javacOpts[] = {TESTFILE + ".java"};
        if (javac.run(null, null, null,  javacOpts) != 0) {
            throw new RuntimeException("compilation of " + TESTFILE + ".java Failed");
        }
        else{return true;}
    }

    @Override
    public int isSupportedOption(String option) {
        return 0;
    }

    @Override
    public int run(InputStream in, OutputStream out, OutputStream err, String... arguments) {
        return 0;
    }

    @Override
    public Set<SourceVersion> getSourceVersions() {
        return null;
    }
}