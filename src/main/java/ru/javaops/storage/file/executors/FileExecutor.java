package ru.javaops.storage.file.executors;

import ru.javaops.exceptions.StorageException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileExecutor extends AbstractIOExecutor {

    public List<File> listFiles(File directory) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable");
        }

        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("IO Error");
        }
        return Arrays.asList(files);
    }
}
