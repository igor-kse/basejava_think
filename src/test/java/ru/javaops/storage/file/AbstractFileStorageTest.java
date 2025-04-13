package ru.javaops.storage.file;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import ru.javaops.model.Resume;
import ru.javaops.storage.AbstractStorageTest;
import ru.javaops.storage.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractFileStorageTest extends AbstractStorageTest {
    protected static final String USER_DIRECTORY_PATH = System.getProperty("user.dir");
    protected static final String STORAGE_DIRECTORY_PATH = "file_storage";
    protected static final Path STORAGE_DIRECTORY = Paths.get(USER_DIRECTORY_PATH, STORAGE_DIRECTORY_PATH);

    public AbstractFileStorageTest(Storage storage) {
        super(storage);
    }

    @AfterAll
    public static void cleanUp() {
        if (Files.exists(STORAGE_DIRECTORY)) {
            try {
                Files.delete(STORAGE_DIRECTORY);
            } catch (IOException e) {
                throw new RuntimeException("Could not delete " + STORAGE_DIRECTORY.getFileName(), e);
            }
        }
    }

    @Override
    protected void doAssertUpdate(Resume updated, String uuid) {
        Assertions.assertEquals(updated, storage.get(uuid));
    }
}
