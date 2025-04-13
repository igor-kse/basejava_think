package ru.javaops.storage.array;

import org.junit.jupiter.api.Test;

class SortedArrayStorageTest extends AbstractArrayStorageTest {

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
