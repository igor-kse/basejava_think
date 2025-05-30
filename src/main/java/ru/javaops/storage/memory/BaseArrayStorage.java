package ru.javaops.storage.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javaops.exceptions.StorageException;
import ru.javaops.model.Resume;
import ru.javaops.storage.BaseStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseArrayStorage extends BaseStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseArrayStorage.class);

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected boolean isExisting(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        LOGGER.debug("The storage has been cleared. Size is: {}", size);
    }

    @Override
    protected int doSize() {
        return size;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected List<Resume> doGetAll() {
        LOGGER.debug("Copying all resumes");
        var resumes = Arrays.stream(storage, 0, size).toList();
        return new ArrayList<>(resumes);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        squashPosition(searchKey);
        size--;
        storage[size] = null;
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertResume(resume, searchKey);
            size++;
        }
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        LOGGER.debug("Replacing resume with uuid {}", resume.getUuid());
        storage[searchKey] = resume;
    }

    protected abstract void squashPosition(int index);

    protected abstract void insertResume(Resume resume, int index);
}
