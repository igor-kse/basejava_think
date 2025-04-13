package ru.javaops.storage.file;

public class ObjectStreamFileStorageTest extends AbstractFileStorageTest {

    private static final String STORAGE_DIRECTORY = ".\\objectStreamStorage";

    public ObjectStreamFileStorageTest() {
        super(new ObjectStreamFileStorage(STORAGE_DIRECTORY), STORAGE_DIRECTORY);
    }
}
