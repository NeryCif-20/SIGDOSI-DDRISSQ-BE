package com.ddrissq.sigdosi.iam.auth.mail.model;

import lombok.Builder;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Builder
public record PasswordSetEmailData(
        String to,
        String name,
        String token,
        Instant expiresAt
) {

    private static final String TEMPLATE = "/mail/auth/set-password";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");

    public String template() {
        return TEMPLATE;
    }

    public String buildButtonLink(String uri, String path) {
        return UriComponentsBuilder
                .fromUriString(uri)
                .path(path)
                .queryParam("token", this.token)
                .build()
                .toUriString();
    }

    public String formatExpiresAt(ZoneId zone) {
        return expiresAt.atZone(zone).format(FORMATTER);
    }

}
