package ru.javaops.storage;

import ru.javaops.model.Resume;

public class ArrayStorage extends AbstractArrayStorage implements Storage {

    public void save(Resume resume) {
        int searchKey = getSearchKey(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else if (searchKey != -1) {
            System.out.println("A resume with uuid " + resume.getUuid() + " already exists");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey == -1) {
            System.out.println("Resume with uuid " + uuid + " not found");
            return null;
        }
        return storage[searchKey];
    }

    public void delete(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey == -1) {
            System.out.println("Resume with uuid " + uuid + " not found");
            return;
        }
        size--;
        storage[searchKey] = storage[size];
        storage[size] = null;
    }

    public void update(Resume resume) {
        int searchKey = getSearchKey(resume.getUuid());
        if (searchKey == -1) {
            System.out.println("Resume with uuid " + resume.getUuid() + " not found");
            return;
        }
        System.out.println("Replacing resume with uuid " + resume.getUuid());
        storage[searchKey] = resume;
    }

    protected int getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
