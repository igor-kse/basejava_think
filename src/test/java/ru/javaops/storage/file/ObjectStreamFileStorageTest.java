package ru.javaops.storage.file;

import ru.javaops.util.serializers.ObjectStreamSerializer;

public class ObjectStreamFileStorageTest extends AbstractFileStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(FILE_STORAGE_DIRECTORY, new ObjectStreamSerializer<>()));
    }
}
