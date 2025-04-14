package ru.javaops.model;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static ru.javaops.model.ContactType.*;

public class ResumeTestData {

    public static Resume createFilledResume(String uuid, String fullName) {
        return new Resume(uuid, fullName, createContacts(), createSections());
    }

    protected static Map<ContactType, String> createContacts() {
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(MOBILE_PHONE, "+1 111 1111111");
        contacts.put(TEAMS, "FISH");
        contacts.put(EMAIL, "john.fish@fish.doe");
        contacts.put(LINKEDIN, "john.doe.linkedin");
        contacts.put(GITHUB, "john.doe.github");
        contacts.put(STACK_OVERFLOW, "john.doe.stackoverflow");
        contacts.put(HOMEPAGE, "john.doe.homepage");
        return contacts;
    }

    protected static Map<SectionType, AbstractSection> createSections() {
        Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
        sections.put(SectionType.PERSONAL, new TextSection(SectionType.PERSONAL, "Personal"));
        sections.put(SectionType.OBJECTIVE, new TextSection(SectionType.OBJECTIVE, "Objective"));
        sections.put(SectionType.ACHIEVEMENT, new ListSection(SectionType.ACHIEVEMENT, List.of("Achieve 1", "Achieve 2", "Achieve 3")));
        sections.put(SectionType.QUALIFICATIONS, new ListSection(SectionType.QUALIFICATIONS, List.of("Q1", "Q2", "Q3")));
        sections.put(SectionType.EDUCATION, createEducation());
        sections.put(SectionType.EXPERIENCE, createExperience());
        return sections;
    }

    protected static CompanySection createEducation() {
        Company companyA = createCompany("University One", "website.one");
        Company companyB = createCompany("University Two", "website.two");
        Company companyC = createCompany("University Three", "website.three");
        var companies = List.of(companyA, companyB, companyC);
        return new CompanySection(SectionType.EDUCATION, companies);
    }

    protected static CompanySection createExperience() {
        Company companyA = createCompany("Company One", "website.one");
        Company companyB = createCompany("Company Two", "website.two");
        Company companyC = createCompany("Company Three", "website.three");
        var companies = List.of(companyA, companyB, companyC);
        return new CompanySection(SectionType.EXPERIENCE, companies);
    }

    protected static Company createCompany(String name, String website) {
        List<Company.Period> periods = List.of(
                new Company.Period(
                        LocalDate.of(2020, 1, 1),
                        LocalDate.of(2022, 12, 12),
                        "The title",
                        "The description"
                ),
                new Company.Period(
                        LocalDate.of(2022, 12, 12),
                        LocalDate.of(2024, 1, 1),
                        "The title 2",
                        "The description 2"
                )
        );

        Company company = new Company(name, website);
        company.setPeriods(periods);
        return company;
    }
}
