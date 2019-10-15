/*package sample;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

public class CompilingClassLoader implements Serializable {
    private static final long serialVersionUID = 1L;
    private String classPath = System.getProperty("java.class.path");

    public void compileClass(final String className, final String classCode) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        OutputStreamJavaFileManager<JavaFileManager> fileManager =
                new OutputStreamJavaFileManager<JavaFileManager>(
                        javaCompiler.getStandardFileManager(null, null, null), byteArrayOutputStream);

        List<JavaFileObject> fileObjects = new ArrayList<JavaFileObject>();
        fileObjects.add(new JavaSourceFromString(className, classCode));

        List<String> options = Arrays.asList("-classpath", this.classPath);
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        if (!javaCompiler.getTask(null, fileManager, diagnostics, options, null, fileObjects).call()) {
            StringBuilder errorMsg = new StringBuilder();
            for (Diagnostic d : diagnostics.getDiagnostics()) {
                String err = String.format("Compilation error: Line %d - %s%n", d.getLineNumber(), d.getMessage(null));
                errorMsg.append(err);
                System.err.print(err);
            }
            throw new IOException(errorMsg.toString());
        }
    }

    private class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        JavaSourceFromString(final String name, final String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(final boolean ignoreEncodingErrors) {
            return this.code;
        }
    }

    private class OutputStreamSimpleFileObject extends SimpleJavaFileObject {
        private OutputStream outputStream;

        protected OutputStreamSimpleFileObject(final URI uri, final JavaFileObject.Kind kind,
                                               final OutputStream outputStream) {
            super(uri, kind);
            this.outputStream = outputStream;
        }

        @Override
        public OutputStream openOutputStream() {
            return this.outputStream;
        }
    }

    private class OutputStreamJavaFileManager<M extends JavaFileManager>
            extends ForwardingJavaFileManager<M> {
        private OutputStream outputStream;

        protected OutputStreamJavaFileManager(final M fileManager, final OutputStream outputStream) {
            super(fileManager);
            this.outputStream = outputStream;
        }

        @Override
        public JavaFileObject getJavaFileForOutput(final JavaFileManager.Location location,
                                                   final String className, final JavaFileObject.Kind kind, final FileObject sibling) {
            return new OutputStreamSimpleFileObject(new File(className).toURI(), kind, outputStream);
        }
    }
}

 */