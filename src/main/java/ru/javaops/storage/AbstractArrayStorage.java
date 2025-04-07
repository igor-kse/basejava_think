package ru.javaops.storage;

import ru.javaops.exceptions.ExistingResumeStorageException;
import ru.javaops.exceptions.NotExistingResumeStorageException;
import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size = 0;

    public final void save(Resume resume) {
        int searchKey = getSearchKey(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else if (searchKey >= 0) {
            throw new ExistingResumeStorageException(resume.getUuid());
        } else {
            insertResume(resume, searchKey);
            size++;
        }
    }

    public final Resume get(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey < 0) {
            throw new NotExistingResumeStorageException(uuid);
        }
        return storage[searchKey];
    }

    public final void delete(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey < 0) {
            throw new NotExistingResumeStorageException(uuid);
        }
        squashPosition(searchKey);
        size--;
        storage[size] = null;
    }

    public final void update(Resume resume) {
        int searchKey = getSearchKey(resume.getUuid());
        if (searchKey < 0) {
            throw new NotExistingResumeStorageException(resume.getUuid());
        }
        System.out.println("Replacing resume with uuid " + resume.getUuid());
        storage[searchKey] = resume;
    }

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

    protected abstract void squashPosition(int index);

    protected abstract void insertResume(Resume resume, int index);
}
