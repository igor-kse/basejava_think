package ru.javaops.exceptions;

public class NotExistingResumeStorageException extends StorageException {

    public NotExistingResumeStorageException(String uuid) {
        super("Resume " + uuid + "does not exist", uuid);
    }
}
