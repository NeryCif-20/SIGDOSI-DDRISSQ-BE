package com.ddrissq.sigdosi.iam.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthLoginRequest(
        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {
}
