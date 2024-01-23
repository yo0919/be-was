package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class FileUtils {

    public static byte[] readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath).normalize();
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        }
        return null;
    }
    public static void writeFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes());
    }
}
