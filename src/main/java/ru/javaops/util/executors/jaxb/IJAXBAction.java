package ru.javaops.util.executors.jaxb;

import jakarta.xml.bind.JAXBException;

@FunctionalInterface
public interface IJAXBAction {
    void apply() throws JAXBException;
}
