package com.antipin.core.localdatetime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/localdatetime")
public class LocalDateTimeController {

    @GetMapping
    public LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }
}
