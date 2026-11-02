package com.ddrissq.sigdosi.iam.auth.model;

import lombok.Builder;

import java.time.Instant;

@Builder
public record PasswordRequired(
        String flowToken,
        Instant expiresAt
) implements AuthIdentifyResult {

    @Override
    public AuthNextStep nextStep() {
        return AuthNextStep.PASSWORD;
    }
}
