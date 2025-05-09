package ru.javaops.storage.memory;

import ru.javaops.model.Resume;

public class ArrayStorage extends BaseArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void squashPosition(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        storage[size] = resume;
    }
}
