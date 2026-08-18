package com.ddrissq.sigdosi.iam.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken
) {
}
