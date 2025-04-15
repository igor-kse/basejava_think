package ru.javaops.storage.file.serializers;

import ru.javaops.model.Resume;
import ru.javaops.storage.file.serializers.datastream.IResumeDataStream;
import ru.javaops.util.serializers.ISerializer;

import java.io.*;

public class DataStreamSerializer implements ISerializer<Resume> {

    private final IResumeDataStream resumeDataStream;

    public DataStreamSerializer(IResumeDataStream resumeDataStream) {
        this.resumeDataStream = resumeDataStream;
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            return resumeDataStream.read(dis);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            resumeDataStream.write(resume, dos);
        }
    }
}
