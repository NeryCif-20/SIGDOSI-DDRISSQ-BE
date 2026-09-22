package com.ddrissq.sigdosi.iam.auth.mail.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordSetMailData {

    private static final String TEMPLATE = "/mail/auth/set-password";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");

    private final String to;
    private final String name;
    private final PasswordSetAction action;
    private final String token;
    private final Instant expiresAt;

    @Builder(builderMethodName = "forSetupBuilder")
    private static PasswordSetMailData forSetup(String to, String name, String token, Instant expiresAt) {
        return new PasswordSetMailData(to, name, PasswordSetAction.SETUP, token, expiresAt);
    }

    @Builder(builderMethodName = "forResetBuilder")
    private static PasswordSetMailData forReset(String to, String name, String token, Instant expiresAt) {
        return new PasswordSetMailData(to, name, PasswordSetAction.RESET, token, expiresAt);
    }

    public String to() {
        return this.to;
    }

    public String name() {
        return this.name;
    }

    public PasswordSetAction action() {
        return this.action;
    }

    public String template() {
        return TEMPLATE;
    }

    public String buildButtonLink(String uri, String path) {
        return UriComponentsBuilder
                .fromUriString(uri)
                .path(path.formatted(this.token))
                .build()
                .toUriString();
    }

    public String formatExpiresAt(ZoneId zone) {
        return expiresAt.atZone(zone).format(FORMATTER);
    }

}
