package com.ddrissq.sigdosi.iam.auth.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "iam.auth")
public record AuthProperties(
    PersistentTokenProperties flowToken,
    PersistentTokenProperties passwordToken,
    PersistentTokenProperties refreshToken,
    TokenProperties accessToken
) {

    public record TokenProperties(
            Duration timeToLive
    ) {
    }

    public record PersistentTokenProperties(
            Duration timeToLive,
            String cleanup
    ) {
    }

}
