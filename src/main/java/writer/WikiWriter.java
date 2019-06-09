package writer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface WikiWriter {
    void readInputDirectoryAndWriteToWikiDirectory(String inputDirectoryPath, String outputDirectoryPath) throws IOException;

    void writeNewFilesToWiki(String inputDirectory, String outputDirectory) throws IOException;

    boolean writeAllNewFilesToOutput(List<File> files, String inputDirectory, String outputDirectory);

    void readFileAndWriteToWikiFile(String inputPath, String outputPath, String fileNamePath);
}
