package ru.javaops.storage.file.serializers;

import ru.javaops.model.*;
import ru.javaops.util.serializers.ISerializer;
import ru.javaops.util.serializers.XmlStreamSerializer;

public class ResumeXmlStreamSerializer extends XmlStreamSerializer<Resume> implements ISerializer<Resume> {

    public ResumeXmlStreamSerializer() {
        super(Resume.class, Company.class, Company.Period.class,
                CompanySection.class, TextSection.class, ListSection.class);
    }
}
