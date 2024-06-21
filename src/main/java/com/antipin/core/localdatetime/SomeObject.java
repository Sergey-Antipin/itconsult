package com.antipin.core.localdatetime;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class SomeObject {

    @JsonFormat(pattern = "yyyy:MM:dd'##'HH:mm:ss:SSS", locale = "ru_RU")
    private LocalDateTime localDateTime = LocalDateTime.now();

}
