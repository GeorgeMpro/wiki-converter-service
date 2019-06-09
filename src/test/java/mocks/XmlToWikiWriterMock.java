package mocks;

import writer.WikiWriter;

import java.io.File;
import java.util.List;

public class XmlToWikiWriterMock implements WikiWriter {
    @Override
    public void readInputDirectoryAndWriteToWikiDirectory(String inputDirectoryPath, String outputDirectoryPath) {

    }

    @Override
    public void writeNewFilesToWiki(String inputDirectory, String outputDirectory) {

    }

    @Override
    public boolean writeAllNewFilesToOutput(List<File> files, String inputDirectory, String outputDirectory) {
        return true;
    }

    @Override
    public void readFileAndWriteToWikiFile(String inputPath, String outputPath, String fileNamePath) {

    }
}
