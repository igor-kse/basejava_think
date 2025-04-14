package ru.javaops.storage.file;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import ru.javaops.model.Resume;
import ru.javaops.storage.AbstractStorageTest;
import ru.javaops.storage.Storage;
import ru.javaops.util.executors.io.PathExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractFileStorageTest extends AbstractStorageTest {
    protected static final String USER_DIRECTORY_PATH = System.getProperty("user.dir");
    protected static final String STORAGE_DIRECTORY_PATH = "file_storage";
    protected static final Path PATH_STORAGE_DIRECTORY = Paths.get(USER_DIRECTORY_PATH, STORAGE_DIRECTORY_PATH);
    protected static final File FILE_STORAGE_DIRECTORY = new File(USER_DIRECTORY_PATH, STORAGE_DIRECTORY_PATH);

    public AbstractFileStorageTest(Storage storage) {
        super(storage);
    }

    @BeforeAll
    @AfterAll
    public static void cleanStorageDir() {
        if (!Files.exists(PATH_STORAGE_DIRECTORY)) {
            return;
        }
        new PathExecutor().executeOnDirectory(PATH_STORAGE_DIRECTORY, "I/O error while setting up",
                stream -> {
                    stream.forEach(AbstractFileStorageTest::deleteByPath);
                    Files.delete(PATH_STORAGE_DIRECTORY);
                    return true;
                });
    }

    private static void deleteByPath(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doAssertUpdate(Resume updated, String uuid) {
        Assertions.assertEquals(updated, storage.get(uuid));
    }
}
