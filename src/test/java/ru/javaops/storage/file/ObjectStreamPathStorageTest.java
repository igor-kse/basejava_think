package ru.javaops.storage.file;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import ru.javaops.model.Resume;
import ru.javaops.storage.AbstractStorageTest;
import ru.javaops.util.serializers.ObjectStreamSerializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    private static final Path STORAGE_DIRECTORY = Paths.get(".\\objectStreamPathStorage");

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new ObjectStreamSerializer()));
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
