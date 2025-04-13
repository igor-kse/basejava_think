package ru.javaops.storage.file.executors;

import java.io.IOException;

@FunctionalInterface
public interface IOFunction<P, R> {
    R apply(P parameter) throws IOException;
}