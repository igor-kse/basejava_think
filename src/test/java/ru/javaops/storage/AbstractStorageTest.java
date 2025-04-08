package ru.javaops.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.javaops.exceptions.ExistingResumeStorageException;
import ru.javaops.exceptions.NotExistingResumeStorageException;
import ru.javaops.model.Resume;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_NOT_EXISTING = "uuid_not_existing";

    protected static final Resume RESUME_1 = new Resume(UUID_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3);
    protected static final Resume RESUME_NOT_EXISTING = new Resume(UUID_NOT_EXISTING);

    protected static final int INITIAL_SIZE = 3;


    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        assertSize(INITIAL_SIZE);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();

        assertSize(0);
        Assertions.assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    public void update() throws Exception {
        assertUpdate(UUID_1);
        assertUpdate(UUID_2);
        assertUpdate(UUID_3);

        assertNotExist(() -> storage.update(RESUME_NOT_EXISTING));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        Resume[] actual = storage.getAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_NOT_EXISTING);
        assertSize(INITIAL_SIZE + 1);
        assertGet(RESUME_NOT_EXISTING);

        assertExist(() -> storage.save(RESUME_1));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);

        assertSize(INITIAL_SIZE - 1);
        assertNotExist(() -> storage.delete(UUID_1));
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);

        assertNotExist(() -> storage.get(UUID_NOT_EXISTING));
    }

    protected void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }

    protected void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    protected void assertUpdate(String uuid) {
        Resume updated = new Resume(uuid);
        int size = storage.size();

        storage.update(updated);

        Assertions.assertSame(updated, storage.get(uuid));
        assertSize(size);
    }

    protected void assertNotExist(Executable executable) {
        Assertions.assertThrows(NotExistingResumeStorageException.class, executable);
    }

    protected void assertExist(Executable executable) {
        Assertions.assertThrows(ExistingResumeStorageException.class, executable);
    }
}
