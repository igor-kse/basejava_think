package ru.javaops.storage.memory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;
import ru.javaops.storage.BaseStorageTest;
import ru.javaops.storage.IStorage;

import java.util.UUID;

public abstract class BaseArrayStorageTest extends BaseStorageTest {

    public BaseArrayStorageTest(IStorage storage) {
        super(storage);
    }

    @Test
    public void saveOverflow() {
        storage.clear();
        assertSize(0);
        for (int i = 0; i < BaseArrayStorage.STORAGE_LIMIT ; i++) {
            storage.save(new Resume(UUID.randomUUID().toString()));
        }
        assertSize(BaseArrayStorage.STORAGE_LIMIT);
        Assertions.assertThrows(StorageException.class, () -> storage.save(RESUME_NOT_EXISTING));
    }
}
