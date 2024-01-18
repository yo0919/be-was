package utils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileUtilsTest {
    @Test
    void readFile_ExistingFile_ReturnsContent() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.writeString(tempFile, "Hello, World!");

        byte[] content = FileUtils.readFile(tempFile.toString());

        assertThat(content).isNotNull()
                .isEqualTo("Hello, World!".getBytes());

        // 임시 파일 삭제
        Files.delete(tempFile);
    }

    @Test
    void readFile_NonExistingFile_ReturnsNull() throws IOException {
        String nonExistingFilePath = "non_existing_file.txt";

        byte[] content = FileUtils.readFile(nonExistingFilePath);

        assertThat(content).isNull();
    }
}