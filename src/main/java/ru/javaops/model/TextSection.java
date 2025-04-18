package ru.javaops.model;

import java.io.Serial;
import java.util.Objects;

public class TextSection extends BaseSection {

    @Serial
    private static final long serialVersionUID = 1L;

    private String text;

    public TextSection(SectionType type, String text) {
        super(type);
        if (type != SectionType.OBJECTIVE && type != SectionType.PERSONAL) {
            throw new IllegalStateException(type + " is not a valid section type");
        }
        this.text = text;
    }

    public TextSection() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "text='" + text + '\'' +
                '}';
    }
}
