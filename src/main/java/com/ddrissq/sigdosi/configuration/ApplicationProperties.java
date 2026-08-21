package com.ddrissq.sigdosi.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.Cookie;

import java.time.ZoneId;

@ConfigurationProperties(prefix = "app")
public record ApplicationProperties(
        ClientProperties client,
        CookiesProperties cookies,
        ZoneId zone
) {

    public record ClientProperties(
            String origin
    ) {

    }

    public record CookiesProperties(
            Cookie.SameSite sameSite,
            String path
    ) {
    }

}
