package ru.javaops.storage.executors;

import java.io.IOException;

@FunctionalInterface
public interface IOSupplier<R> {
    R accept() throws IOException;
}
