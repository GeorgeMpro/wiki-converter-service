package processor;

import writer.WikiWriter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InputFileProcessor {
    private NewFileUtility utility;
    private WikiWriter writer;

    public InputFileProcessor(NewFileUtility utility, WikiWriter writer) {
        this.utility = utility;
        this.writer = writer;
    }

    public boolean checkAndUpdateDirectory(String inputPath, String outputPath) throws IOException {
        List<File> files = processDirectoryForNewFiles(inputPath);
        processFileList(files, inputPath, outputPath);

        return utility.updateTimeToLatestInDirectory(inputPath);
    }

    List<File> processDirectoryForNewFiles(String path) throws IOException {
        boolean hasNewFiles = utility.hasNewFiles(path);
        List<File> newFiles = null;
        if (hasNewFiles) {
            newFiles = utility.getNewFiles(path);
        }

        return Optional.ofNullable(newFiles).orElse(Collections.emptyList());
    }

    boolean processFileList(List<File> newFiles, String inputDirectory, String outputDirectory) {
        if (newFiles.size() == 0) {
            return false;
        }

        return writer.writeAllNewFilesToOutput(newFiles, inputDirectory, outputDirectory);
    }
}
