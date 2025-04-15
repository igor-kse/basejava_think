package ru.javaops.util.executors.io;

import java.io.IOException;

@FunctionalInterface
public interface IAction {
    void accept() throws IOException;
}
