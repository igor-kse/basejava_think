package ru.javaops.util.executors.io;

import java.io.IOException;

@FunctionalInterface
public interface IOFunction<P, R> {
    R apply(P parameter) throws IOException;
}