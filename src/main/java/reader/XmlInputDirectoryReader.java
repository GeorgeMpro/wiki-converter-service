package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class XmlInputDirectoryReader implements InputDirectoryReader {

    /**
     * Get the number of files in directory.
     *
     * @param directoryPath target directory
     * @return number of files in directory
     * @throws IOException
     */
    @Override
    public int getDirectoryFileSize(String directoryPath) throws IOException {
        List<Path> filePaths = Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .collect(toList());

        return filePaths.size();
    }

    @Override
    public List<Path> getAllFilePaths(String directoryPath) throws IOException {

        return Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .collect(toList());
    }


    @Override
    public List<String> getFilePathList(String directoryPath) throws IOException {
        List<Path> list = getAllFilePaths(directoryPath);
        List<String> filePaths = new ArrayList<>();
        for (Path p : list)
            filePaths.add(p.toString());

        return filePaths;
    }
}
