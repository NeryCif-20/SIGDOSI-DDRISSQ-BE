package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.iam.user.constant.UserErrorMessageKeys;
import com.ddrissq.sigdosi.iam.user.model.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserUpdateRequest(
        @Email
        @Size(max = 100)
        String email,
        UserStatus status,
        @Pattern(regexp = "^\\d{13}$", message = UserErrorMessageKeys.CUI_PATTERN)
        String cui,
        @Size(max = 50)
        String firstName,
        @Size(max = 50)
        String lastName,
        @Pattern(regexp = "^\\d{8}$", message = UserErrorMessageKeys.PHONE_NUMBER_PATTERN)
        String phoneNumber
) {
}
