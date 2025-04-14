package ru.javaops.storage.file;

import ru.javaops.util.serializers.ObjectStreamSerializer;

public class ObjectStreamPathStorageTest extends AbstractFileStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(PATH_STORAGE_DIRECTORY, new ObjectStreamSerializer<>()));
    }
}
