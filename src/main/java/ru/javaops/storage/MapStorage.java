package ru.javaops.storage;

import ru.javaops.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExisting(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected int doSize() {
        return storage.values().size();
    }

    @Override
    protected Resume[] doGetAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void doSave(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void doUpdate(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }
}
