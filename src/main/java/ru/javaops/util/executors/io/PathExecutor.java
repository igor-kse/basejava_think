package ru.javaops.util.executors.io;

import ru.javaops.exceptions.StorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class PathExecutor extends BaseIOExecutor {

    public <R> R executeOnDirectory(Path directory, String errorMessage, IFunction<Stream<Path>, R> function) {
        try (Stream<Path> stream = Files.list(directory)) {
            return function.apply(stream);
        } catch (IOException e) {
            throw new StorageException(errorMessage, e);
        }
    }
}