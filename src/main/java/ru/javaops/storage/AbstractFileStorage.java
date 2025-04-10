package ru.javaops.storage;

import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class AbstractFileStorage extends AbstractStorage<File>{

    private final File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory);

        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected boolean isExisting(File searchKey) {
        return false;
    }

    @Override
    protected void doClear() {

    }

    @Override
    protected int doSize() {
        return 0;
    }

    @Override
    protected List<Resume> doGetAll() {
        return List.of();
    }

    @Override
    protected Resume doGet(File searchKey) {
        return null;
    }

    @Override
    protected void doDelete(File searchKey) {

    }

    @Override
    protected void doSave(Resume resume, File searchKey) {

    }

    @Override
    protected void doUpdate(Resume resume, File searchKey) {

    }

    @Override
    protected File getSearchKey(String uuid) {
        return null;
    }
}
