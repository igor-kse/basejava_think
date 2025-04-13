package ru.javaops.storage.file.executors;

import ru.javaops.exceptions.StorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class PathExecutor extends AbstractIOExecutor {

    public <R> R executeOnDirectory(Path directory, String errorMessage, IOFunction<Stream<Path>, R> function) {
        try (Stream<Path> stream = Files.list(directory)) {
            return function.apply(stream);
        } catch (IOException e) {
            throw new StorageException(errorMessage, e);
        }
    }
}