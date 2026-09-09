package com.ddrissq.sigdosi.iam.auth.dto;

import com.ddrissq.sigdosi.common.validation.comparison.annotation.Compare;
import jakarta.validation.constraints.NotBlank;

@Compare(
        firstField = "confirmNewPassword",
        secondField = "newPassword")
public record AuthPasswordSetRequest(
        @NotBlank
        String token,
        @NotBlank
        String newPassword,
        @NotBlank
        String confirmNewPassword
) {
}
