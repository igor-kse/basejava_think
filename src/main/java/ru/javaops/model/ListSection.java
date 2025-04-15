package ru.javaops.model;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

public class ListSection extends BaseSection {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> list;

    public ListSection(SectionType type, List<String> list) {
        super(type);
        if (type != SectionType.ACHIEVEMENT && type != SectionType.QUALIFICATIONS) {
            throw new IllegalStateException(type + " is not a valid section type");
        }
        this.list = list;
    }

    public ListSection() {
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(list);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "list=" + list +
                '}';
    }
}
