package Graph;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Utils {

    public static String getPath(String fileName) {
        String absolutePath = fileName;
        try {
            URL res = Utils.class.getClassLoader().getResource(fileName);
            File file;
            if (res != null) {
                file = Paths.get(res.toURI()).toFile();
                absolutePath = file.getAbsolutePath();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return absolutePath;
    }

    public static boolean isFileEmpty(File file) {
        return file.exists() && file.length() > 0;
    }

    public static String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }
}
