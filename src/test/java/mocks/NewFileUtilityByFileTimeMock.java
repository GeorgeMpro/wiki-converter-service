package mocks;

import processor.NewFileUtility;

import java.io.File;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

public class NewFileUtilityByFileTimeMock implements NewFileUtility {

    @Override
    public FileTime getTime(String directory) {
        return null;
    }

    @Override
    public boolean updateTimeToLatestInDirectory(String directory) throws NullPointerException {

        return true;
    }

    @Override
    public FileTime checkLatestModification(String directory) {
        return null;
    }

    @Override
    public List<File> getNewFiles(String directory) {
        return new ArrayList<>();
    }

    @Override
    public boolean hasNewFiles(String directory) {
        return true;
    }

    @Override
    public FileTime getLastModifiedFileTimeInDirectory(List<FileTime> fileTimes) {
        return null;
    }

    @Override
    public List<FileTime> getFileTimes(String directory, Class theClass) {
        return null;
    }
}
