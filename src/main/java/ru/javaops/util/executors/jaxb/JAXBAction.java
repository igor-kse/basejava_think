package ru.javaops.util.executors.jaxb;

import jakarta.xml.bind.JAXBException;

@FunctionalInterface
public interface JAXBAction {
    void apply() throws JAXBException;
}
