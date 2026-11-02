package com.ddrissq.sigdosi.iam.auth.flowtoken.model;

import lombok.Builder;

import java.time.Instant;

@Builder
public record FlowTokenCreateResult(
        String token,
        Instant expiresAt
) {
}
