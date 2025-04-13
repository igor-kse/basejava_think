package ru.javaops.storage.file.executors;

import java.io.IOException;

@FunctionalInterface
public interface IOSupplier<R> {
    R accept() throws IOException;
}
