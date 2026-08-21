package com.ddrissq.sigdosi.iam.auth.model;

import lombok.Builder;

import java.time.Instant;

@Builder
public record AuthResult(
        String accessToken,
        String refreshToken,
        Instant expiresAt
) {
}
