package ru.javaops.storage.file;

import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;
import ru.javaops.storage.AbstractStorage;
import ru.javaops.storage.file.executors.PathExecutor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private final Path directory;
    private final PathExecutor executor;

    public AbstractPathStorage(Path directory) {
        this.directory = directory;
        this.executor = new PathExecutor();

        Objects.requireNonNull(directory);

        if (!Files.exists(directory)) {
            try {
                Files.createDirectory(directory);
            } catch (IOException e) {
                throw new StorageException("Cannot create storage directory: " + directory, null, e);
            }
        }

        if (!Files.isDirectory(directory) || !Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException("Is not directory or not readable/writable " + directory);
        }
    }

    @Override
    protected boolean isExisting(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected void doClear() {
        executor.executeOnDirectory(directory, "Cannot clear storage directory", pathStream -> {
            pathStream.forEach(this::doDelete);
            return true;
        });
    }

    @Override
    protected int doSize() {
        return executor.executeOnDirectory(directory, "Cannot count resumes",
                pathStream -> (int) pathStream.count()
        );
    }

    @Override
    protected List<Resume> doGetAll() {
        return executor.executeOnDirectory(directory, "Cannot get resumes",
                pathStream -> pathStream.map(this::doGet).collect(Collectors.toList())
        );
    }

    @Override
    protected Resume doGet(Path searchKey) {
        return executor.read("Cannot read resume", () -> {
            var is = new BufferedInputStream(Files.newInputStream(searchKey));
            return doRead(is);
        });
    }

    @Override
    protected void doDelete(Path searchKey) {
        executor.execute("Cannot delete resume", () -> Files.delete(searchKey));
    }

    @Override
    protected void doSave(Resume resume, Path searchKey) {
        executor.execute("Cannot save resume", () -> {
            var os = Files.newOutputStream(searchKey, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            doWrite(resume, new BufferedOutputStream(os));
        });
    }

    @Override
    protected void doUpdate(Resume resume, Path searchKey) {
        executor.execute("Cannot write file with updated resume", () -> {
            var os = Files.newOutputStream(searchKey, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            doWrite(resume, new BufferedOutputStream(os));
        });
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    protected abstract Resume doRead(InputStream inputStream) throws IOException;

    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;
}
