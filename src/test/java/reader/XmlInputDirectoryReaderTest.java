package reader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Collections;
import java.util.List;

import static helper.TestFileHelper.RESOURCES;
import static org.junit.jupiter.api.Assertions.*;

class XmlInputDirectoryReaderTest {
    private InputDirectoryReader reader = new XmlInputDirectoryReader();

    @Test
    void getDirectorySize() throws Exception {

        assertEquals(3, reader.getDirectoryFileSize(RESOURCES + "input"));
    }

    @Test
    void getAllFilePaths() throws IOException {
        assertNotNull(reader.getAllFilePaths(RESOURCES + "input/"));
    }

    @Test
    void whenInvalidPathThrowException() {
        assertThrows(NoSuchFileException.class, () -> reader.getAllFilePaths("invalid name"));
    }


    @Test
    void getPathListFromDirectory() throws IOException {
        String directoryPath = RESOURCES + "input";
        List<String> filePathList = reader.getFilePathList(directoryPath);
        assertNotEquals(Collections.emptyList(), filePathList);
        assertTrue(filePathList.size() > 0);
    }
}
