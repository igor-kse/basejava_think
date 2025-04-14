package ru.javaops.storage.file.serializers;

import ru.javaops.model.ContactType;
import ru.javaops.model.Resume;
import ru.javaops.util.serializers.ISerializer;

import java.io.*;
import java.util.Map;

public class DataStreamSerializer implements ISerializer<Resume> {

    private final String CONTACTS_COUNT = "contacts_count=";

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return null;
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try(DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeContacts(resume, dos);
        }
    }

    private void writeContacts(Resume resume, DataOutputStream dos) throws IOException {
        dos.writeUTF(CONTACTS_COUNT + resume.getContacts().size());
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }
}
