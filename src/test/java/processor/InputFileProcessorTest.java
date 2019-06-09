package processor;

import mocks.NewFileUtilityByFileTimeMock;
import mocks.XmlToWikiWriterMock;
import org.junit.jupiter.api.Test;
import writer.WikiWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class InputFileProcessorTest {
    private NewFileUtility utility = new NewFileUtilityByFileTimeMock();
    private WikiWriter writer = new XmlToWikiWriterMock();
    private final InputFileProcessor inputFileProcessor = new InputFileProcessor(utility, writer);

    @Test
    void whenHasNewFiles_thenGetNewFiles() throws IOException {
        List<File> newFiles = inputFileProcessor.processDirectoryForNewFiles("");

        assertNotNull(newFiles);
        assertEquals(Collections.emptyList(), newFiles);
    }

    @Test
    void whenNoNewFiles_thenReturnEmptyCollection() throws IOException {
        List<File> newFiles = inputFileProcessor.processDirectoryForNewFiles("");

        assertNotEquals(Optional.empty(), newFiles);
        assertEquals(Collections.emptyList(), newFiles);
    }

    @Test
    void whenNoNewFiles_thenNoWriting() throws IOException {
        List<File> newFiles = inputFileProcessor.processDirectoryForNewFiles("");
        boolean writtenNewFiles = inputFileProcessor.processFileList(newFiles, "", "");

        assertFalse(writtenNewFiles);
    }

    @Test
    void whenNewFiles_thenWriteToOutput() {
        List<File> files = new ArrayList<>();
        files.add(new File(""));

        boolean writtenNewFiles = inputFileProcessor.processFileList(files, "", "");

        assertTrue(writtenNewFiles);
    }

    @Test
    void whenDoneProcessThenUpdateTime() throws IOException {
        boolean updateModifiedTime = inputFileProcessor.checkAndUpdateDirectory("", "");

        assertTrue(updateModifiedTime);
    }
}

