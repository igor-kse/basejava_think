package ru.javaops.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.io.Serializable;
import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextSection.class, name = BaseSection.SectionTypes.TEXT_SECTION),
        @JsonSubTypes.Type(value = ListSection.class, name = BaseSection.SectionTypes.LIST_SECTION),
        @JsonSubTypes.Type(value = CompanySection.class, name = BaseSection.SectionTypes.COMPANY_SECTION),
})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseSection implements Serializable {
    private SectionType type;

    protected BaseSection(SectionType type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    protected BaseSection() {
    }

    public SectionType getType() {
        return type;
    }

    public void setType(SectionType type) {
        this.type = type;
    }

    public static class SectionTypes {
        public static final String TEXT_SECTION = "TextSection";
        public static final String LIST_SECTION = "ListSection";
        public static final String COMPANY_SECTION = "CompanySection";

        private SectionTypes() {}
    }
}
