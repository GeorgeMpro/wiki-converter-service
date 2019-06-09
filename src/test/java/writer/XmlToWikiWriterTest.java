package writer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import processor.NewFileUtility;
import processor.NewFileUtilityByFileTime;
import reader.InputDirectoryReader;
import reader.XmlInputDirectoryReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Collections;
import java.util.List;

import static helper.TestFileHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlToWikiWriterTest {

    private WikiWriter writer = new XmlToWikiWriter();
    private InputDirectoryReader reader = new XmlInputDirectoryReader();
    private WriterHelper helper = new WriterHelper();

    private String testFileName = "test-file";

    private NewFileUtility utility;

    @BeforeEach
    void setUp() throws IOException {
        cleanFileDirectory(RESOURCES_FILES_INPUT);
        cleanFileDirectory(RESOURCES_FILES_OUTPUT);
        utility = new NewFileUtilityByFileTime();
        utility.updateTimeToLatestInDirectory(RESOURCES_FILES_INPUT);
    }

    @Test
    @DisplayName("Writer integration test")
    void writeFilesFromXmlFolderToWikiFolder() throws Exception {
        String inputDirectoryPath = INPUT;
        String outputDirectoryPath = OUTPUT;
        writer.readInputDirectoryAndWriteToWikiDirectory(inputDirectoryPath, outputDirectoryPath);

        assertTrue(Files.exists(Paths.get(outputDirectoryPath)));
        assertOutputFilesExist(inputDirectoryPath, outputDirectoryPath);
    }


    @Test
    void writeNewFilesToOutputDirectory() throws IOException {
        createTestFiles(2, testFileName);

        FileTime latestModified = utility.checkLatestModification(RESOURCES_FILES_INPUT);
        System.out.println("last" + utility.getTime(RESOURCES_FILES_INPUT) + "\nnew" + latestModified);
        List<File> files = utility.getNewFiles(RESOURCES_FILES_INPUT);
        int newInput = files.size();

        writer.writeAllNewFilesToOutput(files, RESOURCES_FILES_INPUT, RESOURCES_FILES_OUTPUT);
        int newOutput = utility.getNewFiles(RESOURCES_FILES_OUTPUT).size();

        assertTrue(newInput > 0, "should find new files");
        assertEquals(2, newInput, "There should be 2 new files in the directory");
        assertEquals(newInput, newOutput, "The number of new files in output directory should match the number of new files in the input directory. These new files should be converted to new files in the output.");
    }

    @Test
    void whenNewFilesListIsEmpty_thenNoNewFilesAreWritten() throws IOException {
        writer.writeAllNewFilesToOutput(Collections.EMPTY_LIST, RESOURCES_FILES_INPUT, RESOURCES_FILES_OUTPUT);

        int afterUpdate = reader.getDirectoryFileSize(RESOURCES_FILES_OUTPUT);

        assertEquals(0, afterUpdate, "number of files in the directory should remain the same after writing attempt");
    }

    @Test
    void settingUtilityTimeUpdatesToTheCurrentTime() throws IOException {
        createTestFiles(2, testFileName);
        writer.writeNewFilesToWiki(RESOURCES_FILES_INPUT, RESOURCES_FILES_OUTPUT);

        long expected = utility.getLastModifiedFileTimeInDirectory(utility.getFileTimes(RESOURCES_FILES_OUTPUT, null)).toMillis();

        utility.updateTimeToLatestInDirectory(RESOURCES_FILES_OUTPUT);
        long actual = utility.getTime(RESOURCES_FILES_OUTPUT).toMillis();

        assertEquals(expected, actual, "the last modified time field should be equal to the actual last modified time in the directory time after writing new files");
    }


    private void assertOutputFilesExist(String inputDirectoryPath, String outputDirectoryPath) throws IOException {
        List<String> inputFilePaths = reader.getFilePathList(inputDirectoryPath);
        assertTrue(inputFilePaths.size() > 0);
        for (String inputPath : inputFilePaths) {
            String xmlName = helper.extractFileName(inputDirectoryPath, inputPath);
            String wikiName = helper.changeFileExtension(xmlName);
            assertTrue(Files.exists(Paths.get(outputDirectoryPath + wikiName)));
        }
    }
}

