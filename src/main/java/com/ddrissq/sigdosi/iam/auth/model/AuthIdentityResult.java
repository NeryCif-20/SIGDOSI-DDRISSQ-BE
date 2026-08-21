package com.ddrissq.sigdosi.iam.auth.model;

import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowTokenStep;
import lombok.Builder;

import java.time.Instant;

@Builder
public record AuthIdentityResult(
        String flowToken,
        Instant expiresAt,
        FlowTokenStep step
) {
}
