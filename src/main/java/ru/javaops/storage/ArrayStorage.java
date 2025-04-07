package ru.javaops.storage;

import ru.javaops.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void squashPosition(int index) {
        // handling early decrement of size in the parent class as it might be
        Resume last = storage[size] != null
                ? storage[size]
                : storage[size - 1];
        storage[index] = last;
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        storage[size] = resume;
    }
}
