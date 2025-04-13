package ru.javaops.storage.file;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import ru.javaops.model.Resume;
import ru.javaops.storage.AbstractStorageTest;
import ru.javaops.util.serializers.ObjectStreamSerializer;

import java.io.File;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    private static final File STORAGE_DIRECTORY = new File(".\\objectStreamFileStorage");

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectStreamSerializer<>()));
    }

    @AfterAll
    protected static void cleanUp() {
        if (STORAGE_DIRECTORY.exists() && !STORAGE_DIRECTORY.delete()) {
            throw new RuntimeException("Could not delete " + STORAGE_DIRECTORY.getAbsolutePath());
        }
    }

    @Override
    protected void doAssertUpdate(Resume updated, String uuid) {
        Assertions.assertEquals(updated, storage.get(uuid));
    }
}
