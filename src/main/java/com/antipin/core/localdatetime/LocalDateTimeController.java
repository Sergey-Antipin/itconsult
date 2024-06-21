package com.antipin.core.localdatetime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/localdatetime")
public class LocalDateTimeController {

    @GetMapping
    public SomeObject getDateTime() {
        return new SomeObject();
    }
}
