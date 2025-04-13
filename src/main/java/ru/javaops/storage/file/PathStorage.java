package ru.javaops.storage.file;

import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;
import ru.javaops.storage.AbstractStorage;
import ru.javaops.util.executors.io.PathExecutor;
import ru.javaops.util.serializers.ISerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {

    private final Path directory;
    private final PathExecutor executor;
    private final ISerializer<Resume> serializer;

    public PathStorage(Path directory, ISerializer<Resume> serializer) {
        Objects.requireNonNull(directory);
        Objects.requireNonNull(serializer);

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

        this.directory = directory;
        this.serializer = serializer;
        this.executor = new PathExecutor();
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
            try(var is = new BufferedInputStream(Files.newInputStream(searchKey))) {
                return serializer.doRead(is);
            }
        });
    }

    @Override
    protected void doDelete(Path searchKey) {
        executor.execute("Cannot delete resume", () -> Files.delete(searchKey));
    }

    @Override
    protected void doSave(Resume resume, Path searchKey) {
        executor.execute("Cannot save resume", () -> {
            try(var os = Files.newOutputStream(searchKey, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                serializer.doWrite(resume, new BufferedOutputStream(os));
            }
        });
    }

    @Override
    protected void doUpdate(Resume resume, Path searchKey) {
        executor.execute("Cannot write file with updated resume", () -> {
            try(var os = Files.newOutputStream(searchKey, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                serializer.doWrite(resume, new BufferedOutputStream(os));
            }
        });
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }
}
