package ru.javaops.storage.executors;

import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileExecutor {

    public void execute(String errorMessage, IOAction action) {
        try {
            action.accept();
        } catch (IOException e) {
            throw new StorageException(errorMessage, e);
        }
    }

    public Resume read(String errorMessage, IOSupplier<Resume> function) {
        try {
            return function.accept();
        } catch (IOException e) {
            throw new StorageException(errorMessage, e);
        }
    }

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
