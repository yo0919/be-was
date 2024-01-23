package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedInputStream;

public class FileUtils {

    public static byte[] readFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                byte[] data = new byte[(int) file.length()];
                bis.read(data);
                return data;
            }
        }
        return null;
    }
}
