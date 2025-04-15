package ru.javaops.storage.memory;

import org.junit.jupiter.api.Test;

class SortedArrayStorageTest extends BaseArrayStorageTest {

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    void deleteSingle() {
        storage.clear();
        assertSize(0);
        storage.save(RESUME_1);
        assertSize(1);
        storage.delete(UUID_1);
        assertSize(0);
    }
}
