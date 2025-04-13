package ru.javaops.util.serializers;

import ru.javaops.util.XmlParser;

import java.io.*;

public class XmlStreamSerializer<R> {

    private final XmlParser xmlParser;

    public XmlStreamSerializer(Class<?>... classes) {
        xmlParser = new XmlParser(classes);
    }

    public R doRead(InputStream is) {
        Reader reader = new InputStreamReader(is);
        return xmlParser.unmarshall(reader);
    }

    public void doWrite(R instance, OutputStream os) {
        Writer writer = new OutputStreamWriter(os);
        xmlParser.marshall(instance, writer);
    }
}
