package processor;

import log.LoggingUtility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static log.LoggingUtility.*;

/**
 * Utility class for finding newly modified files in a directory.
 */
public class NewFileUtilityByFileTime implements NewFileUtility {

    private FileTime lastKnownModification = FileTime.fromMillis(0L);

    /**
     * Returns latest known modified time of files in the directory.<br>
     * If no value is set, will return default value.
     *
     * @return latest modified time.
     */
    @Override
    public FileTime getTime(String directory) {
        return lastKnownModification;
    }

    /**
     * Set {@code lastKnownModification} to the latest modified time in the target directory.
     *
     * @param directory target directory to get the latest time from
     * @throws IOException
     */
    @Override
    public boolean updateTimeToLatestInDirectory(String directory) throws IOException, NullPointerException {
        Optional<String> opDirectory = Optional.ofNullable(directory);
        lastKnownModification = getTimeInDirectory(opDirectory);
        return true;
    }

    //    todo(?) del needed method? already compares to lastKnownModification
    @Override
    public FileTime checkLatestModification(String directory) throws IOException {
        Optional<String> opDirectory = Optional.ofNullable(directory);
        return getTimeInDirectory(opDirectory);
    }

    private FileTime getTimeInDirectory(Optional<String> directory) throws IOException {
        return getLastModifiedFileTimeInDirectory(getFileTimes(directory.orElseThrow(NullPointerException::new), null));
    }


    /**
     * Get new  files from the target directory.
     *
     * @param directory target directory
     * @return files that were modified or added after specified date
     * @throws IOException
     */
    @Override
    public List<File> getNewFiles(String directory) throws IOException {
        return getFileStream(directory)
                .collect(Collectors.toList());
    }

    /**
     * Checks to see if new files were added to directory.
     *
     * @param directory target directory
     * @return true if there are new files
     * @throws IOException
     */
    @Override
    public boolean hasNewFiles(String directory) throws IOException {
        long fileSize = getFileStream(directory)
                .count();

        return fileSize > 0;
    }

    private Stream<File> getFileStream(String directory) throws IOException {
        return Files.walk(Paths.get(directory))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .filter(file -> file.lastModified() > lastKnownModification.toMillis());
    }

    @Override
    public FileTime getLastModifiedFileTimeInDirectory(List<FileTime> fileTimes) {
        return fileTimes.stream()
                .max(Comparator.comparing(FileTime::toMillis))

//                todo (?) throw an exception instead
                .orElse(null);
    }


    @Override
    public List<FileTime> getFileTimes(String directory, Class theClass) throws IOException {
        return Files.walk(Paths.get(directory).toRealPath())
                .map(path -> getFileTime(theClass, path))
                .collect(Collectors.toList());
    }

    private FileTime getFileTime(Class theClass, Path path) {
        FileTime lastModifiedTime = null;
        try {
            lastModifiedTime = Files.getLastModifiedTime(path);
        } catch (IOException e) {
            logError(theClass, e.getMessage(), e);
        }
        return lastModifiedTime;
    }
}
