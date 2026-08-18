package com.ddrissq.sigdosi.iam.auth.model;

import lombok.Builder;

@Builder
public record AuthResult(
        String accessToken,
        String refreshToken
) {
}
