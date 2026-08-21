package com.ddrissq.sigdosi.iam.auth.passwordtoken.model;

import com.ddrissq.sigdosi.iam.user.model.User;
import lombok.Builder;

import java.time.Instant;

@Builder
public record PasswordTokenResult(
        User user,
        String token,
        PasswordTokenPurpose purpose,
        Instant expiresAt
) {
}
