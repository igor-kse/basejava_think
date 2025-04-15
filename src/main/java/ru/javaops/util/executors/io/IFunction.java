package ru.javaops.util.executors.io;

import java.io.IOException;

@FunctionalInterface
public interface IFunction<P, R> {
    R apply(P parameter) throws IOException;
}