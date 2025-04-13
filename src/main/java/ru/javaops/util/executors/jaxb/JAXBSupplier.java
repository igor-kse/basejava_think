package ru.javaops.util.executors.jaxb;

import jakarta.xml.bind.JAXBException;

@FunctionalInterface
public interface JAXBSupplier<R> {
    R supply() throws JAXBException;
}
