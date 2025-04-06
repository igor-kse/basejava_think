package ru.javaops.storage;

import model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int searchKey = getSearchKey(resume.getUuid());
        if (searchKey == -1) {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey == -1) {
            return null;
        }
        return storage[searchKey];
    }

    public void delete(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey == -1) {
            return;
        }
        size--;
        storage[searchKey] = storage[size];
        storage[size] = null;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
