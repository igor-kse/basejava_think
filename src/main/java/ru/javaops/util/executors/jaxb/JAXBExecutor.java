package ru.javaops.util.executors.jaxb;

import jakarta.xml.bind.JAXBException;

public class JAXBExecutor {
    public <R> R supply(JAXBSupplier<R> supplier) {
        try {
            return supplier.supply();
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public void apply(JAXBAction executor) {
        try {
            executor.apply();
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}