package ru.javaops.util.executors.io;

import java.io.IOException;

@FunctionalInterface
public interface ISupplier<R> {
    R get() throws IOException;
}
