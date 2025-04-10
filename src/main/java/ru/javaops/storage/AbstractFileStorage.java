package ru.javaops.storage;

import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;
import ru.javaops.storage.executors.FileExecutor;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractFileStorage extends AbstractStorage<File>{

    private final File directory;
    private final FileExecutor executor;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory);

        if (!directory.exists() && !directory.mkdirs()) {
            throw new StorageException("Cannot create storage directory");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.executor = new FileExecutor();
    }

    @Override
    protected boolean isExisting(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected void doClear() {
        executor.listFiles(directory).forEach(this::doDelete);
    }

    @Override
    protected int doSize() {
        return executor.listFiles(directory).size();
    }

    @Override
    protected List<Resume> doGetAll() {
        return executor.listFiles(directory)
                .stream()
                .map(this::doGet)
                .collect(Collectors.toList());
    }

    @Override
    protected Resume doGet(File searchKey) {
        return executor.read("Cannot read resume", () -> {
                    var is = new BufferedInputStream(new FileInputStream(searchKey));
                    return doRead(is);
                });
    }

    @Override
    protected void doDelete(File searchKey) {
        if (!searchKey.delete()) {
            throw new StorageException("Cannot delete file " + searchKey.getName());
        }
    }

    @Override
    protected void doSave(Resume resume, File searchKey) {
        executor.execute("Cannot write file ", () -> {
            if (!searchKey.createNewFile()) {
                throw new StorageException("Cannot create file " + searchKey.getName());
            }
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(searchKey)));
        });
    }

    @Override
    protected void doUpdate(Resume resume, File searchKey) {
        executor.execute("Cannot write file ", () -> {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(searchKey)));
        });
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory.getAbsolutePath(), uuid);
    }



    protected abstract Resume doRead(InputStream inputStream) throws IOException;

    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;
}
