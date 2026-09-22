package com.ddrissq.sigdosi.iam.auth.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthPasswordValidateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        String token
) {
}
