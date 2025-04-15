package ru.javaops.storage.file.serializers.datastream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javaops.exceptions.CorruptedDataFormatException;
import ru.javaops.exceptions.UnsupportedFormatVersionException;
import ru.javaops.model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ResumeDataStreamV1 implements IResumeDataStream {

    private static final String RESUME_HEADER = Resume.class.getSimpleName();
    private static final String RESUME_CONTACTS_COUNT_HEADER = "contacts";
    private static final String RESUME_SECTIONS_COUNT_HEADER = "sections";

    private static final int CURRENT_VERSION = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResumeDataStreamV1.class.getName());

    @Override
    public void write(Resume resume, DataOutputStream dos) throws IOException {
        dos.writeUTF(RESUME_HEADER);
        dos.writeInt(CURRENT_VERSION);

        dos.writeUTF(resume.getUuid());
        dos.writeUTF(resume.getFullName());
        writeContacts(resume, dos);
        writeSections(resume, dos);
    }

    @Override
    public Resume read(DataInputStream dis) throws IOException {
        var header = dis.readUTF();
        var version = dis.readInt();

        if (version != CURRENT_VERSION || !RESUME_HEADER.equals(header)) {
            throw new UnsupportedFormatVersionException(version);
        }

        var uuid = dis.readUTF();
        var fullName = dis.readUTF();
        var contacts = readContacts(dis);
        var sections = readSections(dis);
        return new Resume(uuid, fullName, contacts, sections);
    }

    public void writeContacts(Resume resume, DataOutputStream dos) throws IOException {
        dos.writeUTF(RESUME_CONTACTS_COUNT_HEADER);
        dos.writeInt(resume.getContacts().size());
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private Map<ContactType, String> readContacts(DataInputStream dis) throws IOException {
        var header = dis.readUTF();
        int contactCount = dis.readInt();

        if (contactCount < 0 || !header.equals(RESUME_CONTACTS_COUNT_HEADER)) {
            var errorMessage = "Invalid header or contact count: header=" + header + ", count=" + contactCount;
            throw new CorruptedDataFormatException(errorMessage);
        }

        var contacts = new EnumMap<ContactType, String>(ContactType.class);
        for (int i = 0; i < contactCount; i++) {
            var type = ContactType.valueOf(dis.readUTF());
            var value = dis.readUTF();
            contacts.put(type, value);
        }
        return contacts;
    }

    public void writeSections(Resume resume, DataOutputStream dos) throws IOException {
        dos.writeUTF(RESUME_SECTIONS_COUNT_HEADER);
        dos.writeInt(resume.getSections().size());
        for (Map.Entry<SectionType, BaseSection> entry : resume.getSections().entrySet()) {
            var type = entry.getKey();
            var section = entry.getValue();

            Objects.requireNonNull(section, "Section must not be null");

            dos.writeUTF(type.name());
            switch (type) {
                case OBJECTIVE, PERSONAL -> writeTextSection(section, dos);
                case QUALIFICATIONS, ACHIEVEMENT -> writeListSection(section, dos);
                case EXPERIENCE, EDUCATION -> writeCompanySection(section, dos);
                default -> LOGGER.warn("Unknown section type: {}", type);
            }
        }
    }

    public Map<SectionType, BaseSection> readSections(DataInputStream dis) throws IOException {
        var header = dis.readUTF();
        int sectionCount = dis.readInt();

        if (sectionCount < 0 || !header.equals(RESUME_SECTIONS_COUNT_HEADER)) {
            var errorMessage = "Invalid header or section count: header=" + header + ", count=" + sectionCount;
            throw new CorruptedDataFormatException(errorMessage);
        }

        var sections = new EnumMap<SectionType, BaseSection>(SectionType.class);
        for (int i = 0; i < sectionCount; i++) {
            var typeName = dis.readUTF();
            var type = SectionType.valueOf(typeName);
            var section = switch (type) {
                case OBJECTIVE, PERSONAL -> readTextSection(dis, type);
                case QUALIFICATIONS, ACHIEVEMENT -> readListSection(dis, type);
                case EXPERIENCE, EDUCATION -> readCompanySection(dis, type);
            };
            sections.put(type, section);
        }
        return sections;
    }

    private void writeTextSection(BaseSection section, DataOutputStream dos) throws IOException {
        if (section instanceof TextSection textSection) {
            dos.writeUTF(textSection.getText());
            return;
        }
        var name = section == null ? "null" : section.getClass().getSimpleName();
        throw new IllegalArgumentException("Expected TextSection, but got " + name);
    }

    private TextSection readTextSection(DataInputStream dis, SectionType type) throws IOException {
        var text = dis.readUTF();
        return new TextSection(type, text);
    }

    private void writeListSection(BaseSection section, DataOutputStream dos) throws IOException {
        if (section instanceof ListSection listSection) {
            dos.writeInt(listSection.getList().size());
            for (String string : listSection.getList()) {
                dos.writeUTF(string);
            }
            return;
        }
        var name = section == null ? "null" : section.getClass().getSimpleName();
        throw new IllegalArgumentException("Expected ListSection, but got " + name);
    }

    private ListSection readListSection(DataInputStream dis, SectionType type) throws IOException {
        int count = dis.readInt();
        if (count < 0) {
            var errorMessage = "Invalid items count: " + count;
            throw new CorruptedDataFormatException(errorMessage);
        }

        var list = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            var item = dis.readUTF();
            list.add(item);
        }
        return new ListSection(type, list);
    }

    private void writeCompanySection(BaseSection section, DataOutputStream dos) throws IOException {
        if (section instanceof CompanySection companySection) {
            dos.writeInt(companySection.getCompanies().size());
            for (Company company : companySection.getCompanies()) {
                writeCompany(company, dos);
            }
            return;
        }
        var name = section == null ? "null" : section.getClass().getSimpleName();
        throw new IllegalArgumentException("Expected CompanySection, but got " + name);
    }

    private CompanySection readCompanySection(DataInputStream dis, SectionType type) throws IOException {
        int count = dis.readInt();
        if (count < 0) {
            var errorMessage = "Invalid count of companies: " + count;
            throw new CorruptedDataFormatException(errorMessage);
        }

        var companies = new ArrayList<Company>();
        for (int i = 0; i < count; i++) {
            var company = readCompany(dis);
            companies.add(company);
        }
        return new CompanySection(type, companies);
    }

    private void writeCompany(Company company, DataOutputStream dos) throws IOException {
        Objects.requireNonNull(company, "Company must not be null");

        dos.writeUTF(company.getName());
        dos.writeUTF(company.getWebsite());
        dos.writeInt(company.getPeriods().size());

        for (Company.Period period : company.getPeriods()) {
            writePeriod(period, dos);
        }
    }

    private Company readCompany(DataInputStream dis) throws IOException {
        var name = dis.readUTF();
        var website = dis.readUTF();

        var count = dis.readInt();
        if (count < 0) {
            var errorMessage = "Invalid count of periods: " + count;
            throw new CorruptedDataFormatException(errorMessage);
        }

        var periods = new ArrayList<Company.Period>();
        for (int i = 0; i < count; i++) {
            var period = readPeriod(dis);
            periods.add(period);
        }
        return new Company(name, website, periods);
    }

    private void writePeriod(Company.Period period, DataOutputStream dos) throws IOException {
        Objects.requireNonNull(period, "Period must not be null");

        dos.writeUTF(period.getTitle());
        dos.writeUTF(period.getDescription());
        dos.writeUTF(period.getStartDate().toString());
        dos.writeUTF(period.getEndDate().toString());
    }

    private Company.Period readPeriod(DataInputStream dis) throws IOException {
        var title = dis.readUTF();
        var description = dis.readUTF();
        var startDate = LocalDate.parse(dis.readUTF());
        var endDate = LocalDate.parse(dis.readUTF());
        return new Company.Period(startDate, endDate, title, description);
    }
}
