package reader;

import java.io.IOException;
import java.util.List;

public interface InputDirectoryReader {
    int getDirectoryFileSize(String directoryPath) throws IOException;

    List getAllFilePaths(String directoryPath) throws IOException;

    List<String> getFilePathList(String directoryPath) throws IOException;
}
