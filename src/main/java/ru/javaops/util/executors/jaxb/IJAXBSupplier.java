package ru.javaops.util.executors.jaxb;

import jakarta.xml.bind.JAXBException;

@FunctionalInterface
public interface IJAXBSupplier<R> {
    R supply() throws JAXBException;
}
