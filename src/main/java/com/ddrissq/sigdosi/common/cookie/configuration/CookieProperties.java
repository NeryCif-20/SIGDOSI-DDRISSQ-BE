package com.ddrissq.sigdosi.common.cookie.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.Cookie;

@ConfigurationProperties(prefix = "app.cookies")
public record CookieProperties(
        Cookie.SameSite sameSite,
        String path
) {
}
