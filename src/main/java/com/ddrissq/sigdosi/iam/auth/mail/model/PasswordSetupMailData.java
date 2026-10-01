package com.ddrissq.sigdosi.iam.auth.mail.model;

import com.ddrissq.sigdosi.iam.auth.model.PasswordSetupAction;
import lombok.Builder;

import java.time.Instant;

@Builder
public record PasswordSetupMailData(
        String to,
        String name,
        PasswordSetupAction action,
        String token,
        Instant expiresAt
) {
}
