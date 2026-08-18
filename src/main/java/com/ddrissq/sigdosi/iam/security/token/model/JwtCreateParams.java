package com.ddrissq.sigdosi.iam.security.token.model;

import lombok.Builder;
import lombok.Singular;

import java.time.Duration;
import java.util.Map;

@Builder
public record JwtCreateParams(
        String subject,
        Duration expirationTime,
        @Singular
        Map<String, Object> claims
) {

    public JwtCreateParams {
        claims = claims == null
                ? Map.of()
                : Map.copyOf(claims);
    }

}
