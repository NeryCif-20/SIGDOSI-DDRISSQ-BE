package com.ddrissq.sigdosi.iam.auth.refreshtoken.model;

import lombok.Builder;

import java.time.Instant;

@Builder
public record RefreshTokenResult(
        String token,
        Instant expiresAt
) {
}
