package com.ddrissq.sigdosi.iam.auth.passwordtoken.model;

import com.ddrissq.sigdosi.iam.auth.model.PasswordSetupAction;
import com.ddrissq.sigdosi.iam.user.model.User;
import lombok.Builder;

import java.time.Instant;

@Builder
public record PasswordTokenCreateResult(
        User user,
        String token,
        PasswordSetupAction purpose,
        Instant expiresAt
) {
}
