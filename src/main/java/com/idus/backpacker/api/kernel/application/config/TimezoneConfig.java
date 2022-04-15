package com.idus.backpacker.api.kernel.application.config;

import java.time.ZoneId;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimezoneConfig {
    private static final String TIMEZONE = "UTC";

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(TIMEZONE)));
    }
}
