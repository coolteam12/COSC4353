
package sample;

import java.io.File;

public class FileItem extends File {

    private String name;

    public FileItem(String path) {
        super(path);
        this.name = path.substring(path.lastIndexOf("\\") + 1);
    }

    public FileItem(File file) {
        this(file.getPath());
    }

    public String toString() {
        return name;
    }

}
