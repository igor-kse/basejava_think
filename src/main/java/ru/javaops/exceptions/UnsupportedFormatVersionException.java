package ru.javaops.exceptions;

import java.io.IOException;

public class UnsupportedFormatVersionException extends IOException {
    public UnsupportedFormatVersionException(int version) {
        super("Unsupported format version: " + version);
    }
}
