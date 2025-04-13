package ru.javaops.storage.file;

import org.junit.jupiter.api.Assertions;
import ru.javaops.model.Resume;
import ru.javaops.storage.AbstractStorageTest;
import ru.javaops.storage.Storage;

public abstract class AbstractFileStorageTest extends AbstractStorageTest {

    public AbstractFileStorageTest(Storage storage) {
        super(storage);
    }

    @Override
    protected void doAssertUpdate(Resume updated, String uuid) {
        Assertions.assertEquals(updated, storage.get(uuid));
    }
}
