package ru.javaops.storage;

import ru.javaops.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size = 0;

    public Resume[] getAll() {
        System.out.println("Copying all resumes");
        return Arrays.copyOf(storage, size);
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("The storage has been cleared. Size is: " + size);
    }

    public int size() {
        return size;
    }

    protected abstract int getSearchKey(String uuid);
}
