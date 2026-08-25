package com.ddrissq.sigdosi.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.ZoneId;

@ConfigurationProperties(prefix = "app")
public record ApplicationProperties(
        ZoneId zone,
        ClientProperties client
) {

    public record ClientProperties(
            String origin
    ) {
    }

}
