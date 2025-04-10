package ru.javaops.storage.executors;

import java.io.IOException;

@FunctionalInterface
public interface IOAction {
    void accept() throws IOException;
}
