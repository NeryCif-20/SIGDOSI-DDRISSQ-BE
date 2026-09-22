package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.iam.user.error.UserValidationError;
import com.ddrissq.sigdosi.iam.user.model.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserUpdateRequest(
        @NullableNotBlank
        @Email(message = ValidationError.EMAIL)
        @Size(min = 1, max = 100, message = ValidationError.SIZE)
        String email,
        UserStatus status,
        @Pattern(regexp = "^\\d{13}$", message = UserValidationError.CUI_PATTERN)
        String cui,
        @NullableNotBlank
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String firstName,
        @NullableNotBlank
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String lastName,
        @Pattern(regexp = "^\\d{8}$", message = UserValidationError.PHONE_NUMBER_PATTERN)
        String phoneNumber
) {
}
