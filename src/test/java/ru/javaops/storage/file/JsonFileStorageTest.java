package ru.javaops.storage.file;

import ru.javaops.storage.file.serializers.ResumeJsonSerializer;

public class JsonFileStorageTest extends BaseFileStorageTest {

    public JsonFileStorageTest() {
        super(new FileStorage(FILE_STORAGE_DIRECTORY, new ResumeJsonSerializer()));
    }
}
