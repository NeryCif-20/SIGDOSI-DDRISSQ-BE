package com.ddrissq.sigdosi.iam.auth.dto;

import com.ddrissq.sigdosi.common.validation.annotation.CompareFields;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotBlank;

@CompareFields(
        first = "confirmNewPassword",
        second = "newPassword")
public record AuthPasswordSetRequest(
        @NotBlank(message = ValidationError.REQUIRED)
        String token,
        @NotBlank(message = ValidationError.REQUIRED)
        String newPassword,
        @NotBlank(message = ValidationError.REQUIRED)
        String confirmNewPassword
) {
}
