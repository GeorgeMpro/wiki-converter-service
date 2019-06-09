package writer;

import log.LoggingUtility;
import parser.WikiParser;
import parser.XmlToWikiParser;
import processor.NewFileUtility;
import processor.NewFileUtilityByFileTime;
import reader.InputDirectoryReader;
import reader.XmlInputDirectoryReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static log.LoggingUtility.*;

public class XmlToWikiWriter implements WikiWriter {
    private WriterHelper helper = new WriterHelper();
    private InputDirectoryReader reader = new XmlInputDirectoryReader();
    private NewFileUtility newFileUtility = new NewFileUtilityByFileTime();
    private WikiParser parser = new XmlToWikiParser();

    @Override
    public void readInputDirectoryAndWriteToWikiDirectory(String inputDirectoryPath, String outputDirectoryPath) throws IOException {
        List<String> filePathsToParse = reader.getFilePathList(inputDirectoryPath);
        Files.createDirectories(Paths.get(outputDirectoryPath));
        for (String path : filePathsToParse) {
            readFileAndWriteToWikiFile(inputDirectoryPath, outputDirectoryPath, path);
        }
    }

    @Override
    public void writeNewFilesToWiki(String inputDirectory, String outputDirectory) throws IOException {
        boolean hasNewFiles = newFileUtility.hasNewFiles(inputDirectory);

        if (hasNewFiles) {
            List<File> files = newFileUtility.getNewFiles(inputDirectory);
            writeAllNewFilesToOutput(files, inputDirectory, outputDirectory);
            newFileUtility.updateTimeToLatestInDirectory(inputDirectory);
        }
    }

    @Override
    public boolean writeAllNewFilesToOutput(List<File> newFiles, String inputDirectory, String outputDirectory) {
        for (File file : newFiles) {
            readFileAndWriteToWikiFile(inputDirectory, outputDirectory, file.getPath());
        }

        return true;
    }

    @Override
    public void readFileAndWriteToWikiFile(String inputPath, String outputPath, String fileNamePath) {
        String wikiFilePath = helper.setupWikiFilePath(inputPath, fileNamePath, outputPath);
        String wikiFileContents = parser.writeToWikiFormat(fileNamePath);

        try (PrintWriter out = new PrintWriter(wikiFilePath)) {
            out.println(wikiFileContents);
        } catch (FileNotFoundException e) {
            logError(this.getClass(), e.getMessage(), e);
        }
    }
}
