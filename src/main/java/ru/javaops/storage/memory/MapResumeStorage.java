package ru.javaops.storage.memory;

import ru.javaops.model.Resume;
import ru.javaops.storage.BaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends BaseStorage<Resume> {

    Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExisting(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected int doSize() {
        return storage.size();
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doDelete(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    protected void doSave(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }
}
