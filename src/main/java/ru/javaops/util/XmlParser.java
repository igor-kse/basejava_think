package ru.javaops.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import ru.javaops.util.executors.jaxb.JAXBExecutor;

import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unchecked")
public class XmlParser {
    private final JAXBExecutor executor;

    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller;
    private Marshaller marshaller;

    public XmlParser(Class<?>... classes) {
        executor = new JAXBExecutor();
        executor.apply(() -> {
            jaxbContext = JAXBContext.newInstance(classes);
            unmarshaller = jaxbContext.createUnmarshaller();
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
        });
    }

    public <R> R unmarshall(Reader reader) {
        return executor.supply(() -> (R) unmarshaller.unmarshal(reader));
    }

    public void marshall(Object instance, Writer writer) {
        executor.apply(() -> marshaller.marshal(instance, writer));
    }
}
