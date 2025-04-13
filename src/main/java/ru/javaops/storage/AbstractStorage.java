package ru.javaops.storage;

import ru.javaops.exceptions.ExistingResumeStorageException;
import ru.javaops.exceptions.NotExistingResumeStorageException;
import ru.javaops.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<K> implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public final void save(Resume resume) {
        K searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public final Resume get(String uuid) {
        K searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public final void delete(String uuid) {
        K searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    public final void update(Resume resume) {
        K searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumes = doGetAll();
        resumes.sort(RESUME_COMPARATOR);
        return Collections.unmodifiableList(resumes);
    }

    public void clear() {
        doClear();
    }

    public int size() {
        return doSize();
    }

    protected K getExistingSearchKey(String uuid) {
        K searchKey = getSearchKey(uuid);
        if (!isExisting(searchKey)) {
            throw new NotExistingResumeStorageException(uuid);
        }
        return searchKey;
    }

    protected K getNotExistingSearchKey(String uuid) {
        K searchKey = getSearchKey(uuid);
        if (isExisting(searchKey)) {
            throw new ExistingResumeStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExisting(K searchKey);

    protected abstract void doClear();

    protected abstract int doSize();

    protected abstract List<Resume> doGetAll();

    protected abstract Resume doGet(K searchKey);

    protected abstract void doDelete(K searchKey);

    protected abstract void doSave(Resume resume, K searchKey);

    protected abstract void doUpdate(Resume resume, K searchKey);

    protected abstract K getSearchKey(String uuid);
}
