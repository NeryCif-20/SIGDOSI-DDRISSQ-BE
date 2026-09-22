package com.ddrissq.sigdosi.iam.auth.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthIdentifyRequest(
        @NotBlank(message = ValidationError.REQUIRED)
        @Email(message = ValidationError.EMAIL)
        String email
) {
}
