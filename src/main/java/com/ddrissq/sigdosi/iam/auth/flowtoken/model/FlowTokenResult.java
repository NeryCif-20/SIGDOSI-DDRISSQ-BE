package com.ddrissq.sigdosi.iam.auth.flowtoken.model;

import lombok.Builder;

import java.time.Instant;

@Builder
public record FlowTokenResult(
        String token,
        FlowTokenStep step,
        Instant expiresAt
) {
}
