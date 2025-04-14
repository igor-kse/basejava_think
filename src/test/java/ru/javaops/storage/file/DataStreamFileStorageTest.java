package ru.javaops.storage.file;

import ru.javaops.storage.file.serializers.DataStreamSerializer;
import ru.javaops.storage.file.serializers.datastream.ResumeDataStreamV1;

public class DataStreamFileStorageTest extends AbstractFileStorageTest {

    public DataStreamFileStorageTest() {
        super(new FileStorage(FILE_STORAGE_DIRECTORY, new DataStreamSerializer(new ResumeDataStreamV1())));
    }
}
