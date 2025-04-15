package ru.javaops.storage.file;

import ru.javaops.storage.file.serializers.ResumeXmlStreamSerializer;

public class XmlStreamFileStorageTest extends BaseFileStorageTest {
    public XmlStreamFileStorageTest() {
        super(new FileStorage(FILE_STORAGE_DIRECTORY, new ResumeXmlStreamSerializer()));
    }
}
