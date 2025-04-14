package ru.javaops.storage.file.serializers;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.javaops.model.Resume;
import ru.javaops.util.serializers.JsonSerializer;

public class ResumeJsonSerializer extends JsonSerializer<Resume> {

    public ResumeJsonSerializer() {
        super(new TypeReference<>() {});
    }
}
