package ru.javaops.util.executors.io;

import java.io.IOException;

@FunctionalInterface
public interface IOSupplier<R> {
    R get() throws IOException;
}
