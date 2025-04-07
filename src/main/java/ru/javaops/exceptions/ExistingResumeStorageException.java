package ru.javaops.exceptions;

public class ExistingResumeStorageException extends StorageException {

    public ExistingResumeStorageException(String uuid) {
        super("Resume " + uuid + " already exists", uuid);
    }
}
