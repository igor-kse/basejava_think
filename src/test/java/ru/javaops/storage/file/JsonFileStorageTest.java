package ru.javaops.storage.file;

import ru.javaops.storage.file.serializers.ResumeJsonSerializer;

import java.io.File;

public class JsonFileStorageTest extends AbstractFileStorageTest {

    public JsonFileStorageTest() {
        super(new FileStorage(new File(USER_DIRECTORY_PATH, STORAGE_DIRECTORY_PATH), new ResumeJsonSerializer()));
    }
}
