package ru.javaops.exceptions;

import java.io.IOException;

public class CorruptedDataFormatException extends IOException {
    public CorruptedDataFormatException(String message) {
        super(message);
    }
}