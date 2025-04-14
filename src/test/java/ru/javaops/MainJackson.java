package ru.javaops;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.javaops.model.Resume;
import ru.javaops.model.ResumeTestData;

public class MainJackson {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = JsonMapper.builder()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build()
                        .registerModule(new JavaTimeModule());

        String json = mapper.writeValueAsString(ResumeTestData.createFilledResume("uuid1", "Вася Рогов"));
        System.out.println(json);

        Resume resume = mapper.readValue(json, Resume.class);
        System.out.println(resume);
    }
}
