package ru.javaops.storage.file;

import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;
import ru.javaops.storage.AbstractStorage;
import ru.javaops.util.executors.io.FileExecutor;
import ru.javaops.util.serializers.ISerializer;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileStorage extends AbstractStorage<File> {

    private final File directory;
    private final FileExecutor executor;
    private final ISerializer<Resume> serializer;

    public FileStorage(File directory, ISerializer<Resume> serializer) {
        Objects.requireNonNull(directory);
        Objects.requireNonNull(serializer);

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
        this.serializer = serializer;
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
            try (var is = new BufferedInputStream(new FileInputStream(searchKey))) {
                return serializer.doRead(is);
            }
        });
    }

    @Override
    protected void doDelete(File searchKey) {
        executor.execute("Cannot delete file", () -> {
            if (!searchKey.delete()) {
                throw new IOException("Delete failed for " + searchKey.getName());
            }
        });
    }

    @Override
    protected void doSave(Resume resume, File searchKey) {
        executor.execute("Cannot write file ", () -> {
            if (!searchKey.createNewFile()) {
                throw new StorageException("Cannot create file " + searchKey.getName());
            }
            try (var os = new BufferedOutputStream(new FileOutputStream(searchKey))) {
                serializer.doWrite(resume, os);
                os.flush();
            }
        });
    }

    @Override
    protected void doUpdate(Resume resume, File searchKey) {
        executor.execute("Cannot update file ", () -> {
            try (var os = new BufferedOutputStream(new FileOutputStream(searchKey))){
                serializer.doWrite(resume, os);
                os.flush();
            }
        });
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory.getAbsolutePath(), uuid);
    }
}
