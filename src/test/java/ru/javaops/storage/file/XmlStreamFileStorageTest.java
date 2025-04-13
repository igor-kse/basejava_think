package ru.javaops.storage.file;

import ru.javaops.storage.file.serializers.ResumeXmlStreamSerializer;

import java.io.File;

public class XmlStreamFileStorageTest extends AbstractFileStorageTest {
    public XmlStreamFileStorageTest() {
        super(new FileStorage(new File(USER_DIRECTORY_PATH, STORAGE_DIRECTORY_PATH), new ResumeXmlStreamSerializer()));
    }
}
