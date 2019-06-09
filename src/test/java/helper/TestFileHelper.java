package helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestFileHelper {
    public final static String BASE = "src/test/java/";
    public final static String RESOURCES = "src/test/resources/";
    public final static String RESOURCES_FILES_INPUT = RESOURCES + "files/input/";
    public final static String RESOURCES_FILES_OUTPUT = RESOURCES + "files/output/";
    public final static String INPUT = RESOURCES + "input/";
    public final static String OUTPUT = RESOURCES + "output/";

    public static void cleanFileDirectory(String directoryPath) throws IOException {
        Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    public static void createTestFiles(int filesToCreate, String fileName) throws IOException {
        for (int i = 0; i < filesToCreate; i++) {
            File tempFile = new File(String.format("%1s%2s%d.xml", RESOURCES_FILES_INPUT, fileName, i));
            tempFile.createNewFile();
        }
    }
}

