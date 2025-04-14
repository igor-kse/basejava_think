package ru.javaops.storage.file.serializers.datastream;

import ru.javaops.model.Resume;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ResumeDataStream {

    void write(Resume resume, DataOutputStream dos) throws IOException;

    Resume read(DataInputStream in) throws IOException;
}
