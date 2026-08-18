package com.ddrissq.sigdosi.iam.auth.dto;

import lombok.Builder;

@Builder
public record AuthPasswordValidateRequest(
        String token
) {
}
