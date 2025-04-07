package ru.javaops.storage;

import org.junit.jupiter.api.Test;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    public void deleteSingle() {
        storage.clear();
        assertSize(0);
        storage.save(RESUME_1);
        assertSize(1);
        storage.delete(UUID_1);
        assertSize(0);
    }
}
