package ru.javaops.storage.file;

import ru.javaops.storage.file.serializers.ResumeXmlStreamSerializer;

public class XmlStreamPathStorageTest extends AbstractFileStorageTest {

    public XmlStreamPathStorageTest() {
        super(new PathStorage(PATH_STORAGE_DIRECTORY, new ResumeXmlStreamSerializer()));
    }
}
