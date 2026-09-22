package com.ddrissq.sigdosi.iam.auth.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthLoginRequest(
        @NotBlank(message = ValidationError.REQUIRED)
        String password
) {
}
