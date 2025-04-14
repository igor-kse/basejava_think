package ru.javaops.util.serializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JsonSerializer<T> implements ISerializer<T> {

    private static final ObjectMapper mapper = JsonMapper.builder()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .build()
            .registerModule(new JavaTimeModule());

    private final TypeReference<T> typeReference;

    public JsonSerializer(TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public T doRead(InputStream is) throws IOException {
        return mapper.readValue(is, typeReference);
    }

    @Override
    public void doWrite(T instance, OutputStream os) throws IOException {
        mapper.writeValue(os, instance);
    }
}
