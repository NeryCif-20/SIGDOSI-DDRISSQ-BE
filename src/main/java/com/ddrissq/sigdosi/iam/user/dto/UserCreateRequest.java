package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.iam.user.error.UserValidationError;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserCreateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        UUID role,
        @NotBlank(message = ValidationError.REQUIRED)
        @Email(message = ValidationError.EMAIL)
        @Size(min = 1, max = 100, message = ValidationError.SIZE)
        String email,
        @NotNull(message = ValidationError.REQUIRED)
        @Pattern(regexp = "^\\d{13}$", message = UserValidationError.CUI_PATTERN)
        String cui,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String firstName,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String lastName,
        @Pattern(regexp = "^\\d{8}$", message = UserValidationError.PHONE_NUMBER_PATTERN)
        String phoneNumber
) {
}
