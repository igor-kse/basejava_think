package ru.javaops.util.serializers;

import ru.javaops.exceptions.StorageException;

import java.io.*;

@SuppressWarnings("unchecked")
public class ObjectStreamSerializer<T> implements ISerializer<T> {

    @Override
    public T doRead(InputStream is) throws IOException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error while reading resume", "", e);
        }
    }

    @Override
    public void doWrite(T instance, OutputStream os) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
        objectOutputStream.writeObject(instance);
    }
}
