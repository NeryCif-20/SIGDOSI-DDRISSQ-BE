package com.ddrissq.sigdosi.iam.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthPasswordValidateRequest(
        @NotNull(message = "El token es obligatorio")
        String token
) {
}
