package ru.javaops.model;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Company> companies;

    public CompanySection(SectionType type, List<Company> companies) {
        super(type);

        if (type != SectionType.EXPERIENCE && type != SectionType.EDUCATION) {
            throw new IllegalStateException(type + " is not a valid section type");
        }
        this.companies = companies;
    }

    public CompanySection() {
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(companies);
    }

    @Override
    public String toString() {
        return "CompanySection{" +
                "companies=" + companies +
                '}';
    }
}
