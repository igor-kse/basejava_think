package ru.javaops.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;

public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String string) {
        return (string == null || string.isEmpty())
            ? null
            : LocalDate.parse(string);
    }

    @Override
    public String marshal(LocalDate localDate) {
        return (localDate == null)
            ? null
            : localDate.toString();
    }
}
