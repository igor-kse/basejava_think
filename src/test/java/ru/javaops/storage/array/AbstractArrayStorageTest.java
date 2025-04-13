package ru.javaops.storage.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;
import ru.javaops.storage.AbstractStorageTest;
import ru.javaops.storage.Storage;

import java.util.UUID;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void saveOverflow() {
        storage.clear();
        assertSize(0);
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT ; i++) {
            storage.save(new Resume(UUID.randomUUID().toString()));
        }
        assertSize(AbstractArrayStorage.STORAGE_LIMIT);
        Assertions.assertThrows(StorageException.class, () -> storage.save(RESUME_NOT_EXISTING));
    }
}
