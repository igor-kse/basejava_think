package ru.javaops.storage.file.executors;

import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;

import java.io.IOException;

public abstract class AbstractIOExecutor {
    public Resume read(String errorMessage, IOSupplier<Resume> supplier) {
        try {
            return supplier.get();
        } catch (IOException e) {
            throw new StorageException(errorMessage, e);
        }
    }

    public void execute(String errorMessage, IOAction action) {
        try {
            action.accept();
        } catch (IOException e) {
            throw new StorageException(errorMessage, e);
        }
    }
}
