package com.antipin.core.localdatetime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeCustomSerializer extends JsonSerializer<LocalDateTime> {

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy:MM:dd'##'hh:mm:ss:SSS");

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.format(formatter));
    }
}
