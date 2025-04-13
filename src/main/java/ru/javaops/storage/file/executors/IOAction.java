package ru.javaops.storage.file.executors;

import java.io.IOException;

@FunctionalInterface
public interface IOAction {
    void accept() throws IOException;
}
