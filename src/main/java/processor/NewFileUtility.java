package processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.util.List;

public interface NewFileUtility {
    FileTime getTime(String directory);

    boolean updateTimeToLatestInDirectory(String directory) throws IOException, NullPointerException;

    FileTime checkLatestModification(String directory) throws IOException;

    List<File> getNewFiles(String directory) throws IOException;

    boolean hasNewFiles(String directory) throws IOException;

    FileTime getLastModifiedFileTimeInDirectory(List<FileTime> fileTimes);

    List<FileTime> getFileTimes(String directory, Class theClass) throws IOException;
}
