package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.iam.user.constant.UserErrorMessageKeys;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserCreateRequest(
        @NotNull
        UUID role,
        @NotBlank
        @Email
        @Size(max = 100)
        String email,
        @NotBlank
        @Pattern(regexp = "^\\d{13}$", message = UserErrorMessageKeys.CUI_PATTERN)
        String cui,
        @NotBlank
        @Size(max = 50)
        String firstName,
        @NotBlank
        @Size(max = 50)
        String lastName,
        @Pattern(regexp = "^\\d{8}$", message = UserErrorMessageKeys.PHONE_NUMBER_PATTERN)
        String phoneNumber
) {
}
