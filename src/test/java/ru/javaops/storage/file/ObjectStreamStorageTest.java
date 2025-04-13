package ru.javaops.storage.file;

public class ObjectStreamStorageTest extends AbstractFileStorageTest {

    private static final String STORAGE_DIRECTORY = ".\\objectStreamStorage";

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIRECTORY), STORAGE_DIRECTORY);
    }
}
