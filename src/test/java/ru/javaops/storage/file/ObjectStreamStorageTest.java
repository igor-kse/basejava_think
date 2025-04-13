package ru.javaops.storage.file;

public class ObjectStreamStorageTest extends AbstractFileStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(".\\objectStreamStorage"));
    }
}
