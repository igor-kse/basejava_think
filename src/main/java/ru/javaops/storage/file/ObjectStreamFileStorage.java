package ru.javaops.storage.file;

import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;

import java.io.*;

public class ObjectStreamFileStorage extends AbstractFileStorage {

    public ObjectStreamFileStorage(String directoryPath) {
        super(new File(directoryPath));
    }

    @Override
    protected Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return  (Resume) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error while reading resume", "", e);
        }
    }

    @Override
    protected void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
        }
    }
}
