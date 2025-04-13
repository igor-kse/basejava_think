package ru.javaops.storage.file;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import ru.javaops.model.Resume;
import ru.javaops.storage.AbstractStorageTest;
import ru.javaops.storage.Storage;

import java.io.File;

public abstract class AbstractFileStorageTest extends AbstractStorageTest {

    private static File STORAGE_DIRECTORY;

    public AbstractFileStorageTest(Storage storage, String storageDir) {
        super(storage);
        STORAGE_DIRECTORY = new File(storageDir);
    }

    @AfterAll
    protected static void cleanUp() {
        if (STORAGE_DIRECTORY != null && STORAGE_DIRECTORY.exists() && !STORAGE_DIRECTORY.delete()) {
            throw new RuntimeException("Could not delete " + STORAGE_DIRECTORY.getAbsolutePath());
        }
    }

    @Override
    protected void doAssertUpdate(Resume updated, String uuid) {
        Assertions.assertEquals(updated, storage.get(uuid));
    }
}
