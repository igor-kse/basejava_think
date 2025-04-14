package ru.javaops.storage.file;

import ru.javaops.storage.file.serializers.ResumeJsonSerializer;

public class JsonPathStorageTest extends AbstractFileStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new ResumeJsonSerializer()));
    }
}
