package ru.javaops.util.executors.io;

import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;

import java.io.IOException;

public abstract class BaseIOExecutor {
    public Resume read(String errorMessage, ISupplier<Resume> supplier) {
        try {
            return supplier.get();
        } catch (IOException e) {
            throw new StorageException(errorMessage, e);
        }
    }

    public void execute(String errorMessage, IAction action) {
        try {
            action.accept();
        } catch (IOException e) {
            throw new StorageException(errorMessage, e);
        }
    }
}
