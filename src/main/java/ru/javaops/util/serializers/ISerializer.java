package ru.javaops.util.serializers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISerializer<T> {

    T doRead(InputStream is) throws IOException;

    void doWrite(T instance, OutputStream os) throws IOException;
}
