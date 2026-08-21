package com.ddrissq.sigdosi.iam.auth.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "iam.auth")
public record AuthProperties(
    TokenProperties flowToken,
    TokenProperties passwordToken,
    TokenProperties refreshToken,
    TokenProperties accessToken
) {

    public record TokenProperties(
            Duration timeToLive
    ) {
    }

}
