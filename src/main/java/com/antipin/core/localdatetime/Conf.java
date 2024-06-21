package com.antipin.core.localdatetime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootConfiguration
public class Conf {

    @Bean
    public ObjectMapper registerObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("DateTimeCustomSerializer");
        module.addSerializer(LocalDateTime.class, new DateTimeCustomSerializer());
        mapper.registerModule(module);
        return mapper;
    }
}
