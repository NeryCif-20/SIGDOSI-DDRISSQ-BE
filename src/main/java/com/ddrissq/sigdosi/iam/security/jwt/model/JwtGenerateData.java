package com.ddrissq.sigdosi.iam.security.jwt.model;

import lombok.Builder;
import lombok.Singular;

import java.time.Duration;
import java.util.Map;

@Builder
public record JwtGenerateData(
        String subject,
        Duration timeToLive,
        @Singular
        Map<String, Object> claims
) {

    public JwtGenerateData {
        claims = claims == null
                ? Map.of()
                : Map.copyOf(claims);
    }

}
