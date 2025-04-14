package ru.javaops.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextSection.class, name = AbstractSection.SectionTypes.TEXT_SECTION),
        @JsonSubTypes.Type(value = ListSection.class, name = AbstractSection.SectionTypes.LIST_SECTION),
        @JsonSubTypes.Type(value = CompanySection.class, name = AbstractSection.SectionTypes.COMPANY_SECTION),
})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractSection implements Serializable {
    protected AbstractSection() {
    }

    public static class SectionTypes {
        public static final String TEXT_SECTION = "TextSection";
        public static final String LIST_SECTION = "ListSection";
        public static final String COMPANY_SECTION = "CompanySection";

        private SectionTypes() {}
    }
}
