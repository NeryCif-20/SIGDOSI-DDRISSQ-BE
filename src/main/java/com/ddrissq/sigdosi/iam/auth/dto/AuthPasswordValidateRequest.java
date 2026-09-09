package com.ddrissq.sigdosi.iam.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthPasswordValidateRequest(
        @NotNull
        String token
) {
}
