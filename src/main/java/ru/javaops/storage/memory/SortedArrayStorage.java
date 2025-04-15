package ru.javaops.storage.memory;

import ru.javaops.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends BaseArrayStorage {

    @Override
    protected void squashPosition(int index) {
        int moveAmount = size - index - 1;
        if (moveAmount > 0) {
            System.arraycopy(storage, index + 1, storage, index, moveAmount);
        }
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        storage[insertionIndex] = resume;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, searchKey, Comparator.comparing(Resume::getUuid));
    }
}
