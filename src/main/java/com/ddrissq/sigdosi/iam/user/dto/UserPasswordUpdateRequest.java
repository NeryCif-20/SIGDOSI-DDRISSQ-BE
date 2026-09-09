package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.common.validation.comparison.annotation.Compare;
import jakarta.validation.constraints.NotBlank;

@Compare(
        firstField = "confirmNewPassword",
        secondField = "newPassword")
public record UserPasswordUpdateRequest(
        @NotBlank
        String currentPassword,
        @NotBlank
        String newPassword,
        @NotBlank
        String confirmNewPassword
) {
}
