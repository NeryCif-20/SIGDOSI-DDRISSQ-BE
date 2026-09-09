package com.ddrissq.sigdosi.iam.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthLoginRequest(
        @NotBlank
        String password
) {
}
