package ru.javaops.storage.file;

import ru.javaops.util.serializers.ObjectStreamSerializer;

import java.io.File;

public class ObjectStreamFileStorageTest extends AbstractFileStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(new File(USER_DIRECTORY_PATH, STORAGE_DIRECTORY_PATH), new ObjectStreamSerializer<>()));
    }
}
